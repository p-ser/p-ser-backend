package com.pser.hotel.global.error;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException() {
        this("유효성 검증에 실패했습니다");
    }

    public ValidationFailedException(String message) {
        super(message);
    }
}
