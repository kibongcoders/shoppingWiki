package com.kibong.shoppingwiki.contents_category;


import com.kibong.shoppingwiki.contents.repository.ContentsRepository;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import com.kibong.shoppingwiki.contents_category.service.ContentsCategoryService;
import com.kibong.shoppingwiki.contents_category.service.ContentsCategoryServiceServiceImpl;
import com.kibong.shoppingwiki.domain.Contents;
import com.kibong.shoppingwiki.domain.ContentsCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContentsCategoryServiceTest {

    @Autowired
    ContentsRepository contentsRepository;

    @Autowired
    ContentsCategoryRepository contentsCategoryRepository;

    @Test
    @DisplayName("콘텐츠 서칭 서비스 테스트")
    void contentsSearchingTest(@Mock ContentsCategoryRepository contentsCategoryRepository){
        //Given
        ContentsDto contentsDto = new ContentsDto(1L, "아이폰", "아이폰 굿", false, null);

        given(contentsCategoryRepository.searchContents("아이폰")).willReturn(Optional.of(contentsDto));
        ContentsCategoryService categoryService = new ContentsCategoryServiceServiceImpl(contentsCategoryRepository);

        //When
        ContentsDto returnContents = categoryService.searchContents("아이폰");

        //Then
        assertEquals(returnContents.getContentsSubject(), contentsDto.getContentsSubject());
    }

    @Test
    @DisplayName("콘텐츠 생성")
    void createContents(){
//        Contents contents = Contents.builder().contentsDetail("아이폰 굿").contentsSubject("아이폰").contentsUseYn(true).build();
//        contentsRepository.save(contents);

        ContentsCategory contentsCategory = ContentsCategory.builder().contents(contentsRepository.findById(1L).get()).build();
        contentsCategoryRepository.save(contentsCategory);
    }

}
