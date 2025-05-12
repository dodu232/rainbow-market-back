package org.example.rm_back.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.HttpServletResponse;
import org.example.rm_back.admin.entity.Admin;
import org.example.rm_back.admin.entity.AdminRole;
import org.example.rm_back.admin.service.AdminService;
import org.example.rm_back.auth.dto.AuthRequestDto;
import org.example.rm_back.auth.dto.AuthRequestDto.SignIn;
import org.example.rm_back.auth.dto.AuthResponseDto;
import org.example.rm_back.common.BCryptEncryptor;
import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.example.rm_back.global.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    AdminService adminService;

    @Mock
    JwtProvider jwtProvider;

    @Mock
    private BCryptEncryptor encryptor;

    @InjectMocks
    AuthService authService;

    private SignIn dto;
    private MockHttpServletResponse response;
    private String roles;

    @BeforeEach
    void setUp(){
        String account = "haha";
        String password = "hahah1212";

        dto = new SignIn(account, password);
        response = new MockHttpServletResponse();
        roles = "notice_read, notice_write";
    }

    @Test
    @DisplayName("존재하지 않는 계정으로 로그인 시 ApiException 발생")
    void signIn_whenAccountNotFound_thenThrowsApiException() {
        // given
        given(adminService.getAdminByAccount(dto.getAccount())).willThrow(new ApiException("존재하지 않는 account 입니다.", ErrorType.INVALID_PARAMETER, HttpStatus.BAD_REQUEST));

        // when & then
        assertThrows(ApiException.class, () -> authService.singIn(dto, response));
        verify(adminService, times(1)).getAdminByAccount(anyString());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 ApiException 발생")
    void signIn_whenPasswordMismatch_thenThrowsApiException() {
        // given
        Admin admin = new Admin(dto.getAccount(), "$2a$10$validHashedPassword", roles);

        given(adminService.getAdminByAccount(dto.getAccount())).willReturn(admin);

        doThrow(new ApiException(
            "비밀번호가 일치하지 않습니다.",
            ErrorType.INVALID_PARAMETER,
            HttpStatus.BAD_REQUEST
        ))
            .when(encryptor)
            .isMatch(dto.getPassword(), admin.getPassword());

        // when & then
        assertThrows(ApiException.class, () -> authService.singIn(dto, response));
        verify(adminService, times(1)).getAdminByAccount(anyString());
        verify(encryptor, times(1)).isMatch(anyString(), anyString());
        verify(jwtProvider, times(0)).generateToken(anyString(), any(String[].class));
    }

    @Test
    @DisplayName("정상 로그인 시 헤더에 토큰이 세팅되고 DTO 반환")
    void signIn_whenCredentialsValid_thenReturnsDtoAndSetsHeader() {
        // given
        Admin admin = new Admin(dto.getAccount(), "$2a$10$validHashedPassword", roles);
        given(adminService.getAdminByAccount(dto.getAccount())).willReturn(admin);

        String token = "testToken";
        given(jwtProvider.generateToken(anyString(), any(String[].class))).willReturn(token);

        // when
        AuthResponseDto.SingIn result = authService.singIn(dto, response);

        // then
        assertEquals(token, response.getHeader("accesstoken"));
        assertEquals(dto.getAccount(), result.getAccount());
        assertNotNull(result.getSignInAt());
        verify(adminService, times(1)).getAdminByAccount(anyString());
        verify(encryptor, times(1)).isMatch(anyString(), anyString());
        verify(jwtProvider, times(1)).generateToken(anyString(), any(String[].class));
    }
}
