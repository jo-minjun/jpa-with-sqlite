package my.jpawithsqlite.application.persistence;

import my.jpawithsqlite.domain.TestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestJpaRepository extends JpaRepository<TestJpaEntity, Long> {

}
