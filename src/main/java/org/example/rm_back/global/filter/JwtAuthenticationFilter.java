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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("api/v1/auth/signin")
            || HttpMethod.OPTIONS.matches(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
        FilterChain chain)
        throws ServletException, IOException {
        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtProvider.validToken(token);
                String roles = claims.get("roles", String.class);
                if(roles == null){
                    roles = "";
                }
                UserPrincipal principal = new UserPrincipal(
                    claims.getSubject(),
                    roles
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
