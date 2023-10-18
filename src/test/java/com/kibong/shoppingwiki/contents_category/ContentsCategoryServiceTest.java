package com.kibong.shoppingwiki.contents_category;


import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.category.repository.CategoryRedisRepository;
import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.contents.repository.ContentsRedisRepository;
import com.kibong.shoppingwiki.contents.repository.ContentsRepository;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import com.kibong.shoppingwiki.domain.Category;
import com.kibong.shoppingwiki.domain.Contents;
import com.kibong.shoppingwiki.domain.ContentsCategory;
import com.kibong.shoppingwiki.domain.redis.RedisCategory;
import com.kibong.shoppingwiki.domain.redis.RedisContents;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class ContentsCategoryServiceTest {

    @Autowired
    ContentsRepository contentsRepository;

    @Autowired
    ContentsCategoryRepository contentsCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ContentsRedisRepository contentsRedisRepository;

    @Autowired
    CategoryRedisRepository categoryRedisRepository;

    @Test
    @DisplayName("콘텐츠 서칭 서비스 테스트")
    void contentsSearchingTest(){
        //Given
        //ContentsDto contentsDto = new ContentsDto(1L, "아이폰", "아이폰 굿", false, LocalDateTime.now(), LocalDateTime.now(), null);

//        Contents contents = Contents.builder()
//                .contentsSubject("아이폰2")
//                .contentsUseYn(true)
//                .contentsDetail("아이폰2 굿")
//                .build();
//
//        contentsRepository.save(contents);
//
//        Category category = Category.builder().categoryName("헬로우").build();
//
//        categoryRepository.save(category);
//
//        ContentsCategory contentsCategory = ContentsCategory.builder().contents(contents).category(category).build();

        ContentsDto contentsDto = contentsCategoryRepository.searchContents("아이폰2");
        List<CategoryDto> categoryList = contentsCategoryRepository.getCategoryList(contentsDto.getContentsId());
        CategoryDto categoryDto = categoryList.get(0);
        List<RedisCategory> redisCategoryList = new ArrayList<>();
        RedisCategory redisCategory = RedisCategory.builder()
                .id(categoryDto.getCategoryId())
                .categoryName(categoryDto.getCategoryName())
                .regDate(categoryDto.getRegDate())
                .modDate(categoryDto.getModDate())
                .build();

        redisCategoryList.add(redisCategory);

        RedisContents redisContents = RedisContents.builder()
                .id(contentsDto.getContentsId())
                .contentsSubject(contentsDto.getContentsSubject())
                .contentsDetail(contentsDto.getContentsDetail())
                .contentsUseYn(contentsDto.getContentsUseYn())
                .regDate(contentsDto.getRegDate())
                .modDate(contentsDto.getModDate())
                .categoryList(redisCategoryList)
                .build();

        categoryRedisRepository.save(redisCategory);
        contentsRedisRepository.save(redisContents);


//
//        contentsRedisRepository.save(redisContents);

//        Optional<RedisContents> redisContentsByContentsSubject = contentsRedisRepository.findByContentsSubject("투투");
//        RedisContents redisContents = redisContentsByContentsSubject.get();
//        log.info("detail={}", redisContents.getContentsDetail());


        //given(contentsCategoryRepository.searchContents("아이폰")).willReturn(Optional.of(contentsDto));
        //ContentsCategoryService categoryService = new ContentsCategoryServiceServiceImpl(contentsCategoryRepository);

        //When
        //ContentsDto returnContents = categoryService.searchContents("아이폰");

        //Then
        //assertEquals(returnContents.getContentsSubject(), contentsDto.getContentsSubject());
    }

//    @Test
//    @DisplayName("콘텐츠 생성")
//    void createContents(){
////        Contents contents = Contents.builder().contentsDetail("아이폰 굿").contentsSubject("아이폰").contentsUseYn(true).build();
////        contentsRepository.save(contents);
//
//        ContentsCategory contentsCategory = ContentsCategory.builder().contents(contentsRepository.findById(1L).get()).build();
//        contentsCategoryRepository.save(contentsCategory);
//    }

}
