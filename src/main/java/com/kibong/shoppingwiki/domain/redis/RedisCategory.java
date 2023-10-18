package com.kibong.shoppingwiki.domain.redis;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@RedisHash(value = "category" , timeToLive = 1209600)
public class RedisCategory {

    @Id
    private Long id;
    @Indexed
    private String categoryName;
    private Long parentId;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public RedisCategory() {
    }

    @Builder
    public RedisCategory(Long id, String categoryName, Long parentId, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
