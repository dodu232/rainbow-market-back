package org.example.rm_back.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Add {

        private Long id;
        private String account;
    }

}
