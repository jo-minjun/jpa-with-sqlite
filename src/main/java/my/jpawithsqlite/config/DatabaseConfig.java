package my.jpawithsqlite.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import my.jpawithsqlite.infra.TestSqliteEntityRepository;
import my.jpawithsqlite.infra.TestSqliteEntitySubRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = {"my.jpawithsqlite.application.persistence"},
    includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*JpaRepository")
)
public class DatabaseConfig {

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public TestSqliteEntityRepository testSqliteEntityRepository(String name) {
    return new TestSqliteEntityRepository(sqliteJdbcTemplate(name));
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public TestSqliteEntitySubRepository testSqliteEntitySubRepository(String name) {
    return new TestSqliteEntitySubRepository(sqliteJdbcTemplate(name));
  }

  public JdbcTemplate sqliteJdbcTemplate(String name) {
    final String dbPath = System.getProperty("user.dir") + "/build/sqlite/" + name + ".db";

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
