package com.kibong.shoppingwiki.security;

import com.kibong.shoppingwiki.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken>{

    @Value("${token.secret}")
    private String base64Secret;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public JwtAuthToken createAuthToken(String id, String role, Map<String, String> claims, Date expireDate) {
        return new JwtAuthToken(id, key, role, claims, expireDate);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }

    @Override
    public Authentication getAuthentication(JwtAuthToken authToken) {
        Claims claims = authToken.getData();
        if (authToken.validate()) {
            Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(claims.get(AuthToken.AUTHORITIES_KEY, String.class)));
            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        }else{
            throw new JwtException("만료된 토큰입니다.");
        }
    }
}
