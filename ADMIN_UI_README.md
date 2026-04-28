# MegaCoffee 관리 시스템 웹 UI 레이아웃

## 개요
전형적인 관리용 웹시스템의 화면 구성으로, **상단 메뉴 + 좌측 사이드바 + 메인 콘텐츠 + 하단 푸터** 구조를 제공합니다.

## 디렉토리 구조
```
src/main/resources/
├── static/
│   └── css/
│       └── admin-layout.css          # 통합 스타일시트
└── templates/
    ├── layout/
    │   └── base-layout.html          # 기본 레이아웃 템플릿 (재사용 가능)
    └── admin/
        ├── dashboard.html            # 대시보드
        ├── settings.html             # 설정
        ├── user/
        │   └── list.html             # 사용자 관리 목록
        └── member/
            ├── list.html             # 회원 목록
            └── commute.html          # 출퇴근 관리
```

## 구성 요소

### 1. 상단 네비게이션 바 (Navbar)
- **위치**: 페이지 최상단, 고정(sticky)
- **구성**: 
  - 좌측: 시스템 브랜드명
  - 중앙: 주요 메뉴 (대시보드, 사용자 관리, 회원 관리, 설정)
  - 우측: 현재 사용자 정보, 로그아웃 버튼
- **높이**: 60px
- **색상**: 진한 파란색 (#2c3e50)

### 2. 좌측 사이드바 (Sidebar)
- **위치**: 메인 컨테이너의 좌측
- **너비**: 200px
- **구성**:
  - 섹션별 메뉴 그룹 (관리 메뉴, 회원 관리, 시스템)
  - 활성 메뉴는 파란색 배경 + 좌측 보더
  - 아이콘 + 텍스트 조합
- **특징**:
  - 스크롤 가능
  - 현재 페이지 활성화 표시

### 3. 메인 콘텐츠 영역 (Content)
- **위치**: 사이드바 우측
- **구성**:
  - 콘텐츠 헤더 (제목 + 설명)
  - 검색/필터 섹션 (필요시)
  - 데이터 테이블 또는 형식
  - 페이징 네비게이션
- **배경**: 흰색
- **패딩**: 25px

### 4. 하단 푸터 (Footer)
- **위치**: 페이지 최하단
- **높이**: 자동 (콘텐츠 높이에 따라)
- **색상**: 상단 네비게이션과 동일 (#2c3e50)
- **내용**: 저작권 정보

## 스타일 특징

### 색상 팔레트
- **주색상**: #3498db (파란색) - 버튼, 활성 상태
- **배경**: #f5f5f5 (연한 회색) - 전체 배경
- **텍스트**: #333 (진회색) - 일반 텍스트
- **보조**: #27ae60 (초록색) - 성공/긍정
- **경고**: #e74c3c (빨강색) - 삭제/경고

### 반응형 디자인
- **태블릿 (768px 이상)**: 정상 레이아웃
- **모바일 (768px 이하)**: 
  - 사이드바가 수평 메뉴로 변환
  - 단일 컬럼 레이아웃
  - 검색 폼이 세로 정렬

## 페이지별 설명

### 1. 대시보드 (Dashboard)
```
/admin/dashboard
```
- **목적**: 시스템 전체 통계 및 최근 활동 표시
- **구성**:
  - 대시보드 카드 (4개의 주요 통계)
  - 주요 통계 섹션
  - 최근 활동 로그
  - 빠른 작업 링크

### 2. 사용자 관리 (User List)
```
/admin/user/list
```
- **목적**: 시스템 사용자 관리
- **기능**:
  - 검색 필터 (아이디, 번호)
  - 테이블 형식 데이터 표시
  - 수정/삭제 버튼
  - 페이징 네비게이션

### 3. 회원 관리 (Member List)
```
/admin/member/list
```
- **목적**: 시스템 회원 관리
- **기능**:
  - 검색 필터 (이름, 이메일, 번호)
  - 테이블 형식 데이터 표시
  - 상태 표시기
  - 수정/삭제 버튼

### 4. 출퇴근 관리 (Commute)
```
/admin/member/commute
```
- **목적**: 회원의 출퇴근 현황 관리
- **기능**:
  - 날짜 범위 필터
  - 상태 필터 (출근/퇴근)
  - 근무 시간 표시
  - 상태 배지 (출근/퇴근/대기)

### 5. 설정 (Settings)
```
/admin/settings
```
- **목적**: 시스템 전체 설정 관리
- **탭 메뉴**:
  1. 일반 설정 (시스템 이름, 언어 등)
  2. 보안 설정 (비밀번호, 세션 정책)
  3. 이메일 설정 (SMTP 설정)
  4. 백업 설정 (자동 백업 정책)

## 사용 방법

### 1. CSS 연결
모든 HTML 파일에서:
```html
<link rel="stylesheet" href="/css/admin-layout.css">
```

### 2. 네비게이션 메뉴 활성화
현재 페이지에 해당하는 메뉴에 `class="active"` 추가:
```html
<a href="/admin/user/list" class="active">사용자 관리</a>
```

### 3. 사이드바 활성화
현재 페이지에 해당하는 사이드바 메뉴에 `class="active"` 추가:
```html
<li><a href="/admin/user/list" class="active">👤 사용자 목록</a></li>
```

### 4. 새로운 페이지 추가
```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>페이지 제목</title>
    <link rel="stylesheet" href="/css/admin-layout.css">
</head>
<body>
    <!-- 네비게이션 바 복사 -->
    <nav class="navbar">
        <!-- ... -->
    </nav>

    <!-- 메인 컨테이너 -->
    <div class="main-container">
        <!-- 사이드바 복사 -->
        <aside class="sidebar">
            <!-- ... -->
        </aside>

        <!-- 콘텐츠 영역 -->
        <main class="content">
            <!-- 페이지 콘텐츠 -->
        </main>
    </div>

    <!-- 푸터 복사 -->
    <footer>
        <!-- ... -->
    </footer>
</body>
</html>
```

## 컴포넌트 클래스

### 버튼
```html
<button class="btn btn-primary">기본 버튼</button>
<button class="btn btn-success">성공 버튼</button>
<button class="btn btn-danger">삭제 버튼</button>
<button class="btn btn-secondary">보조 버튼</button>
<button class="btn btn-sm btn-primary">작은 버튼</button>
```

### 테이블
```html
<div class="table-container">
    <table>
        <thead>
            <tr>
                <th>헤더 1</th>
                <th>헤더 2</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>데이터 1</td>
                <td>데이터 2</td>
            </tr>
        </tbody>
    </table>
</div>
```

### 알림 메시지
```html
<div class="alert alert-success">성공 메시지</div>
<div class="alert alert-error">오류 메시지</div>
<div class="alert alert-warning">경고 메시지</div>
<div class="alert alert-info">정보 메시지</div>
```

### 검색 폼
```html
<div class="search-section">
    <form method="get" action="/path" class="search-form">
        <select name="searchType">
            <option>-- 검색 유형 --</option>
        </select>
        <input type="text" name="searchValue" placeholder="검색어">
        <button type="submit">🔍 검색</button>
        <button type="button" class="reset">초기화</button>
    </form>
</div>
```

## Thymeleaf 통합 팁

### 현재 사용자 표시
```html
<span th:text="${#authentication.principal.username}"></span>
```

### 날짜 포맷
```html
<td th:text="${#dates.format(user.lastLoginDatetime, 'yyyy-MM-dd HH:mm')}"></td>
```

### 조건부 렌더링
```html
<div th:if="${userList != null and userList.size() > 0}">
    데이터가 있을 때
</div>
<div th:unless="${userList != null and userList.size() > 0}">
    데이터가 없을 때
</div>
```

## 지원하는 브라우저
- Chrome (최신 버전)
- Firefox (최신 버전)
- Safari (최신 버전)
- Edge (최신 버전)

## 반응형 지원
- 데스크톱: 1024px 이상
- 태블릿: 768px ~ 1024px
- 모바일: 768px 이하

## 라이선스
© 2026 MegaCoffee Management System
