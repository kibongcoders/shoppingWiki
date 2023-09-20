package com.kibong.shoppingwiki.category_product.service;

import com.kibong.shoppingwiki.domain.CategoryProduct;
import com.kibong.shoppingwiki.domain.Product;

public interface CategoryProductService {

    CategoryProduct createCategoryProduct(String categoryName, String productName);
}
