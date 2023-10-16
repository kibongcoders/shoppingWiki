package com.kibong.shoppingwiki.category.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long parentId;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public CategoryDto() {
    }
}


