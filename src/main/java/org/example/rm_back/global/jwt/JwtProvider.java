package org.example.rm_back.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    public Claims validToken(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new ApiException("잘못된 토큰", ErrorType.INVALID_TOKEN,
                HttpStatus.UNAUTHORIZED); // 토큰이 잘못된 경우 예외 처리
        } catch (ExpiredJwtException e) {
            throw new ApiException("만료된 토큰", ErrorType.INVALID_TOKEN,
                HttpStatus.UNAUTHORIZED); // 토큰이 만료된 경우 예외 처리
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new ApiException("지원하지 않는 토큰", ErrorType.INVALID_TOKEN,
                HttpStatus.UNAUTHORIZED); // 지원하지 않는 토큰이거나 잘못된 형식의 경우 예외 처리
        } catch (Exception e) {
            throw new ApiException("알 수 없는 에러", ErrorType.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

}
