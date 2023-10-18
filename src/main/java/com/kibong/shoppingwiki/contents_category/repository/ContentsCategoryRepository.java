package com.kibong.shoppingwiki.contents_category.repository;

import com.kibong.shoppingwiki.domain.Contents;
import com.kibong.shoppingwiki.domain.ContentsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentsCategoryRepository extends JpaRepository<ContentsCategory, Long>, ContentsCategoryCustom{

    List<ContentsCategory> findContentsCategoryByContents(Contents contents);
}
