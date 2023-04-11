package com.ailen.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    public AppException(String message) {
        super(message);
        this.code = 0;
    }

    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

}
