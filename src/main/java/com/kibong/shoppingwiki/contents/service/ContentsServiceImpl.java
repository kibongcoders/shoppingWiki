package com.kibong.shoppingwiki.contents.service;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.category.repository.CategoryRedisRepository;
import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents.dto.RequestContents;
import com.kibong.shoppingwiki.contents.repository.ContentsRedisRepository;
import com.kibong.shoppingwiki.contents.repository.ContentsRepository;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import com.kibong.shoppingwiki.contents_log.repository.ContentsLogRepository;
import com.kibong.shoppingwiki.domain.*;
import com.kibong.shoppingwiki.domain.redis.RedisCategory;
import com.kibong.shoppingwiki.domain.redis.RedisContents;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user_contents.repository.UserContentsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private final StringRedisTemplate redisTemplate;

    private final ContentsRedisRepository contentsRedisRepository;
    private final CategoryRedisRepository categoryRedisRepository;

    @Override
    public ContentsDto searchContents(String searchValue) {

        ContentsDto contentsDto = new ContentsDto();

        Optional<RedisContents> redisContentsOptional = contentsRedisRepository.findByContentsSubject(searchValue);

        //레디스 콘텐츠가 없는 경우
        if(redisContentsOptional.isEmpty()){
            contentsDto = contentsCategoryRepository.searchContents(searchValue);

            RedisContents redisContents = RedisContents.builder()
                    .id(contentsDto.getContentsId())
                    .contentsSubject(contentsDto.getContentsSubject())
                    .contentsDetail(contentsDto.getContentsDetail())
                    .contentsUseYn(contentsDto.getContentsUseYn())
                    .regDate(contentsDto.getRegDate())
                    .modDate(contentsDto.getModDate())
                    .build();


            if (contentsDto != null) {
                List<CategoryDto> categoryList = contentsCategoryRepository.getCategoryList(contentsDto.getContentsId());
                contentsDto.setCategoryList(categoryList);
                List<RedisCategory> redisCategoryList = new ArrayList<>();
                for (CategoryDto categoryDto : categoryList) {

                    redisCategoryList.add(RedisCategory.builder()
                            .categoryName(categoryDto.getCategoryName())
                            .id(categoryDto.getCategoryId())
                            .parentId(categoryDto.getParentId())
                            .regDate(categoryDto.getRegDate())
                            .modDate(categoryDto.getModDate())
                            .build());
                }

                redisContents.setCategoryList(redisCategoryList);
                categoryRedisRepository.saveAll(redisCategoryList);
            }

            contentsRedisRepository.save(redisContents);

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
