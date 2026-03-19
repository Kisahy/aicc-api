package com.kisahy.aicc.global.security;

/**
 * 요청별 {@link TenantContext} 저장소.
 *
 * <p>ThreadLocal 기반이므로 HTTP 요청 하나당 하나의 컨텍스트가 유지된다.
 * JWT 인증 필터가 요청 진입 시 {@link #set} 하고 응답 직전에 {@link #clear} 해야
 * 스레드 풀 재사용 시 데이터 누수를 방지할 수 있다.</p>
 *
 * <p>주의: 비동기 처리(@Async, CompletableFuture 등)에서는 ThreadLocal 이
 * 전파되지 않는다. 필요 시 {@code TaskDecorator} 로 명시적으로 전파해야 한다.</p>
 */
public final class TenantContextHolder {

    private static final ThreadLocal<TenantContext> HOLDER = new ThreadLocal<>();

    private TenantContextHolder() {}

    public static void set(TenantContext context) {
        HOLDER.set(context);
    }

    /** 현재 요청의 컨텍스트. 인증 전이거나 인증 불필요 경로에서는 null */
    public static TenantContext get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }
}
