package org.example.rm_back.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.auth.UserPrincipal;
import org.example.rm_back.global.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest req,
        HttpServletResponse res,
        Object handler) throws Exception {

        UserPrincipal user = (UserPrincipal) req.getAttribute("currentUser");

        if (user == null) {
            res.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
            return false;
        }

        String path = req.getRequestURI();
        String method = req.getMethod();
        if (!permissionService.hasAccess(user, path, method)) {
            res.sendError(HttpStatus.FORBIDDEN.value(), "권한이 없습니다.");
            return false;
        }
        return true;
    }
}
