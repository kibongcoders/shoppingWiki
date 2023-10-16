package com.kibong.shoppingwiki.category.controller;

import com.kibong.shoppingwiki.category.dto.ResponseCategoryDto;
import com.kibong.shoppingwiki.category.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory/{contentsId}/{categoryName}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addCategory(@PathVariable Long contentsId, @PathVariable String categoryName
            , HttpServletRequest request
    ) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        categoryService.addCategory(userId, contentsId, categoryName, request.getRemoteAddr());
    }

    /**
     * 카테고리 가져오기
     *
     * @param categoryName
     * @return
     */
    @GetMapping("/getCategory/{categoryName}")
    public ResponseCategoryDto getCategory(@PathVariable String categoryName) {

        return categoryService.getCategory(categoryName);
    }
}

