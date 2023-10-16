package com.kibong.shoppingwiki.contents.service;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents.dto.RequestContents;
import com.kibong.shoppingwiki.contents.repository.ContentsRedisRepository;
import com.kibong.shoppingwiki.contents.repository.ContentsRepository;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import com.kibong.shoppingwiki.contents_log.repository.ContentsLogRepository;
import com.kibong.shoppingwiki.domain.*;
import com.kibong.shoppingwiki.domain.redis.RedisContents;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user_contents.repository.UserContentsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
    private final StringRedisTemplate redisTemplate;
    private final ContentsRedisRepository contentsRedisRepository;

    @Override
    public ContentsDto searchContents(String searchValue) {

        ContentsDto contentsDto = new ContentsDto();

        Optional<RedisContents> redisContentsOptional = contentsRedisRepository.getRedisContentsByContentsSubject(searchValue);

        if(redisContentsOptional.isEmpty()){
            contentsDto = contentsCategoryRepository.searchContents(searchValue);
        }else{
            contentsDto.convertRedis(redisContentsOptional.get());
        }

        if(contentsDto != null){
            List<CategoryDto> categoryList = contentsCategoryRepository.getCategoryList(contentsDto.getContentsId());
            contentsDto.setCategoryList(categoryList);
        }

        return contentsDto;
    }

    /**
     * 콘텐츠 업데이트
     *
     * @param userId         유저 아이디
     * @param contentsId     콘텐츠 아이디
     * @param contentsDetail 콘텐츠 상세
     * @param request        request
     */
    @Override
    @Transactional
    public void updateContents(Long userId, Long contentsId, String contentsDetail, HttpServletRequest request) {

        //유저 널 체크
        User user = nullCheckUser(userRepository.findById(userId));

        //콘텐츠 널 체크
        Contents contents = contentsRepository.findById(contentsId).orElseThrow(() -> new NullPointerException("존재하지 않는 상품입니다."));

        //콘텐츠 수정
        contents.updateContents(contentsDetail);

        //유저 콘텐츠 여부 확인 없을 시 생성
        UserContents userContents = userContentsRepository.getUserContentsByUserAndContents(user, contents).orElseGet(() -> UserContents.builder()
                .user(user)
                .contents(contents).build());

        //콘텐츠 로그 생성
        ContentsLog contentsLog = ContentsLog.builder()
                .contentsLogIp(request.getRemoteAddr())
                .userContents(userContents)
                .contentsLogDetail(contentsDetail).build();

        //저장
        contentsRepository.save(contents);
        userContentsRepository.save(userContents);
        contentsLogRepository.save(contentsLog);
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
