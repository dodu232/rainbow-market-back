package org.example.rm_back.admin.entity;

import lombok.Getter;
import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.springframework.http.HttpStatus;

@Getter
public enum AdminRole {
    SUPER,
    NOTICE_READ,
    NOTICE_WRITE,
    QNA_READ,
    QNA_WRITE,
    USER_READ,
    ORDER_READ,
    ORDER_WRITE,
    PRODUCT_READ,
    PRODUCT_WRITE;

    public static String toAdminRole(String[] roles){
        StringBuilder result = new StringBuilder();
        for(String s:roles){
            try{
                result.append(AdminRole.valueOf(s.toUpperCase())).append(", ");
            } catch (IllegalArgumentException e){
                throw new ApiException("올바르지 않은 USER ROLE", ErrorType.INVALID_PARAMETER, HttpStatus.BAD_REQUEST);
            }
        }
        return result.toString();
    }
}
