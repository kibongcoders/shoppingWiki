package com.kibong.shoppingwiki.domain.redis;

import com.kibong.shoppingwiki.domain.ContentsCategory;
import com.kibong.shoppingwiki.domain.UserContents;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash(value = "contents" , timeToLive = 1209600)
public class RedisContents {

    @Id
    private Long id;

    @Indexed
    private String contentsSubject;
    private String contentsDetail;
    private Boolean contentsUseYn;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<RedisCategory> categoryList = new ArrayList<>();

    public RedisContents() {
    }

    @Builder
    public RedisContents(Long id, String contentsSubject, String contentsDetail, Boolean contentsUseYn, LocalDateTime regDate, LocalDateTime modDate, List<RedisCategory> categoryList) {
        this.id = id;
        this.contentsSubject = contentsSubject;
        this.contentsDetail = contentsDetail;
        this.contentsUseYn = contentsUseYn;
        this.regDate = regDate;
        this.modDate = modDate;
        this.categoryList = categoryList;
    }
}
