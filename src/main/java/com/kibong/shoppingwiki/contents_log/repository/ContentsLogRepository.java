package com.kibong.shoppingwiki.contents_log.repository;

import com.kibong.shoppingwiki.domain.ContentsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsLogRepository extends JpaRepository<ContentsLog, Long> {
}
