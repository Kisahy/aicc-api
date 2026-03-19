package com.kisahy.aicc.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * JWT 관련 설정값 바인딩.
 *
 * <p>application.yml 의 <code>aicc.jwt.*</code> 값을 타입 안전하게 주입받는다.</p>
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "aicc.jwt")
public class JwtProperties {

    /** HS256 서명 비밀키 (최소 32 bytes). 운영 환경에서는 반드시 환경변수로 주입 */
    private String secret;

    /** 액세스 토큰 만료 시간 (밀리초). 기본 1시간 */
    private long accessTokenExpirationMs;

    /** 리프레시 토큰 만료 시간 (밀리초). 기본 14일 */
    private long refreshTokenExpirationMs;

    /** 토큰 발급자 (iss claim) */
    private String issuer;
}
