package com.kibong.shoppingwiki.category.service;

import com.kibong.shoppingwiki.domain.Category;

import java.util.Optional;

public interface CategoryService {

    Category createCategory(String categoryName);
}
