package com.kibong.shoppingwiki.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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
                }
            }
        } catch (ExpiredJwtException | AccessDeniedException | MalformedJwtException e)
        {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response,"NOT UNAUTHORIZED TOKEN");
        }
        filterChain.doFilter(request, response);
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
    }
}
