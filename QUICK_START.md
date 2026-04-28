# 관리용 웹시스템 UI 구현 완료

## 📋 구현 현황

### ✅ 생성된 파일 목록

#### 1. CSS 스타일시트
- **파일**: `src/main/resources/static/css/admin-layout.css`
- **기능**: 전체 관리 시스템의 통일된 스타일
- **특징**: 반응형 디자인, 모바일/태블릿/데스크톱 지원

#### 2. HTML 템플릿
| 파일 | 경로 | 설명 |
|------|------|------|
| 대시보드 | `templates/admin/dashboard.html` | 통계 및 최근 활동 표시 |
| 사용자 관리 | `templates/admin/user/list.html` | 사용자 검색, 목록, 관리 |
| 회원 목록 | `templates/admin/member/list.html` | 회원 정보 관리 |
| 출퇴근 관리 | `templates/admin/member/commute.html` | 출퇴근 현황 관리 |
| 설정 | `templates/admin/settings.html` | 시스템 설정 (4가지 탭) |
| 기본 레이아웃 | `templates/layout/base-layout.html` | 재사용 가능한 기본 레이아웃 |

#### 3. Java 컨트롤러
- **파일**: `src/main/java/com/megacoffee/modules/admin/AdminController.java`
- **엔드포인트**:
  - `GET /admin/dashboard` - 대시보드
  - `GET /admin/settings` - 설정
  - `GET /admin/member/list` - 회원 목록
  - `GET /admin/member/commute` - 출퇴근 관리

#### 4. 문서
- **ADMIN_UI_README.md** - 전체 UI 가이드
- **QUICK_START.md** - 빠른 시작 가이드 (이 파일)

---

## 🎨 화면 구성

### 레이아웃 구조
```
┌─────────────────────────────────────────────┐
│ ☕ MegaCoffee 관리시스템 | 대시보드 | 설정   │ ◄─ 상단 네비게이션 (고정)
├──────────┬──────────────────────────────────┤
│ 📊 대시보드│                                │
│ 👤 사용자  │   메인 콘텐츠 영역             │ 
│ 👥 회원    │   - 콘텐츠 헤더                │
│ 🚌 출퇴근  │   - 검색/필터                 │
│ ⚙️ 설정   │   - 테이블/카드                │
│ 📋 로그   │   - 페이징                    │
│           │                                │
└──────────┴──────────────────────────────────┘
│ © 2026 MegaCoffee Management System         │ ◄─ 하단 푸터
└─────────────────────────────────────────────┘
```

### 각 페이지의 특징

#### 1️⃣ 대시보드
- **4개의 통계 카드**: 사용자 수, 회원 수, 오늘 활동, 경고
- **주요 통계**: 이번 달 가입, 활성 세션, 평균 로그인
- **최근 활동 로그**: 시스템 활동 기록 표시
- **빠른 작업 링크**: 자주 사용하는 메뉴로 빠른 이동

#### 2️⃣ 사용자 관리
- **검색 필터**: 아이디, 번호로 검색
- **데이터 테이블**: 사용자 번호, 아이디, 비밀번호 상태, 마지막 로그인
- **작업 버튼**: 수정, 삭제
- **페이징**: 데이터가 많을 경우 페이징 지원

#### 3️⃣ 회원 관리
- **검색 필터**: 이름, 이메일, 번호로 검색
- **데이터 테이블**: 회원 번호, 이름, 이메일, 가입일, 상태
- **작업 버튼**: 수정, 삭제
- **상태 배지**: 활성/비활성 상태 표시

#### 4️⃣ 출퇴근 관리
- **날짜 필터**: 기간별 조회
- **상태 필터**: 출근/퇴근/대기 상태 필터
- **통계 표시**: 근무 시간 자동 계산
- **상태 아이콘**: 🟢 출근, 🔴 퇴근, 🟡 대기

#### 5️⃣ 설정
- **탭 메뉴**: 4가지 설정 카테고리
  - 일반 설정 (시스템명, 언어, 페이지 항목 수)
  - 보안 설정 (비밀번호 정책, 세션 타임아웃)
  - 이메일 설정 (SMTP 설정)
  - 백업 설정 (자동 백업 정책)

---

## 🚀 시작하기

### 1단계: 프로젝트 실행

```bash
# 프로젝트 루트에서
mvn spring-boot:run
```

### 2단계: 페이지 접속

다음 URL에서 각 페이지에 접속할 수 있습니다:

| 페이지 | URL | 설명 |
|--------|-----|------|
| 대시보드 | `http://localhost:8080/admin/dashboard` | 시스템 통계 |
| 사용자 관리 | `http://localhost:8080/admin/user/list` | 사용자 검색 및 관리 |
| 회원 관리 | `http://localhost:8080/admin/member/list` | 회원 정보 관리 |
| 출퇴근 관리 | `http://localhost:8080/admin/member/commute` | 출퇴근 현황 |
| 설정 | `http://localhost:8080/admin/settings` | 시스템 설정 |

### 3단계: 기능 테스트

각 페이지에서:
- ✅ **네비게이션 바**: 상단 메뉴로 페이지 이동
- ✅ **사이드바**: 좌측 메뉴로 빠른 이동
- ✅ **검색**: 검색 조건 입력 및 필터링
- ✅ **페이징**: 데이터 페이지 네비게이션
- ✅ **버튼**: 수정, 삭제 등 작업 버튼

---

## 💻 코드 통합 방법

### Spring Boot 컨트롤러 연결

#### 1. UserController 수정

```java
@GetMapping("/list")
public String list(
    @RequestParam(value = "searchType", required = false) String searchType,
    @RequestParam(value = "searchValue", required = false) String searchValue,
    @RequestParam(value = "page", defaultValue = "1") int page,
    Model model) {
    
    List<UserVO> userList = userService.list(searchType, searchValue, page);
    int totalCount = userService.count(searchType, searchValue);
    int totalPages = (int) Math.ceil((double) totalCount / 20);
    
    model.addAttribute("userList", userList);
    model.addAttribute("searchType", searchType);
    model.addAttribute("searchValue", searchValue);
    model.addAttribute("page", page);
    model.addAttribute("hasNext", page < totalPages);
    model.addAttribute("totalPages", totalPages);
    
    return "admin/user/list";
}
```

#### 2. 데이터 모델 준비

UserVO, MemberVO, CommuteVO 등의 모델 클래스에 다음 필드 확인:

```java
public class UserVO {
    private int seq;
    private String userId;
    private boolean passwordReset;
    private LocalDateTime lastLoginDatetime;
    // getter/setter
}

public class MemberVO {
    private int seq;
    private String name;
    private String email;
    private LocalDate joinDate;
    // getter/setter
}

public class CommuteVO {
    private int seq;
    private String memberName;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String workingHours;
    private String status; // ON, OFF, PENDING
    // getter/setter
}
```

#### 3. Thymeleaf 의존성 확인

pom.xml에 다음이 포함되어 있는지 확인:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

---

## 🎯 커스터마이징

### 색상 변경

`admin-layout.css`에서 주요 색상 변수:

```css
/* 주색상 변경 */
.navbar { background-color: #2c3e50; } /* 상단 바 색상 */
.btn-primary { background-color: #3498db; } /* 버튼 색상 */
.sidebar a.active { background-color: #3498db; } /* 활성 메뉴 색상 */
```

### 로고/브랜드명 변경

HTML 파일에서:
```html
<a href="/" class="navbar-brand">☕ MegaCoffee 관리시스템</a>
<!-- ☕ 를 다른 이모지나 로고로 변경 가능 -->
```

### 메뉴 추가

네비게이션 바와 사이드바에 메뉴 추가:

```html
<!-- 상단 네비게이션 -->
<li><a href="/admin/new-page">새 메뉴</a></li>

<!-- 좌측 사이드바 -->
<li><a href="/admin/new-page">📌 새 메뉴</a></li>
```

### 새 페이지 생성

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>새 페이지</title>
    <link rel="stylesheet" href="/css/admin-layout.css">
</head>
<body>
    <!-- 기존 dashboard.html 참고하여 구조 복사 -->
    <nav class="navbar"><!-- ... --></nav>
    <div class="main-container">
        <aside class="sidebar"><!-- ... --></aside>
        <main class="content">
            <div class="content-header">
                <h1>새 페이지</h1>
                <p>페이지 설명</p>
            </div>
            <!-- 내용 작성 -->
        </main>
    </div>
    <footer><!-- ... --></footer>
</body>
</html>
```

---

## 🔒 보안 고려사항

### 1. 인증 확인
```java
@GetMapping("/admin/**")
// Spring Security 적용 필수
// 관리자 권한 확인
```

### 2. CSRF 보호
```html
<form method="post" action="/admin/user/delete">
    <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>
    <!-- 폼 내용 -->
</form>
```

### 3. XSS 방지
```html
<!-- Thymeleaf는 기본적으로 이스케이프 처리 -->
<td th:text="${user.userId}"></td> <!-- 안전 -->
```

---

## 📱 반응형 테스트

### 브라우저 개발자 도구에서:

1. **Chrome/Firefox**: F12 → 전기 모양 아이콘 (Toggle Device Toolbar)
2. **확인 해상도**:
   - 📱 모바일: 375px (iPhone SE)
   - 📱 모바일: 768px (iPad)
   - 💻 데스크톱: 1024px 이상

---

## 🐛 문제 해결

### CSS가 적용되지 않음
- ✅ 파일 경로 확인: `/css/admin-layout.css`
- ✅ 브라우저 캐시 삭제 (Ctrl+Shift+Delete)

### 페이지가 404 에러
- ✅ URL 경로 확인 (예: `/admin/user/list`)
- ✅ 컨트롤러 매핑 확인

### 데이터가 표시되지 않음
- ✅ Model 객체에 데이터 추가 확인
- ✅ Thymeleaf 변수명 일치 확인

---

## 📚 참고 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Thymeleaf 공식 문서](https://www.thymeleaf.org)
- [Spring Security](https://spring.io/projects/spring-security)
- [Bootstrap CSS](https://getbootstrap.com)

---

## ✨ 다음 단계

1. **데이터 연동**: UserService, MemberService 등과 실제 데이터 연결
2. **기능 구현**: 검색, 필터, 페이징 로직 구현
3. **보안 강화**: Spring Security 통합
4. **성능 최적화**: 페이지 로딩 최적화
5. **테스트**: 단위 테스트, 통합 테스트 작성

---

## 📞 지원

질문이나 버그 보고는 프로젝트 이슈 페이지에서 접수해주세요.

**MegaCoffee Management System v1.0**
© 2026 All rights reserved.
