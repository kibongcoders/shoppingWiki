package com.kibong.shoppingwiki.contents.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "totalRank")
public class TotalRank {

    @Id
    private String contentsSubject;
    private Integer count;

    public TotalRank() {
    }

    @Builder
    public TotalRank(String contentsSubject, Integer count) {
        this.contentsSubject = contentsSubject;
        this.count = count;
    }

    public void addTodayRankCount(){
        this.count = this.count+1;
    }
}
