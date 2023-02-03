package com.openbox.backend.support;

import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SignatureAlgorithm signatureAlgorithm;
    private final Key key;
    private final Long expTime;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") final Long expTime) {
        signatureAlgorithm = SignatureAlgorithm.HS256;
        this.key = new SecretKeySpec(DatatypeConverter.parseBase64Binary(secretKey), signatureAlgorithm.getJcaName());
        this.expTime = expTime;
    }

    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(key, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public Jws<Claims> tokenToJws(final String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String getSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰 기간 만료");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("잘못된 jwt 토큰 값");
        }
    }

    public Boolean validateExpiredToken(String token){
        return true;
    }
}
