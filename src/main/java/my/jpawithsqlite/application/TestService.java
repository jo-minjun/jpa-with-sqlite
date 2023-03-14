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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

  private final TestJpaRepository testJpaRepository;
  private final static TestSqliteRepository<TestSqliteEntity, Long> testSqliteEntityRepository;
  private final static TestSqliteRepository<TestSqliteEntitySub, Long> testSqliteEntitySubRepository;

  static {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(SqliteDataSourceConfig.sqliteDataSource());
    testSqliteEntityRepository = new TestSqliteEntityRepository(jdbcTemplate);
    testSqliteEntitySubRepository = new TestSqliteEntitySubRepository(jdbcTemplate);
  }


   @Transactional
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
