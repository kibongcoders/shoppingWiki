package com.kibong.shoppingwiki.contents.repository;

import com.kibong.shoppingwiki.contents.dto.TodayRank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TodayRankRedisRepository extends CrudRepository<TodayRank, Long> {

    List<TodayRank> findTodayRanksTop10ByOrderByCountDesc();
}
