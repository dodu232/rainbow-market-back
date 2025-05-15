package org.example.rm_back.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.entity.Admin;
import org.example.rm_back.admin.enums.AdminRole;
import org.example.rm_back.admin.service.AdminService;
import org.example.rm_back.auth.dto.AuthRequestDto;
import org.example.rm_back.auth.dto.AuthResponseDto;
import org.example.rm_back.common.BCryptEncryptor;
import org.example.rm_back.global.jwt.JwtProvider;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AdminService adminService;
    private final BCryptEncryptor encryptor;
    private final JwtProvider jwtProvider;

    public AuthResponseDto.SingIn singIn(AuthRequestDto.SignIn dto, HttpServletResponse response) {
        Admin findAdmin = adminService.getAdminByAccount(dto.getAccount());

        encryptor.isMatch(dto.getPassword(), findAdmin.getPassword());

        String[] roleArr = AdminRole.toRoleArray(findAdmin.getRoles());

        String token = jwtProvider.generateToken(findAdmin.getAccount(), roleArr);
        response.setHeader("accessToken", "Bearer " + token);

        return new AuthResponseDto.SingIn(findAdmin.getAccount(), LocalDateTime.now());
    }
}
