package com.kisahy.aicc.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kisahy.aicc.global.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 전역 예외 핸들러.
 *
 * <p>컨트롤러에서 발생하는 예외를 일관된 응답 포맷으로 변환한다.
 * 비즈니스 예외는 지정된 HTTP 상태로, 검증 예외는 400 으로 반환한다.</p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 비즈니스 로직 예외 */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        log.warn("[BusinessException] {}", ex.getMessage());
        return ResponseEntity
            .status(ex.getStatus())
            .body(ApiResponse.fail(ex.getMessage()));
    }

    /** @Valid 검증 실패 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
        MethodArgumentNotValidException ex
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .orElse("요청 형식이 올바르지 않습니다.");
        log.warn("[ValidationException] {}", message);
        return ResponseEntity.badRequest().body(ApiResponse.fail(message));
    }

    /** 나머지 모든 예외 — 운영에서는 스택트레이스 노출 금지 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknownException(Exception ex) {
        log.error("[UnhandledException]", ex);
        return ResponseEntity
            .internalServerError()
            .body(ApiResponse.fail("서버 내부 오류가 발생했습니다."));
    }
}
