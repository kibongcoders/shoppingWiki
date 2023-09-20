package com.kibong.shoppingwiki.category_product;

import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.category_product.repository.CategoryProductRepository;
import com.kibong.shoppingwiki.category_product.service.CategoryProductService;
import com.kibong.shoppingwiki.category_product.service.CategoryProductServiceImpl;
import com.kibong.shoppingwiki.domain.Category;
import com.kibong.shoppingwiki.domain.CategoryProduct;
import com.kibong.shoppingwiki.domain.Product;
import com.kibong.shoppingwiki.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryProductServiceTest {

    @Autowired
    CategoryProductService categoryProductService;

    @Test
    @DisplayName("카테고리 상품 연동 생성")
    void createCategoryProductTest(@Mock CategoryRepository categoryRepository, @Mock ProductRepository productRepository, @Mock CategoryProductRepository categoryProductRepository){

        //Given
        Category category = Category.builder().categoryName("핸드폰").build();
        Product product = Product.builder().productName("아이폰").build();
        CategoryProduct categoryProduct = CategoryProduct.builder().category(category).product(product).build();

//        given(categoryRepository.getCategoryByCategoryName("핸드폰")).willReturn(Optional.of(category));
//        given(productRepository.getProductByProductName("아이폰")).willReturn(Optional.of(product));
        //given(categoryProductRepository.getCategoryProductByName("아이폰", "핸드폰")).willReturn(Optional.empty());

        given(categoryRepository.getCategoryByCategoryName("핸드폰")).willReturn(Optional.empty());
        given(productRepository.getProductByProductName("아이폰")).willReturn(Optional.empty());
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        given(productRepository.save(any(Product.class))).willReturn(product);
        given(categoryProductRepository.save(any(CategoryProduct.class))).willReturn(categoryProduct);

        //Save 때 any()를 해주지 않으면 인스턴스가 서로 달라 오류 발생

        CategoryProductService categoryProductService = new CategoryProductServiceImpl(categoryRepository, categoryProductRepository, productRepository);

        //when
        CategoryProduct newCategoryProduct = categoryProductService.createCategoryProduct("핸드폰", "아이폰");

        Category newCategory = categoryProduct.getCategory();
        Product newProduct = categoryProduct.getProduct();

        //Then
        assertEquals(category.getCategoryName(), newCategory.getCategoryName(), () -> "카테고리 이름이 맞지 않습니다.");
        assertEquals(product.getProductName(), newProduct.getProductName(), () -> "상품이름이 맞지 않습니다.");
        assertEquals(categoryProduct.getCategory(), newCategoryProduct.getCategory(), () -> "카테고리_상품에서 카테고리가 맞지 않습니다.");
        assertEquals(categoryProduct.getProduct(), newCategoryProduct.getProduct(), () -> "카테고리_상품에서 상품이 맞지 않습니다.");
    }

    @Test
    @DisplayName("카테고리 생성 테스트")
    void categoryTest(){
        categoryProductService.createCategoryProduct("핸드폰", "아이폰");
    }
}
