# CQRS vs MVC Study

이 프로젝트는 동일한 CRUD 기능을 CQRS(Command and Query Responsibility Segregation) 패턴과 MVC(Model-View-Controller) 패턴으로 각각 구현하여 두 아키텍처의 차이점을 학습하는 것을 목표로 합니다.

## 브랜치 안내

- `cqrs`: CQRS 패턴으로 구현된 코드가 있습니다. (DB 분리, 이벤트 기반 동기화 적용)
- `mvc`: 전통적인 MVC 패턴으로 구현된 코드가 있습니다.

---

## `cqrs` 브랜치 상세

`cqrs` 브랜치는 Command(명령)와 Query(조회)의 책임을 명확히 분리하고, 각 역할에 최적화된 데이터베이스를 사용하는 CQRS 패턴의 핵심 사상을 적용하여 구현되었습니다.

### 실행 환경

이 프로젝트는 `Docker`를 사용하여 실행 환경을 구성합니다. 로컬에 Docker가 설치되어 있어야 합니다.

1.  **데이터베이스 실행**

    프로젝트 루트 디렉터리에서 아래 명령어를 실행하여 쓰기 모델용 MySQL과 읽기 모델용 Elasticsearch를 실행합니다.

    ```bash
    docker-compose up -d
    ```

2.  **애플리케이션 실행**

    IDE 또는 Gradle 명령어를 사용하여 Spring Boot 애플리케이션을 실행합니다. `application.properties`에 Docker 컨테이너 접속 정보가 모두 설정되어 있습니다.

### 아키텍처

1.  **데이터베이스 분리**
    -   **쓰기 모델 (Write Model)**: 데이터의 일관성과 정합성이 중요하므로 **MySQL**을 사용합니다. 모든 생성/수정/삭제 요청은 이곳을 거칩니다.
    -   **읽기 모델 (Read Model)**: 조회 성능과 검색 기능에 최적화하기 위해 **Elasticsearch**를 사용합니다.

2.  **이벤트 기반 동기화**
    -   쓰기 모델(MySQL)에서 데이터 변경이 성공적으로 완료되면, 시스템은 `PostCreatedEvent` 같은 이벤트를 발행합니다.
    -   `PostEventListener`가 이 이벤트를 수신하여, 변경된 데이터를 읽기 모델(Elasticsearch)에 비동기적으로 반영(Projection)합니다.
    -   이 과정을 통해 두 데이터베이스 간의 **최종적 일관성(Eventual Consistency)** 을 유지합니다.

### 디렉터리 구조

```
src/main/java/practice/cqrsstudy
├── command         # Command 관련 로직
│   ├── event       # 이벤트 클래스
│   └── ...
├── domain          # 도메인 모델 (JPA Entity, Repository)
├── interfaces      # 외부와의 상호작용 (Controller)
│   └── web
└── query           # Query 관련 로직
    ├── document    # Elasticsearch Document 클래스
    ├── projection  # 이벤트 리스너 (Projection 로직)
    └── ...
```