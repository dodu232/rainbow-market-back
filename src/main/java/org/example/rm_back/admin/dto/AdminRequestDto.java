package org.example.rm_back.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class AdminRequestDto {

    @Getter
    @AllArgsConstructor
    public static class Add{

        @NotBlank(message = "account 필수 입력")
        @Length(min = 4, max = 30, message = "4~30자까지 입력 가능")
        private String account;

        private String[] role;

        @NotBlank(message = "password 필수 입력")
        @Length(min = 4, max = 12, message = "4~12자까지 입력 가능")
        private String password;
    }
}
