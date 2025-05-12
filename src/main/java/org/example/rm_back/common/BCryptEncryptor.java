package org.example.rm_back.common;

import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BCryptEncryptor {

    public static String encrypt(String origin) {
        return BCrypt.hashpw(origin, BCrypt.gensalt());
    }

    public static boolean isMatch(String origin, String hashed) {
        try {
            return BCrypt.checkpw(origin, hashed);
        } catch (Exception e) {
            throw new ApiException("비밀번호 불일치", ErrorType.INVALID_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }
}
