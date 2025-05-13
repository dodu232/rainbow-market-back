package org.example.rm_back.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {

    private final String message;
    private final ErrorType errorType;
    private final HttpStatus httpStatus;
}
