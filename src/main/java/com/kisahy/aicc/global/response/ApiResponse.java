package com.kisahy.aicc.global.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 API 응답의 표준 포맷.
 *
 * <p>성공/실패 여부, 데이터, 메시지, 타임스탬프를 일관된 구조로 반환하여
 * 프론트엔드가 응답을 예측 가능한 형태로 처리할 수 있도록 한다.</p>
 *
 * <h3>사용 예</h3>
 * <pre>
 * return ApiResponse.success(tenantDto);
 * return ApiResponse.success("생성 완료", tenantDto);
 * </pre>
 *
 * @param <T> 응답 데이터 타입
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "OK", data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }
}
