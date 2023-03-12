package my.jpawithsqlite.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;

//@Configuration
public class SqliteDataSourceConfig {

  // TODO: bean 구현으로 변경
  //  @Bean("sqliteDataSource")
//  public DataSource sqliteDataSource() throws IOException {
  public static DataSource sqliteDataSource() {
    final String dbPath = System.getProperty("user.dir") + "/build/sqlite" + "/tmp.db";

    final Path path = Paths.get(dbPath);
    try {
      if (!Files.exists(path.getParent())) {
        Files.createDirectories(path.getParent());
      }
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
    } catch (IOException ignored) {}

    final SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl("jdbc:sqlite:" + dbPath);
    dataSource.setDatabaseName("tmp");
    dataSource.setJournalMode("OFF");
    dataSource.setSynchronous("OFF");
    dataSource.setLockingMode("EXCLUSIVE");
    dataSource.setTempStore("MEMORY");
    dataSource.setEncoding("UTF-8");

    return dataSource;
  }
}
