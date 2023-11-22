package com.kibong.shoppingwiki.contents.service;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.category.repository.CategoryRedisRepository;
import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents.dto.RequestContents;
import com.kibong.shoppingwiki.contents.repository.ContentsRedisRepository;
import com.kibong.shoppingwiki.contents.repository.ContentsRepository;
import com.kibong.shoppingwiki.contents.repository.TodayRankRedisRepository;
import com.kibong.shoppingwiki.contents.repository.TotalRankRedisRepository;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import com.kibong.shoppingwiki.contents_log.repository.ContentsLogRepository;
import com.kibong.shoppingwiki.domain.*;
import com.kibong.shoppingwiki.domain.redis.RedisCategory;
import com.kibong.shoppingwiki.domain.redis.RedisContents;
import com.kibong.shoppingwiki.kafka.KafkaProducer;
import com.kibong.shoppingwiki.kafka.dto.ContentsLogMsgDto;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user_contents.repository.UserContentsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.*;

import static com.kibong.shoppingwiki.user.UserUtil.nullCheckUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentsServiceImpl implements ContentsService {

    private final UserRepository userRepository;
    private final UserContentsRepository userContentsRepository;
    private final ContentsRepository contentsRepository;
    private final ContentsCategoryRepository contentsCategoryRepository;
    private final ContentsLogRepository contentsLogRepository;
    private final CategoryRepository categoryRepository;

    private final ContentsRedisRepository contentsRedisRepository;
    private final CategoryRedisRepository categoryRedisRepository;
    private final TotalRankRedisRepository totalRankRedisRepository;
    private final TodayRankRedisRepository todayRankRedisRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public ContentsDto searchContents(String searchValue) {

        Optional<RedisContents> redisContentsOptional = contentsRedisRepository.findByContentsSubject(searchValue);
        ContentsDto contentsDto = new ContentsDto();

        //레디스 콘텐츠가 없는 경우 DB에서 데이터 가져오기
        if(redisContentsOptional.isEmpty()){
            Optional<ContentsDto> optionalContentsDto = contentsCategoryRepository.searchContents(searchValue);

            //DB에 콘텐츠가 있는 경우 레디스에 저장해 둔다.
            //없을 경우에는 Kafka로 chat-gpt에 보내 데이터를 쌓도록 한다.
            //DB의 있을 경우 카프카를 통해 Redis에 데이터를 저장하도록 함
            if (optionalContentsDto.isPresent()) {
                JSONObject kafkaValue = new JSONObject();
                ContentsDto searchContentsDto = optionalContentsDto.get();

                List<CategoryDto> categoryList = contentsCategoryRepository.getCategoryList(searchContentsDto.getContentsId());
                for (CategoryDto categoryDto : categoryList) {
                    categoryDto.convertDate();
                }

                kafkaValue.put("categoryList" ,categoryList);
                kafkaValue.put("contentsId", searchContentsDto.getContentsId());
                kafkaValue.put("contentsSubject", searchContentsDto.getContentsSubject());
                kafkaValue.put("contentsDetail", searchContentsDto.getContentsDetail());
                kafkaValue.put("contentsUseYn", searchContentsDto.getContentsUseYn());
                kafkaValue.put("regDate", searchContentsDto.getRegDate().toEpochSecond(ZoneOffset.UTC));
                kafkaValue.put("modDate", searchContentsDto.getModDate().toEpochSecond(ZoneOffset.UTC));

                kafkaProducer.jsonSend("create-contents-redis", kafkaValue);

                searchContentsDto.setCategoryList(categoryList);
                contentsDto = searchContentsDto;
            }else{
                kafkaProducer.send("create-contents", searchValue);
            }

        } else {
            RedisContents redisContents = redisContentsOptional.get();
            contentsDto.convertRedis(redisContents);
            List<RedisCategory> redisCategoryList = redisContents.getCategoryList();
            if (!redisCategoryList.isEmpty()) {
                List<CategoryDto> categoryDtoList = new ArrayList<>();
                redisCategoryList.forEach(redisCategory -> {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.convertCategory(redisCategory);
                    categoryDtoList.add(categoryDto);
                });
                contentsDto.addCategoryList(categoryDtoList);
            }


        }

        contentsDto.setTodayRankList(todayRankRedisRepository.findTodayRanksTop10ByOrderByCountDesc());
        contentsDto.setTotalRankList(totalRankRedisRepository.findTotalRanksTop10ByOrderByCountDesc());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contentsSubject", searchValue);

        kafkaProducer.jsonSend("count_contents", jsonObject);

        return contentsDto;
    }

    /**
     * 콘텐츠 업데이트
     *
     * @param userId         유저 아이디
     * @param contentsId     콘텐츠 아이디
     * @param contentsDetail 콘텐츠 상세
     */
    @Override
    @Transactional
    public void updateContents(Long userId, Long contentsId, String contentsDetail, String categoryName, String ipAddress) {

        //콘텐츠 널 체크
        Contents contents = contentsRepository.findById(contentsId).orElseThrow(() -> new NullPointerException("존재하지 않는 상품입니다."));

        List<Category> categoryList = new ArrayList<>();
        List<ContentsCategory> contentsCategoryList = new ArrayList<>();

        List<ContentsCategory> contentsCategoryByContents = contentsCategoryRepository.findContentsCategoryByContents(contents);
        contentsCategoryRepository.deleteAll(contentsCategoryByContents);

        //카테고리 등록을 하지 않았다면
        if (categoryName != null) {

            //카테고리 내용 가져오기
            categoryName = categoryName.replaceAll(" ", "");

            List<String> categoryNameList = new ArrayList<>(List.of(categoryName.split(",")));
            log.info(categoryNameList.toString());
            //카테고리 없는 것만 저장
            categoryList = categoryRepository.getCategoriesByCategoryNameIn(categoryNameList);

            for (Category category : categoryList) {
                categoryNameList.remove(category.getCategoryName());
            }

            for (String categoryNames : categoryNameList) {
                if (categoryNames != null && !categoryNames.equals("")) {
                    Category category = Category.builder().categoryName(categoryNames).build();
                    categoryList.add(category);
                }
            }

            categoryRepository.saveAll(categoryList);

            for (Category category : categoryList) {
                ContentsCategory contentsCategory = ContentsCategory.builder().category(category).contents(contents).build();
                contentsCategoryList.add(contentsCategory);
            }

            contentsCategoryRepository.saveAll(contentsCategoryList);
        }

        //유저 널 체크
        User user = nullCheckUser(userRepository.findById(userId));

        //콘텐츠 수정
        contents.updateContents(contentsDetail);

        //유저 콘텐츠 여부 확인 없을 시 생성
        UserContents userContents = userContentsRepository.getUserContentsByUserAndContents(user, contents).orElseGet(() -> UserContents.builder()
                .user(user)
                .contents(contents).build());

        //콘텐츠 로그 생성
        ContentsLog contentsLog = ContentsLog.builder()
                .contentsLogIp(ipAddress)
                .userContents(userContents)
                .contentsLogDetail(contentsDetail).build();

        //저장
        contentsRepository.save(contents);
        userContentsRepository.save(userContents);
        contentsLogRepository.save(contentsLog);

        Optional<RedisContents> optionalRedisContents = contentsRedisRepository.findById(contents.getId());
        optionalRedisContents.ifPresent(contentsRedisRepository::delete);

        //레디스 시작
        RedisContents redisContents = RedisContents.builder()
                .id(contentsId)
                .contentsSubject(contents.getContentsSubject())
                .contentsDetail(contentsDetail)
                .contentsUseYn(contents.getContentsUseYn())
                .regDate(contents.getRegDate())
                .modDate(contents.getModDate())
                .build();

        List<RedisCategory> redisCategoryList = new ArrayList<>();

        for (Category category : categoryList) {
            RedisCategory redisCategory = RedisCategory.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .parentId(category.getParentId())
                    .regDate(category.getRegDate())
                    .modDate(category.getModDate())
                    .build();

            redisCategoryList.add(redisCategory);
        }

        redisContents.setCategoryList(redisCategoryList);
        contentsRedisRepository.save(redisContents);
        categoryRedisRepository.saveAll(redisCategoryList);

//        ContentsLogMsgDto contentsLogMsgDto = new ContentsLogMsgDto();
//        contentsLogMsgDto.setIpAddress(ipAddress);
//        contentsLogMsgDto.setUserId(userId);
//        contentsLogMsgDto.setContentsId(contentsId);
//        contentsLogMsgDto.setContentsDetail(contentsDetail);

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("ipAddress", ipAddress);
//        jsonObject.put("userId", userId);
//        jsonObject.put("contentsId", contentsId);
//        jsonObject.put("contentsDetail", contentsDetail);

        //kafkaProducer.jsonSend("creat-contents-log", contentsLogMsgDto);
    }

    /**
     * 콘텐츠 생성
     *
     * @param userId 유저 아이디
     * @param requestContents 콘텐츠 요청 DTO
     * @param request 요청 - IP 얻기 위해
     */
    @Override
    @Transactional
    public void createContents(Long userId, RequestContents requestContents, HttpServletRequest request) {

        //유저 널 체크
        User user = nullCheckUser(userRepository.findById(userId));

        Contents contents = Contents.builder()
                .contentsSubject(requestContents.getContentsSubject())
                .contentsUseYn(true)
                .contentsDetail(requestContents.getContentsDetail())
                .build();

        //콘텐츠 생성
        UserContents userContents = UserContents.builder().contents(contents).user(user).build();

        //콘텐츠 로그
        ContentsLog contentsLog = ContentsLog.builder()
                .contentsLogIp(request.getRemoteAddr())
                .userContents(userContents)
                .contentsLogDetail(requestContents.getContentsDetail())
                .build();

        //콘텐츠 저장
        contentsRepository.save(contents);
        userContentsRepository.save(userContents);
        contentsLogRepository.save(contentsLog);
    }
}
