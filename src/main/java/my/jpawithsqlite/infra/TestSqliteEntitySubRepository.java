package my.jpawithsqlite.infra;

import java.sql.PreparedStatement;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;
import my.jpawithsqlite.application.persistence.TestSqliteRepository;
import my.jpawithsqlite.domain.TestSqliteEntitySub;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class TestSqliteEntitySubRepository implements TestSqliteRepository<TestSqliteEntitySub, Long> {

  private final JdbcTemplate jdbcTemplate;

  public TestSqliteEntitySubRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  @Transactional
  public void createTable() {
    final String query = """
        CREATE TABLE test_sub (
          id      INTEGER       NOT NULL PRIMARY KEY AUTOINCREMENT,
          home    varchar(20)   NOT NULL
        );
        """;

    log.info("\n{}", query);
    jdbcTemplate.execute(query);
  }

  @Override
  @Transactional
  public Long save(TestSqliteEntitySub entity) {

    final String query = """
        INSERT INTO test_sub (home)
        VALUES (?);
        """;

    log.info(query.replace("\n", " "));
    final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, entity.getHome());

      return pstmt;
    }, generatedKeyHolder);

    return generatedKeyHolder.getKey().longValue();
  }

  @Override
  @Transactional(readOnly = true)
  public TestSqliteEntitySub findById(Long id) {
    final String query = """
        SELECT * FROM test_sub WHERE id = ?;
        """;

    log.info(query.replace("\n", " "));
    return jdbcTemplate.queryForObject(query, TestSqliteEntitySub.class, id);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<TestSqliteEntitySub> findAll() {
    final String query = """
        SELECT * FROM test_sub;
        """;

    log.info(query.replace("\n", " "));
    return jdbcTemplate.queryForList(query, TestSqliteEntitySub.class);
  }
}
