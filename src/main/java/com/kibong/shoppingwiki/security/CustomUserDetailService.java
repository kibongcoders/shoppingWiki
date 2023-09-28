package com.kibong.shoppingwiki.security;

import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.getUserByUserEmail(userEmail);

        User user = optionalUser.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }
}
