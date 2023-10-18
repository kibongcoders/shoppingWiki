package com.kibong.shoppingwiki.category.repository;

import com.kibong.shoppingwiki.domain.redis.RedisCategory;
import com.kibong.shoppingwiki.domain.redis.RedisContents;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRedisRepository extends CrudRepository<RedisCategory, Long> {

}
