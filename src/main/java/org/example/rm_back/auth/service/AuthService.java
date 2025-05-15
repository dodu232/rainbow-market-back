package org.example.rm_back.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.entity.Admin;
import org.example.rm_back.admin.enums.AdminRole;
import org.example.rm_back.admin.service.AdminService;
import org.example.rm_back.auth.UserPrincipal;
import org.example.rm_back.auth.dto.AuthRequestDto;
import org.example.rm_back.auth.dto.AuthResponseDto;
import org.example.rm_back.common.BCryptEncryptor;
import org.example.rm_back.common.config.redis.TokenJtiListService;
import org.example.rm_back.common.config.redis.TokenListStrategyFactory;
import org.example.rm_back.common.config.redis.TokenListType;
import org.example.rm_back.global.jwt.JwtProvider;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AdminService adminService;
    private final BCryptEncryptor encryptor;
    private final JwtProvider jwtProvider;
    private final TokenListStrategyFactory strategyFactory;

    public AuthResponseDto.SingIn singIn(AuthRequestDto.SignIn dto, HttpServletResponse response) {
        Admin findAdmin = adminService.getAdminByAccount(dto.getAccount());
        encryptor.isMatch(dto.getPassword(), findAdmin.getPassword());

        String userJtiKey = "user:" + findAdmin.getAccount() + ":jti";
        String oldJti = strategyFactory.get(TokenListType.CURRENT_JTI, TokenJtiListService.class)
            .getJit(userJtiKey);

        if (oldJti != null) {
            strategyFactory.get(TokenListType.BLACKLIST)
                .addTokenToList(oldJti, userJtiKey);
        }

        String[] roleArr = AdminRole.toRoleArray(findAdmin.getRoles());

        String newJti = UUID.randomUUID().toString();
        String token = jwtProvider.generateToken(findAdmin.getAccount(), roleArr, newJti);
        strategyFactory.get(TokenListType.CURRENT_JTI)
            .addTokenToList(newJti, userJtiKey);

        response.setHeader("accessToken", "Bearer " + token);

        return new AuthResponseDto.SingIn(findAdmin.getAccount(), LocalDateTime.now());
    }

    public String signOut(UserPrincipal user) {
        Admin findAdmin = adminService.getAdminByAccount(user.account());
        String userJtiKey = "user:" + findAdmin.getAccount() + ":jti";

        String oldJti = strategyFactory.get(TokenListType.CURRENT_JTI, TokenJtiListService.class)
            .getJit(userJtiKey);

        strategyFactory.get(TokenListType.BLACKLIST).addTokenToList(oldJti, userJtiKey);

        return "signout time: " + LocalDateTime.now();
    }
}
