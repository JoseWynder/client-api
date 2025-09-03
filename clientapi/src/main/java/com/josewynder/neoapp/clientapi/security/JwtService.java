package com.josewynder.neoapp.clientapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public String generateToken(String username){
        long exp = Long.parseLong(expiration);
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(exp);
        Instant instant = expiryDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date expiryDate = Date.from(instant);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token){
        try{
            Claims claims = getClaims(token);
            return !LocalDateTime.now().isAfter(
                    claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            );
        }catch(Exception e){
            return false;
        }
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }
}

