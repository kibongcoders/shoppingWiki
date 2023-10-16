package com.kibong.shoppingwiki.category.repository;

import com.kibong.shoppingwiki.category.dto.CategoryDto;

import java.util.List;

public interface CategoryCustom {

    CategoryDto getParentCategory(Long parentId);
    CategoryDto getCategoryByName(String categoryName);

    List<CategoryDto> getChildCategoryList(Long categoryId);

}
