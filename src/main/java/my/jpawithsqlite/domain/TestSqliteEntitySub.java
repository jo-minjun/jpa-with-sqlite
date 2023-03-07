package my.jpawithsqlite.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TestSqliteEntitySub {

  private Long id;

  private String home;

  @Builder
  public TestSqliteEntitySub(String home) {
    this.home = home;
  }

  @Builder(toBuilder = true)
  public TestSqliteEntitySub(Long id) {
    if (id == null) {
      throw new IllegalArgumentException();
    }
    this.id = id;
  }
}
