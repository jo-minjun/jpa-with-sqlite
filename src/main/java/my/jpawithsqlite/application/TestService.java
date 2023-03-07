package my.jpawithsqlite.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.jpawithsqlite.application.persistence.TestJpaRepository;
import my.jpawithsqlite.application.persistence.TestSqliteRepository;
import my.jpawithsqlite.config.SqliteDataSourceConfig;
import my.jpawithsqlite.domain.TestJpaEntity;
import my.jpawithsqlite.domain.TestSqliteEntity;
import my.jpawithsqlite.domain.TestSqliteEntitySub;
import my.jpawithsqlite.infra.TestSqliteEntityRepository;
import my.jpawithsqlite.infra.TestSqliteEntitySubRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

  private final TestJpaRepository testJpaRepository;
  private final TestSqliteRepository<TestSqliteEntity, Long> testSqliteEntityRepository = new TestSqliteEntityRepository(new JdbcTemplate(SqliteDataSourceConfig.sqliteDataSource()));
  private final TestSqliteRepository<TestSqliteEntitySub, Long> testSqliteEntitySubRepository = new TestSqliteEntitySubRepository(new JdbcTemplate(SqliteDataSourceConfig.sqliteDataSource()));

  // @Transactional
  // mySql로 테스트 결과 정상적으로 동작하나, Sqlite에서는 file is locked 익셉션이 발생한다.
  // Sqlite는 파일 전체를 lock 잡는다고 하는데, 그것과 관련이 있는 것일까?
  // 위 문제를 해결하기 위해서, 각 레포지터리 구현체에 @Transactional을 선언했다.
  public void testSqlite() {
    log.info("===============================");
    testSqliteEntityRepository.createTable();
    testSqliteEntitySubRepository.createTable();
    log.info("===============================");
    final Long hi1 = testSqliteEntityRepository.save(TestSqliteEntity.builder().name("hi").age(10).build());
    final Long hi2 = testSqliteEntityRepository.save(TestSqliteEntity.builder().name("hi").age(10).build());
    final Long hi3 = testSqliteEntitySubRepository.save(TestSqliteEntitySub.builder().home("home").build());
    log.info("===============================");
    log.info("entity id = {}", hi1);
    log.info("entity id = {}", hi2);
    log.info("entity id = {}", hi3);
    log.info("===============================");
  }

  public Long testJpa() {
    final TestJpaEntity entity = new TestJpaEntity(null,
                                                   UUID.randomUUID().toString(),
                                                   (int) (Math.random() * 10) + 10);
    return testJpaRepository.save(entity).getId();
  }
}
