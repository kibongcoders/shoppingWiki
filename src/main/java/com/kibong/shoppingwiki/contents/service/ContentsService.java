package com.kibong.shoppingwiki.contents.service;

import com.kibong.shoppingwiki.contents.dto.RequestContents;
import jakarta.servlet.http.HttpServletRequest;

public interface ContentsService {

    void updateContents(Long userId, Long contentsId, String contentsDetail, HttpServletRequest request);

    void createContents(Long userId, RequestContents requestContents, HttpServletRequest request);
}
