package com.kibong.shoppingwiki.contents.repository;

import com.kibong.shoppingwiki.domain.redis.RedisContents;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContentsRedisRepository extends CrudRepository<RedisContents, Long> {


    Optional<RedisContents> getRedisContentsByContentsSubject(String searchValue);
}
