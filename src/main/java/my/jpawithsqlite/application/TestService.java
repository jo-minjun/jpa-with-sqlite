package my.jpawithsqlite.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.jpawithsqlite.application.persistence.TestJpaRepository;
import my.jpawithsqlite.application.persistence.TestSqliteRepository;
import my.jpawithsqlite.domain.TestJpaEntity;
import my.jpawithsqlite.domain.TestSqliteEntity;
import my.jpawithsqlite.domain.TestSqliteEntitySub;
import my.jpawithsqlite.infra.TestSqliteEntityRepository;
import my.jpawithsqlite.infra.TestSqliteEntitySubRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sqlite.SQLiteDataSource;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

  private final TestJpaRepository testJpaRepository;

  private final ObjectProvider<TestSqliteRepository<TestSqliteEntity, Long>> provider1;
  private final ObjectProvider<TestSqliteRepository<TestSqliteEntitySub, Long>> provider2;

  @Transactional
  public void testSqlite() {
    TestSqliteRepository<TestSqliteEntity, Long> testSqliteEntityRepository = provider1.getObject("1");
    TestSqliteRepository<TestSqliteEntitySub, Long> testSqliteEntitySubRepository = provider2.getObject("2");

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
