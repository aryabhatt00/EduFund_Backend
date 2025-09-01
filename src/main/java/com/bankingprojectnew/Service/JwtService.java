package com.bankingprojectnew.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Secret key for signing JWTs (must be kept safe in production)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Validity: 1 day (in milliseconds)
    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

}
