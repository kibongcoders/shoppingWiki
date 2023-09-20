package com.kibong.shoppingwiki.category.controller;

import com.kibong.shoppingwiki.category_product.service.CategoryProductService;
import com.kibong.shoppingwiki.domain.CategoryProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryProductService categoryProductService;

    @PostMapping("/api/createCategory")
    @ResponseBody
    public ResultCategoryProductDto createCategory(String categoryName, String productName){
        return new ResultCategoryProductDto(categoryProductService.createCategoryProduct(productName,categoryName));
    }

}

@Getter
class ResultCategoryProductDto{

    private final Long category_product_id;

    public ResultCategoryProductDto(CategoryProduct categoryProduct) {
        this.category_product_id = categoryProduct.getId();
    }
}
