package com.kibong.shoppingwiki.domain.redis;

import com.kibong.shoppingwiki.domain.ContentsCategory;
import com.kibong.shoppingwiki.domain.UserContents;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash("contents")
public class RedisContents {

    @Id
    private Long id;
    private String contentsSubject;
    private String contentsDetail;
    private Boolean contentsUseYn;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public RedisContents() {
    }

    @Builder
    public RedisContents(Long id, String contentsSubject, String contentsDetail, Boolean contentsUseYn, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.contentsSubject = contentsSubject;
        this.contentsDetail = contentsDetail;
        this.contentsUseYn = contentsUseYn;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
