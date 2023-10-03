package com.kibong.shoppingwiki.contents.dto;

import lombok.Data;

@Data
public class RequestContents {
    private String contentsSubject;
    private String contentsDetail;

    public RequestContents(String contentsSubject, String contentsDetail) {
        this.contentsSubject = contentsSubject;
        this.contentsDetail = contentsDetail;
    }
}
