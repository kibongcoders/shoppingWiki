package com.kibong.shoppingwiki.user.repository;

import com.kibong.shoppingwiki.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUserEmailAndUserNickname(String userEmail, String userNickname);

    Optional<User> getUserByUserEmail(String userEmail);

    Optional<User> getUserByUserUuid(String userUuid);
}
