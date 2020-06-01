package com.jukusoft.mmo.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

//see also: https://gitlab.com/ertantoker/tutorials/spring-boot-security-jwt-example/-/blob/master/spring-boot-authentication-service/src/main/java/de/ertantoker/tutorial/service/JwtTokenService.java
@Component
public class JwtTokenService {

    private String secret;

    private Long expiration;

    public JwtTokenService(@Value("${jwt.secret}") String secret,
                           @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(long userID, String username) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setId(Long.toString(userID))
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

}
