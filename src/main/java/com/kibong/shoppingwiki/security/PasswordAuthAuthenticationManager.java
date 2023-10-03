package com.kibong.shoppingwiki.security;

import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PasswordAuthAuthenticationManager implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.getUserByUserEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new BadCredentialsException("가입되지 않은 아이디입니다."));

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        PasswordAuthAuthenticationToken token = new PasswordAuthAuthenticationToken(user.getId(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getUserGrade().name())));

        token.setUserId(user.getId());
        token.setUserUuid(user.getUserUuid());

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PasswordAuthAuthenticationToken.class);
    }
}
