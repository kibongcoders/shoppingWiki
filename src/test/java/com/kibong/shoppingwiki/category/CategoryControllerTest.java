package com.kibong.shoppingwiki.category;

import com.kibong.shoppingwiki.category.controller.CategoryController;
import com.kibong.shoppingwiki.category_product.service.CategoryProductService;
import com.kibong.shoppingwiki.domain.Category;
import com.kibong.shoppingwiki.domain.CategoryProduct;
import com.kibong.shoppingwiki.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryProductService categoryProductService;

    @Test
    @WithMockUser("user1")
    public void createCategory() throws Exception {

        given(categoryProductService.createCategoryProduct(any(String.class), any(String.class)))
                .willReturn(CategoryProduct
                        .builder()
                        .id(1L)
                        .category(Category.builder().categoryName("핸드폰").build())
                        .product(Product.builder().productName("아이폰").build())
                        .build());

        RequestBuilder request = MockMvcRequestBuilders.post("/category/api/createCategory")
                .param("categoryName", "핸드폰")
                .param("productName", "아이폰")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        assertEquals("{\"category_product_id\":1}", mvcResult.getResponse().getContentAsString());
    }
}
