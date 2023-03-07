package my.jpawithsqlite.infra;

import java.sql.PreparedStatement;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;
import my.jpawithsqlite.application.persistence.TestSqliteRepository;
import my.jpawithsqlite.domain.TestSqliteEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class TestSqliteEntityRepository implements TestSqliteRepository<TestSqliteEntity, Long> {

  private final JdbcTemplate jdbcTemplate;

  public TestSqliteEntityRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  @Transactional
  public void createTable() {
    final String query = """
        CREATE TABLE test_sqlite_entity (
          id      INTEGER       NOT NULL PRIMARY KEY AUTOINCREMENT,
          name    varchar(20)   NOT NULL,
          age     smallint(3)   NOT NULL
        );
        """;

    log.info("\n{}", query);
    jdbcTemplate.execute(query);
  }

  @Override
  @Transactional
  public Long save(TestSqliteEntity entity) {

    final String query = """
        INSERT INTO test_sqlite_entity (name, age)
        VALUES (?, ?);
        """;

    log.info(query.replace("\n", " "));
    final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, entity.getName());
      pstmt.setInt(2, entity.getAge());

      return pstmt;
    }, generatedKeyHolder);

    return generatedKeyHolder.getKey().longValue();
  }

  @Override
  @Transactional(readOnly = true)
  public TestSqliteEntity findById(Long id) {
    final String query = """
        SELECT * FROM test_sqlite_entity WHERE id = ?;
        """;

    log.info(query.replace("\n", " "));
    return jdbcTemplate.queryForObject(query, TestSqliteEntity.class, id);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<TestSqliteEntity> findAll() {
    final String query = """
        SELECT * FROM test_sqlite_entity;
        """;

    log.info(query.replace("\n", " "));
    return jdbcTemplate.queryForList(query, TestSqliteEntity.class);
  }
}
