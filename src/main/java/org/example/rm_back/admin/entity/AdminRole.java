package org.example.rm_back.admin.entity;

import lombok.Getter;

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
                throw new RuntimeException();
            }
        }
        return result.toString();
    }
}
