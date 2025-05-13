package org.example.rm_back.auth.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthResponseDto {

    @Getter
    @AllArgsConstructor
    public static class SingIn {

        private String account;
        private LocalDateTime signInAt;
    }
}
