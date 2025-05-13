package org.example.rm_back.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class AuthRequestDto {

    @Getter
    @AllArgsConstructor
    public static class SignIn {

        @NotBlank(message = "account 필수 입력")
        @Length(min = 4, max = 30, message = "4~30자까지 입력 가능")
        private String account;

        @NotBlank(message = "password 필수 입력")
        @Length(min = 4, max = 12, message = "4~12자까지 입력 가능")
        private String password;
    }
}
