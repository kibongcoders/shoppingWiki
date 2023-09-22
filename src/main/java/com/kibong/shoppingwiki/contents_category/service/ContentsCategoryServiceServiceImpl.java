package com.kibong.shoppingwiki.contents_category.service;

import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentsCategoryServiceServiceImpl implements ContentsCategoryService {

    private final ContentsCategoryRepository contentsCategoryRepository;

    @Override
    public ContentsDto searchContents(String searchValue) {
        Optional<ContentsDto> optionalContentsDto = contentsCategoryRepository.searchContents(searchValue);

        optionalContentsDto.orElseThrow(() -> new RuntimeException("항목이 없습니다."));

        return optionalContentsDto.get();
    }
}
