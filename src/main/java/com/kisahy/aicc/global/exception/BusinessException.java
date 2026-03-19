package com.kisahy.aicc.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 애플리케이션에서 발생하는 비즈니스 예외의 최상위 클래스.
 *
 * <p>도메인별 구체 예외는 이 클래스를 상속하여 정의한다. 예:
 * {@code MemberNotFoundException}, {@code TenantSuspendedException} 등.</p>
 *
 * <p>{@link GlobalExceptionHandler} 가 이 예외를 catch 하여 일관된 응답 포맷으로 반환한다.</p>
 */
@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }
}
