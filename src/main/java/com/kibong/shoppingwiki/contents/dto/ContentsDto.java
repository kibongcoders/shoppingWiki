package com.kibong.shoppingwiki.contents.dto;

import com.kibong.shoppingwiki.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContentsDto {

    private Long contentsId;
    private String contentsSubject;
    private String contentsDetail;
    private Boolean contentsUseYn;
    List<Category> categoryList = new ArrayList<>();

    public ContentsDto() {
    }

    public ContentsDto(Long contentsId, String contentsSubject, String contentsDetail, Boolean contentsUseYn, List<Category> categoryList) {
        this.contentsId = contentsId;
        this.contentsSubject = contentsSubject;
        this.contentsDetail = contentsDetail;
        this.contentsUseYn = contentsUseYn;
        this.categoryList = categoryList;
    }
}
