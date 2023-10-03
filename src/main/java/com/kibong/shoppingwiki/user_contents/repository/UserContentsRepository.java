package com.kibong.shoppingwiki.user_contents.repository;

import com.kibong.shoppingwiki.domain.Contents;
import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.domain.UserContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserContentsRepository extends JpaRepository<UserContents, Long> {

    Optional<UserContents> getUserContentsByUserAndContents(User user, Contents contents);
}
