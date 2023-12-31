package com.kibong.shoppingwiki.security;

import com.kibong.shoppingwiki.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthToken implements AuthToken<Claims>{

    private final String token;
    private final Key key;

    public JwtAuthToken(String id, Key key, String role, Map<String, String> claims, Date expireDate) {
        this.key = key;
        this.token = createJwtAuthToken(id, role, claims, expireDate).get();
    }

    public String getToken(JwtAuthToken token){
        return token.token;
    }

    public Optional<String> createJwtAuthToken(String id, String role, Map<String, String> claimsMap, Date expireDate) {
        Claims claims = new DefaultClaims();
        claims.put(JwtAuthToken.AUTHORITIES_KEY, role);
        return Optional.ofNullable(
                Jwts.builder()
                        .setSubject(id)
                        .addClaims(claims)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .setExpiration(expireDate)
                        .compact()
        );
    }

    @Override
    public boolean validate() {

        boolean validation = true;

        Claims claims = getData();
        Date expireTime = claims.getExpiration();
        log.info("subject = {}", claims.getSubject());
        validation = new Date().before(Date.from(expireTime.toInstant()));
        return validation;
    }

    @Override
    public Claims getData(){

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
