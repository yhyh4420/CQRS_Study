# CQRS vs MVC Study

이 프로젝트는 동일한 CRUD 기능을 CQRS(Command and Query Responsibility Segregation) 패턴과 MVC(Model-View-Controller) 패턴으로 각각 구현하여 두 아키텍처의 차이점을 학습하는 것을 목표로 합니다.

## 브랜치 안내

- `cqrs`: CQRS 패턴으로 구현된 코드가 있습니다.
- `mvc`: 전통적인 MVC 패턴으로 구현된 코드가 있습니다.

---

## `mvc` 브랜치 상세

`mvc` 브랜치는 전통적인 계층형 아키텍처인 MVC(Model-View-Controller) 패턴을 적용하여 구현되었습니다.

### 주요 특징

- **계층형 구조**: `Controller`, `Service`, `Repository`가 각자의 역할을 가지고 계층을 이루는 일반적인 구조입니다.
- **하나의 모델과 서비스**: 상태를 변경하는 로직과 데이터를 조회하는 로직이 하나의 `PostService`와 `Post` 도메인 모델을 공유합니다.
- **단순성과 빠른 개발**: 구조가 단순하고 직관적이어서 소규모 애플리케이션이나 프로토타입을 빠르게 개발하기에 적합합니다.

### 디렉터리 구조

```
src/main/java/practice/cqrsstudy
├── controller      # Controller 관련 로직
├── domain          # 도메인 모델 (Entity, Repository)
├── dto             # DTO (Data Transfer Object)
└── service         # Service 관련 로직
```

- **`controller`**: HTTP 요청을 받아 `PostService`에 비즈니스 로직 처리를 위임하고, 그 결과를 응답합니다.
- **`service`**: 게시글 생성, 수정, 삭제, 조회 등 모든 비즈니스 로직을 처리합니다.
- **`domain`**: 애플리케이션의 핵심 비즈니스 로직과 데이터를 표현하는 `Post` 엔티티와 `PostRepository`가 위치합니다.
- **`dto`**: 계층 간 데이터 전송을 위한 `PostRequestDto`와 `PostResponseDto`가 위치합니다.
