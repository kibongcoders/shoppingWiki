package com.kibong.shoppingwiki.contents_category.repository;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.domain.Category;

import java.util.List;
import java.util.Optional;

public interface ContentsCategoryCustom {

    Optional<ContentsDto> searchContents(String searchValue);

    List<CategoryDto> getCategoryList(Long contentsId);

    List<ContentsDto> getContentsList(Long categoryId);

    List<Category> getCategoryNameList(Long contentsId);
}
