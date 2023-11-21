package com.kibong.shoppingwiki;

import com.kibong.shoppingwiki.contents.dto.TodayRank;
import com.kibong.shoppingwiki.contents.repository.TodayRankRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class ShoppingWikiApplicationTests {

    @Autowired
    TodayRankRedisRepository todayRankRedisRepository;

    @Test
    void contextLoads() {

        List<TodayRank> todayRanksTop10ByOrderByCountDesc = todayRankRedisRepository.findTodayRanksTop10ByOrderByCountDesc();
        for (TodayRank todayRank : todayRanksTop10ByOrderByCountDesc) {
            log.info("test={}", todayRank.getContentsSubject());
        }
    }

}
