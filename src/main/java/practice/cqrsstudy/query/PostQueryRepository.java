package practice.cqrsstudy.query;

import org.springframework.stereotype.Repository;

/**
 * 조회 전용 리포지토리 인터페이스입니다.
 * 실제 CQRS 패턴에서는 JPA와 다른 기술(예: JDBC, jOOQ, MyBatis)을 사용하여
 * 조회에 최적화된 구현체를 만드는 것이 일반적입니다.
 * 이 예제에서는 개념 분리를 위해 인터페이스만 정의하고, 서비스에서는 편의상 JpaRepository를 직접 사용합니다.
 */
@Repository
public interface PostQueryRepository {
}
