# aicc-api

AICC 플랫폼 백엔드 API 서버

## 기술 스택

| 구분 | 선택 |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.3.5 |
| Build Tool | Gradle 8.10.2 (Groovy DSL) |
| DB | MariaDB 10.11 |
| ORM | Spring Data JPA + Hibernate |
| Security | Spring Security + JWT (jjwt 0.12.x) |
| API Docs | springdoc-openapi 2.6.0 (Swagger UI) |

## 사전 준비

### 1. aicc-infra 컨테이너 실행

이 서버는 `aicc-infra` 의 MariaDB 컨테이너에 접속합니다.
먼저 `aicc-infra` 프로젝트에서 컨테이너를 실행하세요.

```bash
cd ../aicc-infra
docker compose up -d
```

### 2. DB 접속 정보

`src/main/resources/application-local.yml` 에 DB 접속 정보가 설정되어 있습니다.
`aicc-infra/.env` 의 계정 정보와 일치해야 합니다.

| 항목 | 값 |
|---|---|
| URL | `jdbc:mariadb://127.0.0.1:3306/aicc_platform` |
| User | `aicc_app` |
| Password | `aicc_app_1234!` |

## 실행

### IntelliJ IDEA

1. **File → Open** → `aicc-api` 디렉토리 선택
2. Gradle 싱크 자동 완료 대기
3. `AicCApiApplication` 클래스 우클릭 → Run
4. 실행 시 프로필이 `local` 로 설정되는지 확인 (application.yml 기본값)

### 커맨드라인

```bash
cd /Users/kisahy/Workspace/aicc/aicc-api

# 최초 1회 — gradlew 에 실행권한 부여
chmod +x gradlew

# 실행
./gradlew bootRun

# 빌드만
./gradlew build
```

## 동작 확인

서버 기동 후 아래 엔드포인트로 확인합니다.

| 확인 항목 | URL |
|---|---|
| 헬스체크 | http://localhost:8080/health |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI 스펙 | http://localhost:8080/v3/api-docs |

## 프로젝트 구조

```
aicc-api/
├── build.gradle                    # 의존성 정의
├── settings.gradle
├── gradle/wrapper/                 # Gradle Wrapper (8.10.2)
├── gradlew, gradlew.bat            # Wrapper 실행 스크립트
├── src/
│   ├── main/
│   │   ├── java/com/kisahy/aicc/
│   │   │   ├── AicCApiApplication.java    # 메인 진입점
│   │   │   ├── global/                    # 공통 인프라
│   │   │   │   ├── config/                # Security, OpenAPI, JWT 설정
│   │   │   │   ├── security/              # TenantContext
│   │   │   │   ├── controller/            # 헬스체크
│   │   │   │   ├── exception/             # 전역 예외 처리
│   │   │   │   └── response/              # ApiResponse
│   │   │   ├── auth/                      # 인증 도메인 (로그인)
│   │   │   ├── tenant/                    # 테넌트 도메인
│   │   │   ├── member/                    # 사용자 도메인
│   │   │   ├── role/                      # 역할 도메인
│   │   │   └── relation/                  # 소속·권한 연결 도메인
│   │   └── resources/
│   │       ├── application.yml            # 공통 설정
│   │       └── application-local.yml      # 로컬 DB 접속 정보
│   └── test/java/com/kisahy/aicc/
└── README.md
```

## 각 도메인 패키지 구조

모든 도메인은 아래 패턴을 따릅니다.

```
{domain}/
├── controller/   — REST API 엔드포인트
├── service/      — 비즈니스 로직
├── repository/   — JPA Repository
├── entity/       — JPA 엔티티
└── dto/          — 요청/응답 객체
```

## 구현 상태

### 완료
- 프로젝트 초기 구성 (Gradle, Spring Boot 3.3, Java 21)
- Security 뼈대 (세션리스, CSRF 비활성)
- OpenAPI/Swagger UI 설정
- TenantContext 및 ThreadLocal 저장소
- 공통 응답 포맷 (ApiResponse)
- 전역 예외 처리
- JWT 설정값 바인딩 (JwtProperties)
- 헬스체크 API

### 예정
- JPA 엔티티 5종 (Tenant, Member, TenantRole, TenantMember, MemberPermission)
- JWT 토큰 발급/검증 서비스
- 인증 필터 + TenantContext 주입
- 로그인 API (`POST /auth/login`)
- 테넌트·멤버 관리 API

## 주의 사항

- `application.yml` 의 `aicc.jwt.secret` 은 개발용 값입니다.
  운영 환경에서는 반드시 환경변수나 별도 프로필로 주입하세요.
- JPA `ddl-auto` 는 `validate` 로 고정되어 있습니다.
  스키마는 `aicc-infra` 의 DDL 로만 관리하며, 애플리케이션에서 스키마를 변경하지 않습니다.
