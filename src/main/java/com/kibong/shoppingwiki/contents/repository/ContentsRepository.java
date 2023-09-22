package com.kibong.shoppingwiki.contents.repository;

import com.kibong.shoppingwiki.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
}
