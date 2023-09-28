package com.kibong.shoppingwiki.user.service;

import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.dto.UserDto;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signUp(RequestUser requestUser) {

        log.info("requestUser={}", requestUser.getPassword());

        Optional<User> optionalUser = userRepository.getUserByUserEmailAndUserNickname(requestUser.getUserEmail(), requestUser.getUserNickname());

        if(optionalUser.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 또는 닉네임입니다.");
        }

        User user = User.builder()
                .userEmail(requestUser.getUserEmail())
                .userNickname(requestUser.getUserNickname())
                .password(passwordEncoder.encode(requestUser.getPassword()))
                .userUseYn(true).build();

        return userRepository.save(user);
    }
}
