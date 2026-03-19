package com.kisahy.aicc.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger UI) 설정.
 *
 * <p>접속 경로: <code>http://localhost:8080/swagger-ui.html</code></p>
 *
 * <h3>Bearer 토큰 인증</h3>
 * <p>Swagger UI 우측 상단 "Authorize" 버튼을 통해 JWT 토큰을 입력하면
 * 이후 모든 요청에 <code>Authorization: Bearer {token}</code> 헤더가 자동 포함된다.</p>
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("AICC Platform API")
                .version("v0.0.1")
                .description("AICC 플랫폼 백엔드 API — 테넌트·사용자·권한·봇·세션 관리")
                .contact(new Contact()
                    .name("AICC Platform Team")
                    .email("dev@kisahy.com"))
            )
            // 전역 보안 요구사항 — 모든 API 에 Bearer 토큰 필요로 표시
            // 실제 인증 여부는 SecurityConfig 의 requestMatchers 에서 결정
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
