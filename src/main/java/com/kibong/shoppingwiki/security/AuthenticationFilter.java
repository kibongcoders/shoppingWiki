package com.kibong.shoppingwiki.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.user.dto.RequestLogin;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserRepository userRepository;
    private final Environment env;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {


        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUserEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userEmail = authResult.getName();

        Optional<User> optionalUser = userRepository.getUserByUserEmail(userEmail);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 userEmail 입니다 userEmail : " + userEmail));
        log.info("user id {}", user.getId());

        String token = Jwts.builder()
                .setSubject(user.getUserUuid())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userUuid", user.getUserUuid());
    }
}
