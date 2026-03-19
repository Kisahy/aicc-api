package com.kisahy.aicc.global.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kisahy.aicc.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

/**
 * 애플리케이션 기동 확인용 헬스체크 컨트롤러.
 *
 * <p>향후 DB 연결 상태, 외부 의존성 상태 등을 확인하는 상세 헬스체크는
 * Spring Boot Actuator 로 대체할 예정.</p>
 */
@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "애플리케이션 상태 확인")
@SecurityRequirements  // 인증 없이 호출 가능하도록 표시
public class HealthCheckController {

    @GetMapping
    @Operation(summary = "헬스체크", description = "애플리케이션이 정상 기동됐는지 확인")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.success(Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now(),
            "application", "aicc-api"
        ));
    }
}
