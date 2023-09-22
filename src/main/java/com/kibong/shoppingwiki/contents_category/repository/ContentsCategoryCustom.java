package com.kibong.shoppingwiki.contents_category.repository;

import com.kibong.shoppingwiki.contents.dto.ContentsDto;

import java.util.Optional;

public interface ContentsCategoryCustom {

    Optional<ContentsDto> searchContents(String searchValue);
}
