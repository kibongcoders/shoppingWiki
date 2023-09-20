package com.kibong.shoppingwiki.category.service;

import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.domain.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String categoryName) {

        Category category = categoryRepository.getCategoryByCategoryName(categoryName).orElseGet(() -> {
            Category newCategory = Category.builder().categoryName(categoryName).build();
            return categoryRepository.save(newCategory);
        });

        return categoryRepository.save(category);
    }
}
