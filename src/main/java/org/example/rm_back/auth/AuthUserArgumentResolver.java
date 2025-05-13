package org.example.rm_back.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        return parameter.hasParameterAnnotation(AuthUser.class)
            && UserPrincipal.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws  Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        Object principal = request.getAttribute("currentUser");
        if(principal == null) {
            throw  new ApiException("인증 정보가 없습니다.", ErrorType.INVALID_PARAMETER, HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }
}
