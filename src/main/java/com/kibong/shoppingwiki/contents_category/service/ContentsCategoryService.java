package com.kibong.shoppingwiki.contents_category.service;

import com.kibong.shoppingwiki.contents.dto.ContentsDto;

public interface ContentsCategoryService {

    ContentsDto searchContents(String searchValue);
}
