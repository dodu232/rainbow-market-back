package org.example.rm_back.global.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.auth.UserPrincipal;
import org.example.rm_back.global.jwt.JwtProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try {
                Claims claims = jwtProvider.validToken(token);
                UserPrincipal principal = new UserPrincipal(
                    claims.getSubject(),
                    claims.get("roles", String[].class)
                );
                req.setAttribute("currentUser", principal);
            } catch (JwtException e) {
                res.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
