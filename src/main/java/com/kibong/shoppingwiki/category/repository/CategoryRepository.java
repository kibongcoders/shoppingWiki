package com.kibong.shoppingwiki.category.repository;

import com.kibong.shoppingwiki.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
