package com.kibong.shoppingwiki.user.service;

import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.security.*;
import com.kibong.shoppingwiki.user.UserGrade;
import com.kibong.shoppingwiki.user.dto.RequestLogin;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.dto.ResponseUserDto;
import com.kibong.shoppingwiki.user.dto.UserDto;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final PasswordAuthAuthenticationManager authenticationManager;
    private final RefreshTokenAuthenticationManager refreshTokenAuthenticationManager;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    private static final Date expiredDate = Date.from(LocalDateTime.now().plusWeeks(2L).atZone(ZoneId.systemDefault()).toInstant());
    private static final Date expiredTime = Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant());

    /**
     * 로그인
     *
     * @param requestLogin 요청 로그인 Dto
     * @return 응답 유저 Dto
     */
    @Override
    @Transactional
    public ResponseUserDto login(RequestLogin requestLogin) {

        User user = userRepository.getUserByUserEmail(requestLogin.getUserEmail())
                .orElseThrow(() -> new BadCredentialsException("가입되지 않은 아이디입니다."));

        String token = createJwt(user.getId(), requestLogin.getPassword(), expiredTime);
        String refreshToken = createJwt(user.getId(), requestLogin.getPassword(), expiredDate);

        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setToken(token);
        responseUserDto.setUserId(user.getId());
        responseUserDto.setRefreshToken(refreshToken);

        return responseUserDto;
    }

    /**
     * 회원가입
     *
     * @param requestUser 유저 요청 Dto
     * @return 응답 요청 Dto
     */
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
                .userGrade(UserGrade.NORMAL)
                .userUseYn(true).build();

        user = userRepository.save(user);

        String token = createJwt(user.getId(), requestUser.getPassword(), expiredTime);
        String refreshToken = createJwt(user.getId(), requestUser.getPassword(), expiredDate);

        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setToken(token);
        responseUserDto.setUserId(user.getId());
        responseUserDto.setRefreshToken(refreshToken);

        return responseUserDto;
    }

    /**
     * 유저 업데이트
     *
     * @param requestUser 유저 요청 Dto
     * @param userId      유저 아이디
     */
    @Override
    @Transactional
    public void updateUser(RequestUser requestUser, Long userId){

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));

        user.updateUser(requestUser, passwordEncoder);

        userRepository.save(user);
    }

    /**
     * 유저 정보 가져오기
     *
     * @param userId 유저 아이디
     * @return UserDto
     */
    @Override
    public UserDto getUserInfo(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));

        return new UserDto(user);
    }

    @Override
    public ResponseUserDto getToken(Long userId, String refreshToken) {

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));

        ResponseUserDto responseUserDto = new ResponseUserDto();
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(user.getRefreshToken());

        if(!refreshToken.equals(user.getRefreshToken())){
            throw new JwtException("토큰이 일치하지 않습니다. 다시 로그인 해주세요.");
        }

        if(!jwtAuthToken.validate()){
            throw new JwtException("만료된 토큰입니다. 다시 로그인 해주세요");
        }

        String token = createJwtByRefreshToken(user.getId(), user.getPassword(), expiredTime);

        responseUserDto.setToken(token);
        responseUserDto.setUserId(user.getId());

        return responseUserDto;
    }

    /**
     * 토큰 생성
     *
     * @param userId 유저 아이디
     * @param password  유저 패스워드
     * @return 토큰
     */
    public String createJwt(Long userId, String password, Date expire) {

        PasswordAuthAuthenticationToken token = new PasswordAuthAuthenticationToken(userId, password);
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
                expire
        );

        return jwtAuthToken.getToken(jwtAuthToken);
    }

    public String createJwtByRefreshToken(Long userId, String password, Date expire){
        PasswordAuthAuthenticationToken token = new PasswordAuthAuthenticationToken(userId, password);
        Authentication authentication = refreshTokenAuthenticationManager.authenticate(token);

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
                expire
        );

        return jwtAuthToken.getToken(jwtAuthToken);
    }
}
