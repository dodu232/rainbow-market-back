package org.example.rm_back.common;

import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BCryptEncryptor {

    public String encrypt(String origin) {
        return BCrypt.hashpw(origin, BCrypt.gensalt());
    }

    public void isMatch(String origin, String hashed) {
        boolean matches = BCrypt.checkpw(origin, hashed);
        if (!matches) {
            throw new ApiException("비밀번호 불일치", ErrorType.INVALID_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }
}
