package org.example.rm_back.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AdminResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Add {

        private Long id;
        private String account;
    }

    @Getter
    @AllArgsConstructor
    public static class SignIn{
        private String account;
        private String password;
    }

}
