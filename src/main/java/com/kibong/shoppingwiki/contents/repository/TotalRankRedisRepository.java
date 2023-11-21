package com.kibong.shoppingwiki.contents.repository;

import com.kibong.shoppingwiki.contents.dto.TotalRank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TotalRankRedisRepository extends CrudRepository<TotalRank, Long> {

    List<TotalRank> findTotalRanksTop10ByOrderByCountDesc();
}
