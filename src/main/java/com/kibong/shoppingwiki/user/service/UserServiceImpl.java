package com.kibong.shoppingwiki.user.service;

import com.kibong.shoppingwiki.domain.User;

import com.kibong.shoppingwiki.security.JwtAuthToken;
import com.kibong.shoppingwiki.security.JwtAuthTokenProvider;
import com.kibong.shoppingwiki.security.PasswordAuthAuthenticationManager;
import com.kibong.shoppingwiki.security.PasswordAuthAuthenticationToken;
import com.kibong.shoppingwiki.user.dto.RequestLogin;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.dto.ResponseUserDto;
import com.kibong.shoppingwiki.user.dto.UserDto;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
    private final PasswordAuthAuthenticationManager authenticationManager;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    private static final Date expiredDate = Date.from(LocalDateTime.now().plusYears(1L).atZone(ZoneId.systemDefault()).toInstant());
    @Override
    public ResponseUserDto login(RequestLogin requestLogin) {
        Optional<User> optionalUser = userRepository.getUserByUserEmail(requestLogin.getUserEmail());

        String token = createJwt(requestLogin.getUserEmail(), requestLogin.getPassword());

        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setToken(token);

        return responseUserDto;
    }

    @Override
    @Transactional
    public ResponseUserDto signUp(RequestUser requestUser) {

        Optional<User> optionalUser = userRepository.getUserByUserEmailAndUserNickname(requestUser.getUserEmail(), requestUser.getUserNickname());

        if(optionalUser.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 또는 닉네임입니다.");
        }

        User user = User.builder()
                .userEmail(requestUser.getUserEmail())
                .userNickname(requestUser.getUserNickname())
                .password(passwordEncoder.encode(requestUser.getPassword()))
                .userUseYn(true).build();

        String token = createJwt(user.getUserEmail(), user.getPassword());

        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setToken(token);

        return responseUserDto;
    }

    @Override
    @Transactional
    public void updateUser(RequestUser requestUser, Long userId){

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));

        user.updateUser(requestUser, passwordEncoder);

        userRepository.save(user);
    }

    @Override
    public UserDto getUserInfo(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));

        return new UserDto(user);
    }

    //토큰 유효 여부


    public String createJwt(String userEmail, String password) {

        PasswordAuthAuthenticationToken token = new PasswordAuthAuthenticationToken(userEmail, password);
        Authentication authentication = authenticationManager.authenticate(token);
        PasswordAuthAuthenticationToken authToken = (PasswordAuthAuthenticationToken) authentication;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, String> claims = new HashMap<>();
        claims.put("id", authToken.getUserId().toString());
        claims.put("userEmail", authToken.getUserUuid());
        claims.put("role", "normal");

        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.createAuthToken(
                authentication.getName(),
                String.valueOf(authentication.getAuthorities()),
                claims,
                expiredDate
        );

        return jwtAuthToken.getToken(jwtAuthToken);
    }
    private Boolean isJwtValid(HttpHeaders headers, User user) {

        String authorToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
        String jwt = authorToken.replace("Bearer", "");


        boolean returnValue = true;

        String subject = null;

        try {
            Claims body = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody();
            subject = body.getSubject();


            log.info("subject={}",subject);
            log.info("expiration={}",body.getExpiration());
            if(!subject.equals(user.getUserUuid())) returnValue = false;
        } catch (Exception e) {
            log.error(e.getMessage());
            returnValue = false;
        }

        if(subject == null || subject.isEmpty()){
            returnValue = false;
        }

        return returnValue;
    }
}
