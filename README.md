# CQRS vs MVC Study

이 프로젝트는 동일한 CRUD 기능을 CQRS(Command and Query Responsibility Segregation) 패턴과 MVC(Model-View-Controller) 패턴으로 각각 구현하여 두 아키텍처의 차이점을 학습하는 것을 목표로 합니다.

## 브랜치 안내

- `main`: 이 브랜치는 프로젝트의 개요와 브랜치 안내만을 포함하며, **어떠한 애플리케이션 코드도 포함하지 않습니다.**
- `cqrs`: CQRS 패턴으로 구현된 코드가 있습니다. (DB 분리, 이벤트 기반 동기화 적용)
- `mvc`: 전통적인 MVC 패턴으로 구현된 코드가 있습니다.

각 브랜치로 이동하여 해당 아키텍처의 구현 코드를 확인하고 학습할 수 있습니다.

## 코드 라인 수 비교 (LOC: Lines Of Code)

동일한 CRUD 기능을 구현했을 때, 두 아키텍처의 코드 라인 수를 비교한 결과입니다.

-   **MVC 브랜치 총계**: 177 라인
-   **CQRS 브랜치 총계**: 353 라인