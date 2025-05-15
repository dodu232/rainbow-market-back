package org.example.rm_back.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.auth.UserPrincipal;
import org.example.rm_back.auth.dto.AuthRequestDto;
import org.example.rm_back.auth.dto.AuthResponseDto;
import org.example.rm_back.auth.service.AuthService;
import org.example.rm_back.global.annotation.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto.SingIn> signIn(
        @Valid @RequestBody AuthRequestDto.SignIn dto,
        HttpServletResponse response
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.singIn(dto, response));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(@AuthUser UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(service.signOut(user));
    }
}
