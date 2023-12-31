package com.kibong.shoppingwiki.contents.service;

import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents.dto.RequestContents;
import jakarta.servlet.http.HttpServletRequest;

public interface ContentsService {

    ContentsDto searchContents(String searchValue);

    void updateContents(Long userId, Long contentsId, String contentsDetail,String categoryName ,String ipAddress);

    void createContents(Long userId, RequestContents requestContents, HttpServletRequest request);
}
