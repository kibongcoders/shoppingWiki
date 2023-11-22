package com.kibong.shoppingwiki.category.dto;

import com.kibong.shoppingwiki.domain.redis.RedisCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long parentId;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Long formatRegdate;
    private Long formatModDate;

    public CategoryDto() {
    }

    public CategoryDto(Long categoryId, String categoryName, Long parentId, LocalDateTime regDate, LocalDateTime modDate) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    public void convertCategory(RedisCategory redisCategory){
        this.categoryId = redisCategory.getId();
        this.categoryName = redisCategory.getCategoryName();
        this.parentId = redisCategory.getParentId();
        this.regDate = redisCategory.getRegDate();
        this.modDate = redisCategory.getModDate();
    }

    public void convertDate(){
        this.formatRegdate = this.regDate.toEpochSecond(ZoneOffset.UTC);
        this.formatModDate = this.modDate.toEpochSecond(ZoneOffset.UTC);
    }
}


