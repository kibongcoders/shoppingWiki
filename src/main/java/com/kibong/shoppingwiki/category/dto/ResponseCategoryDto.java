package com.kibong.shoppingwiki.category.dto;

import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCategoryDto {

    CategoryDto category;
    CategoryDto parentCategory;
    List<CategoryDto> childCategoryList = new ArrayList<>();
    List<ContentsDto> relationContentsList = new ArrayList<>();
}
