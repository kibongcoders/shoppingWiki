package com.kibong.shoppingwiki.category.service;

import com.kibong.shoppingwiki.category.dto.ResponseCategoryDto;

public interface CategoryService {

    void addCategory(Long userId, Long contentsId, String categoryName, String contentsLogIp);

    ResponseCategoryDto getCategory(String categoryName);
}
