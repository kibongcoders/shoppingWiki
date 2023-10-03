package com.kibong.shoppingwiki.contents.repository;

import com.kibong.shoppingwiki.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

}
