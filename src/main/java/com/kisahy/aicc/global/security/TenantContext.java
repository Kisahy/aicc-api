package com.kisahy.aicc.global.security;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

/**
 * 요청 처리 중 현재 사용자의 테넌트 컨텍스트 정보를 담는 불변 객체.
 *
 * <p>JWT 인증 필터가 토큰을 검증한 후 이 객체를 생성하여
 * {@link TenantContextHolder} 에 저장한다. 비즈니스 로직은 이 객체를 통해
 * "누가 어느 테넌트에서 무엇을 할 수 있는가" 를 판단한다.</p>
 *
 * <h3>필드 의미</h3>
 * <ul>
 *   <li>{@code tenantId}  — 현재 접근 중인 테넌트 (회사) ID</li>
 *   <li>{@code memberId}  — 현재 로그인한 담당자 ID</li>
 *   <li>{@code tmId}      — tenant_members 행 ID (tenant + member 조합)</li>
 *   <li>{@code roleName}  — 부여된 역할명 (예: tenant_admin)</li>
 *   <li>{@code permissions} — 최종 권한 집합 (역할 기본 + 개인 오버라이드 반영 후)</li>
 * </ul>
 */
@Getter
@Builder
public class TenantContext {

    private final UUID tenantId;
    private final UUID memberId;
    private final UUID tmId;
    private final String roleName;
    private final Set<String> permissions;
}
