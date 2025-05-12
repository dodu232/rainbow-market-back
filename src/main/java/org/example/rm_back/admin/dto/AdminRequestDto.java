package org.example.rm_back.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AdminRequestDto {

    @Getter
    @AllArgsConstructor
    public static class Add{

        @NotBlank(message = "account 필수 입력")
        private String account;

        private String[] role;

        @NotBlank(message = "password 필수 입력")
        private String password;
    }
}
