# 💰 Expense Tracker Backend - Spring Boot

개인 지출 관리 시스템의 백엔드 API 서버입니다.

## 🛠 기술 스택

- **Spring Boot 3.2.0** - Java 웹 프레임워크
- **Spring Security** - 인증 및 보안
- **JWT (JSON Web Token)** - 토큰 기반 인증
- **Spring Data JPA** - ORM
- **MySQL 8** - 데이터베이스
- **Lombok** - 보일러플레이트 코드 감소
- **Gradle** - 빌드 도구

## 📋 사전 요구사항

1. **JDK 17 이상**
   ```bash
   java -version
   ```

2. **MySQL 8 설치 및 실행**
   ```bash
   # MySQL 서비스 확인
   mysql --version
   
   # MySQL 로그인
   mysql -u root -p
   ```

3. **Gradle** (프로젝트에 Gradle Wrapper 포함)

## 🚀 설치 및 실행

### 1. 데이터베이스 설정

MySQL에 접속하여 데이터베이스를 생성합니다 (선택사항, 자동 생성됨):

```sql
CREATE DATABASE expense_tracker CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. application.properties 수정

`src/main/resources/application.properties` 파일에서 데이터베이스 설정을 확인/수정:

```properties
# MySQL 설정
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=여기에_MySQL_비밀번호_입력

# JWT Secret Key (프로덕션에서는 환경변수로 관리)
jwt.secret=mySecretKeyForExpenseTrackerApplicationVeryLongSecretKey123456789
```

**중요**: `spring.datasource.password`를 본인의 MySQL root 비밀번호로 변경하세요.

### 3. 빌드 및 실행

#### Gradle Wrapper 사용 (권장)

```bash
# Windows
gradlew.bat bootRun

# Mac/Linux
./gradlew bootRun
```

#### 또는 JAR 파일 빌드 후 실행

```bash
# 빌드
./gradlew build

# 실행
java -jar build/libs/expense-tracker-1.0.0.jar
```

### 4. 서버 실행 확인

서버가 정상적으로 시작되면:

```
Started ExpenseTrackerApplication in X.XXX seconds
```

브라우저나 curl로 테스트:

```bash
curl http://localhost:8080/api/auth/login
```

## 📁 프로젝트 구조

```
src/main/java/com/expense/tracker/
├── ExpenseTrackerApplication.java  # 메인 애플리케이션
├── config/
│   └── SecurityConfig.java         # Spring Security 설정
├── controller/
│   ├── AuthController.java         # 인증 API
│   ├── ExpenseController.java      # 지출 API
│   └── BudgetController.java       # 예산 API
├── dto/
│   ├── AuthDto.java                # 인증 DTO
│   ├── ExpenseDto.java             # 지출 DTO
│   └── BudgetDto.java              # 예산 DTO
├── entity/
│   ├── User.java                   # 사용자 엔티티
│   ├── Expense.java                # 지출 엔티티
│   └── Budget.java                 # 예산 엔티티
├── repository/
│   ├── UserRepository.java
│   ├── ExpenseRepository.java
│   └── BudgetRepository.java
├── service/
│   ├── AuthService.java
│   ├── ExpenseService.java
│   └── BudgetService.java
├── security/
│   ├── JwtTokenProvider.java       # JWT 토큰 생성/검증
│   ├── JwtAuthenticationFilter.java # JWT 필터
│   ├── UserDetailsImpl.java
│   └── UserDetailsServiceImpl.java
└── exception/
    └── GlobalExceptionHandler.java  # 전역 예외 처리
```

## 🔌 API 엔드포인트

### 인증 (Authentication)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | 회원가입 | No |
| POST | `/api/auth/login` | 로그인 | No |
| POST | `/api/auth/logout` | 로그아웃 | No |

### 지출 관리 (Expenses)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/expenses` | 지출 등록 | Yes |
| GET | `/api/expenses` | 전체 지출 조회 | Yes |
| GET | `/api/expenses?startDate=2024-01-01&endDate=2024-01-31` | 기간별 지출 조회 | Yes |
| GET | `/api/expenses/{id}` | 특정 지출 조회 | Yes |
| GET | `/api/expenses/date/{date}` | 날짜별 지출 조회 | Yes |
| PUT | `/api/expenses/{id}` | 지출 수정 | Yes |
| DELETE | `/api/expenses/{id}` | 지출 삭제 | Yes |

### 예산 관리 (Budgets)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/budgets` | 예산 등록 | Yes |
| GET | `/api/budgets/{month}` | 월별 예산 조회 (예: 2024-01) | Yes |
| PUT | `/api/budgets/{id}` | 예산 수정 | Yes |

## 📝 API 사용 예시

### 1. 회원가입

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

**응답:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com"
  }
}
```

### 2. 로그인

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### 3. 지출 등록

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "date": "2024-02-13",
    "category": "음식",
    "amount": 25000,
    "description": "점심 식사"
  }'
```

### 4. 예산 설정

```bash
curl -X POST http://localhost:8080/api/budgets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "month": "2024-02",
    "totalBudget": 3000000
  }'
```

### 5. 지출 조회 (기간별)

```bash
curl -X GET "http://localhost:8080/api/expenses?startDate=2024-02-01&endDate=2024-02-29" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🔒 보안

### JWT 인증

1. 로그인/회원가입 시 JWT 토큰 발급
2. 이후 모든 요청에 `Authorization: Bearer {token}` 헤더 포함
3. 토큰 만료 시간: 24시간 (설정 가능)

### 비밀번호 암호화

- BCrypt 알고리즘 사용
- Salt 자동 생성

### CORS 설정

- `http://localhost:3000` (React 프론트엔드) 허용
- 필요시 `SecurityConfig.java`에서 추가 origin 설정 가능

## 🗄 데이터베이스 스키마

### Users 테이블
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL
);
```

### Expenses 테이블
```sql
CREATE TABLE expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount BIGINT NOT NULL,
    description VARCHAR(500),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Budgets 테이블
```sql
CREATE TABLE budgets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    month VARCHAR(7) NOT NULL,
    total_budget BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 🐛 문제 해결

### 포트 8080이 이미 사용 중

**해결 1**: `application.properties`에서 포트 변경
```properties
server.port=8081
```

**해결 2**: 사용 중인 프로세스 종료
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [프로세스ID] /F

# Mac/Linux
lsof -i :8080
kill -9 [프로세스ID]
```

### MySQL 연결 실패

1. MySQL 서비스가 실행 중인지 확인
2. `application.properties`의 username/password 확인
3. 데이터베이스 URL 확인

### JWT 토큰 에러

- 토큰이 만료되었을 수 있음 (24시간 후)
- 다시 로그인하여 새 토큰 발급

### CORS 에러

프론트엔드 URL이 `SecurityConfig.java`의 CORS 설정에 포함되어 있는지 확인:

```java
configuration.setAllowedOrigins(List.of("http://localhost:3000"));
```

## 🧪 테스트

### Postman 사용

1. Postman 설치
2. Collection 생성
3. 각 API 엔드포인트 테스트
4. Authorization 탭에서 Bearer Token 설정

### curl 사용

위의 API 사용 예시 참조

## 📦 배포

### JAR 파일 생성

```bash
./gradlew clean build
```

생성된 JAR: `build/libs/expense-tracker-1.0.0.jar`

### 실행

```bash
java -jar build/libs/expense-tracker-1.0.0.jar
```

### 환경 변수 설정 (프로덕션)

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/expense_tracker
export SPRING_DATASOURCE_USERNAME=your-username
export SPRING_DATASOURCE_PASSWORD=your-password
export JWT_SECRET=your-very-long-secret-key

java -jar expense-tracker-1.0.0.jar
```

## 🔄 프론트엔드 연동

1. 백엔드 서버 실행 (포트 8080)
2. 프론트엔드 서버 실행 (포트 3000)
3. 프론트엔드에서 백엔드 API 호출
4. JWT 토큰 자동 포함 (Axios 인터셉터)

## 📚 참고 자료

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

**Happy Coding!** 🚀
