package com.kibong.shoppingwiki.product.service;

import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.category.service.CategoryService;
import com.kibong.shoppingwiki.category_product.repository.CategoryProductRepository;
import com.kibong.shoppingwiki.domain.Category;
import com.kibong.shoppingwiki.domain.CategoryProduct;
import com.kibong.shoppingwiki.domain.Product;
import com.kibong.shoppingwiki.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    //private CategoryService categoryService;
}
