package com.kisahy.aicc.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 기본 설정.
 *
 * <p>현재는 뼈대만 구성. 이후 단계에서 JWT 인증 필터를 추가하고
 * 엔드포인트별 권한을 상세 지정한다.</p>
 *
 * <h3>이 시스템의 인증 정책</h3>
 * <ul>
 *   <li>세션 미사용 (Stateless) — JWT 기반 인증</li>
 *   <li>CSRF 비활성 — API 서버이며 브라우저 세션이 없음</li>
 *   <li>인증 불필요 경로: /auth/**, /v3/api-docs/**, /swagger-ui/**, /actuator/health</li>
 * </ul>
 */
@Configuration
public class SecurityConfig {

    /** 비밀번호 해시 — bcrypt (cost 12 권장, Spring 기본값 10 사용) */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // 인증 불필요 경로
                .requestMatchers(
                    "/auth/**",
                    "/health/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/actuator/health"
                ).permitAll()
                // 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
            );

        // TODO: JWT 인증 필터 추가
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
