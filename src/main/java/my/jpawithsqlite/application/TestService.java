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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sqlite.SQLiteDataSource;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

  private final TestJpaRepository testJpaRepository;

  @Transactional
  public void testSqlite() {
    final JdbcTemplate jdbcTemplate = createSqliteJdbcTemplate(UUID.randomUUID());
    TestSqliteRepository<TestSqliteEntity, Long> testSqliteEntityRepository = new TestSqliteEntityRepository(jdbcTemplate);
    TestSqliteRepository<TestSqliteEntitySub, Long> testSqliteEntitySubRepository = new TestSqliteEntitySubRepository(jdbcTemplate);

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

  private JdbcTemplate createSqliteJdbcTemplate(UUID randomUUID) {
    final String dbPath = System.getProperty("user.dir") + "/build/sqlite/" + UUID.randomUUID() + ".db";

    final Path path = Paths.get(dbPath);
    try {
      if (!Files.exists(path.getParent())) {
        Files.createDirectories(path.getParent());
      }
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
    } catch (IOException ignored) {
    }

    final SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl("jdbc:sqlite:" + dbPath);
    dataSource.setDatabaseName("tmp");
    dataSource.setJournalMode("OFF");
    dataSource.setSynchronous("OFF");
    dataSource.setLockingMode("EXCLUSIVE");
    dataSource.setTempStore("MEMORY");
    dataSource.setEncoding("UTF-8");

    return new JdbcTemplate(dataSource);
  }
}
