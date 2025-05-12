package org.example.rm_back.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtProvider {

    private final long exp;

    private final SecretKey key;

    public JwtProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.exp}") long exp
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.exp = exp;
    }

    public String generateToken(String account, String[] roleArr) {
        String roles = String.join(",", roleArr);

        return Jwts.builder()
            .subject(account)
            .claim("roles", roles)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + exp))
            .signWith(key)
            .compact();
    }
}
