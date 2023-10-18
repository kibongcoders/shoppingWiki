package com.kibong.shoppingwiki.contents.dto;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.domain.Category;
import com.kibong.shoppingwiki.domain.redis.RedisCategory;
import com.kibong.shoppingwiki.domain.redis.RedisContents;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContentsDto {

    private Long contentsId;
    private String contentsSubject;
    private String contentsDetail;
    private Boolean contentsUseYn;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    List<CategoryDto> categoryList = new ArrayList<>();

    public ContentsDto() {
    }

    public ContentsDto(Long contentsId, String contentsSubject, String contentsDetail, Boolean contentsUseYn, LocalDateTime regDate, LocalDateTime modDate, List<CategoryDto> categoryList) {
        this.contentsId = contentsId;
        this.contentsSubject = contentsSubject;
        this.contentsDetail = contentsDetail;
        this.contentsUseYn = contentsUseYn;
        this.regDate = regDate;
        this.modDate = modDate;
        this.categoryList = categoryList;
    }

    public void convertRedis(RedisContents redisContents){
        this.contentsId = redisContents.getId();
        this.contentsSubject = redisContents.getContentsSubject();
        this.contentsDetail = redisContents.getContentsDetail();
        this.contentsUseYn = redisContents.getContentsUseYn();
        this.regDate = redisContents.getRegDate();
        this.modDate = redisContents.getModDate();
    }

    public void addCategoryList(List<CategoryDto> categoryList){
        this.categoryList = categoryList;
    }
}
