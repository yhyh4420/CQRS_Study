# CQRS vs MVC Study

이 프로젝트는 동일한 CRUD 기능을 CQRS(Command and Query Responsibility Segregation) 패턴과 MVC(Model-View-Controller) 패턴으로 각각 구현하여 두 아키텍처의 차이점을 학습하는 것을 목표로 합니다.

## 브랜치 안내

- `cqrs`: CQRS 패턴으로 구현된 코드가 있습니다.
- `mvc`: 전통적인 MVC 패턴으로 구현된 코드가 있습니다.

---

## `cqrs` 브랜치 상세

`cqrs` 브랜치는 Command(명령)와 Query(조회)의 책임을 분리한 CQRS 패턴을 적용하여 구현되었습니다.

### 주요 특징

- **Command와 Query의 분리**: 상태를 변경하는 로직(Command)과 데이터를 조회하는 로직(Query)을 명확하게 분리하여 각자의 역할에 더 집중할 수 있도록 설계되었습니다.
- **서로 다른 데이터 모델**: 이 예제에서는 단순화를 위해 Command와 Query가 동일한 `Post` 도메인 모델을 공유하지만, 실제 CQRS 애플리케이션에서는 각기 다른 데이터 모델(혹은 데이터베이스)을 사용할 수 있습니다.
- **유연성 및 확장성**: Command와 Query가 분리되어 있어, 각기 다른 요구사항(예: 성능, 확장성)에 맞춰 독립적으로 최적화하고 확장하기 용이합니다.

### 디렉터리 구조

```
src/main/java/practice/cqrsstudy
├── command         # Command 관련 로직 (Service, DTO)
├── domain          # 도메인 모델 (Entity, Repository)
├── interfaces      # 외부와의 상호작용 (Controller)
│   └── web
└── query           # Query 관련 로직 (Service, DTO, Repository)
```

- **`command`**: 게시글 생성, 수정, 삭제와 같이 시스템의 상태를 변경하는 모든 로직을 포함합니다.
- **`query`**: 게시글 단건 조회, 목록 조회와 같이 데이터를 조회하는 모든 로직을 포함합니다.
- **`domain`**: 애플리케이션의 핵심 비즈니스 로직과 데이터를 표현하는 `Post` 엔티티와 `PostRepository`가 위치합니다.
- **`interfaces.web`**: HTTP 요청을 받아 Command 또는 Query 서비스에 전달하는 `PostController`가 위치합니다.
