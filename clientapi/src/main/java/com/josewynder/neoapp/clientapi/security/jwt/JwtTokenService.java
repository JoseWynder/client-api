package com.josewynder.neoapp.clientapi.security.jwt;

import com.josewynder.neoapp.clientapi.security.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signing-key}")
    private String signatureKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {
        long expMinutes = Long.parseLong(expiration);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(expMinutes);
        Instant instant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(instant);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime expiration =
                    expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(expiration);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }
}
