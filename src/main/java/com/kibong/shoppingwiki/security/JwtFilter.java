package com.kibong.shoppingwiki.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "token";

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = resolveToken(request);


        try {
            if (token.isPresent()) {
                JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
                if (jwtAuthToken.validate()) {
                    Authentication authentication = jwtAuthTokenProvider.getAuthentication(jwtAuthToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new JwtException("만료된 토큰입니다. 다시 로그인 해주세요.");
                }
            } else {
                throw new RuntimeException("토큰이 존재하지 않습니다.");
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "만료된 토큰입니다. 다시 로그인 해주세요.");
        }
    }

    private Optional<String> resolveToken(HttpServletRequest request) {

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authToken != null){
            String jwt = authToken.replace("Bearer", "");
            log.info("authToken={}", authToken);
            log.info("jwt={}", jwt);
            if (StringUtils.hasText(jwt)) {
                return Optional.of(jwt);
            } else {
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status.value());
        jsonObject.put("message", message);

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(response.getWriter(), jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
