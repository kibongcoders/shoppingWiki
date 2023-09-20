package com.kibong.shoppingwiki.category;

import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.category.service.CategoryService;
import com.kibong.shoppingwiki.category.service.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void createCategory(){
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        categoryService.createCategory("핸드폰");
    }
}
