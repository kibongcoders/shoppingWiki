package com.kibong.shoppingwiki.category.repository;

import com.kibong.shoppingwiki.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustom{

    Optional<Category> getCategoryByCategoryName(String categoryName);

    List<Category> getCategoriesByCategoryNameIn(List<String> categoryNameList);
}
