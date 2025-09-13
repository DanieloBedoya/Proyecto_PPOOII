package com.Proyecto.PPOOII.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("PPOOII-System")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(SECRET_KEY)
                .compact();
    }

    public Key getSecretKey() {
        return SECRET_KEY;
    }
}
