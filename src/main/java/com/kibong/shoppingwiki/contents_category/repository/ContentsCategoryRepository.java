package com.kibong.shoppingwiki.contents_category.repository;

import com.kibong.shoppingwiki.domain.ContentsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsCategoryRepository extends JpaRepository<ContentsCategory, Long>, ContentsCategoryCustom{
}
