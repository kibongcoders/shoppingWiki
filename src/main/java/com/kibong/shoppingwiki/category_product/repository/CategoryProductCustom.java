package com.kibong.shoppingwiki.category_product.repository;

import com.kibong.shoppingwiki.domain.CategoryProduct;

import java.util.Optional;

public interface CategoryProductCustom {

    Optional<CategoryProduct> getCategoryProductByName(String categoryName, String product_name);
}
