package com.kibong.shoppingwiki.category_product.service;

import com.kibong.shoppingwiki.category.repository.CategoryRepository;
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
public class CategoryProductServiceImpl implements CategoryProductService{
    private final CategoryRepository categoryRepository;

    private final CategoryProductRepository categoryProductRepository;

    private final ProductRepository productRepository;

    @Transactional
    public CategoryProduct createCategoryProduct(String categoryName, String productName) {
        //상품을 만들 때 카테고리가 이미 연결 있다면 카테고리 생성이 아닌 수정으로 유도해야한다.
        //상품을 만들 때 카테고리가 없다면 카테고리를 생성해야한다.
        //상품이 없다면 상품을 만들어야한다.
        //상품과 카테고리를 연결해야한다.
        Category category = new Category();
        Optional<Category> optionalCategory = categoryRepository.getCategoryByCategoryName(categoryName);

        if (optionalCategory.isEmpty()) {
            category = categoryRepository.save(Category.builder().categoryName(categoryName).build());
        } else {
            if (categoryProductRepository.getCategoryProductByName(productName, categoryName).isPresent()) {
                throw new RuntimeException("이미 상품에 해당 카테고리가 존재합니다. 카테고리를 확인해주세요");
            } else {
                category = optionalCategory.get();
            }
        }

        Product product = productRepository.getProductByProductName(productName).orElseGet(
                () -> productRepository.save(Product.builder().productName(productName).build()));


        CategoryProduct categoryProduct = CategoryProduct.builder().category(category).product(product).build();
        categoryProductRepository.save(categoryProduct);

        return categoryProduct;
    }
}
