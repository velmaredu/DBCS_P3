package com.uva.autenticacion_users.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    private static final long EXPIRE_DURATION = 24l * 60 * 60 * 1000; // 24 hour
     
    @Value("${jwt.secret}")
    private String secretKey;
     
    public String generateAccessToken(String id, String nombre, String email, String rol) {
        
        return Jwts.builder()
                .setSubject("user")
		.claim("id",id)
                .claim("name",nombre)
                .claim("email",email)
                .claim("role",rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
                 
    }
    
}
