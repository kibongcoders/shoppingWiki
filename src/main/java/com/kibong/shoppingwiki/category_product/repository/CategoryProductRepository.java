package com.kibong.shoppingwiki.category_product.repository;

import com.kibong.shoppingwiki.domain.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long>, CategoryProductCustom{


}
