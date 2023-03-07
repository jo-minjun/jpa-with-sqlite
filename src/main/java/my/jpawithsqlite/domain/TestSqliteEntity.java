package my.jpawithsqlite.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestSqliteEntity {

  private Long id;

  private String name;

  private Integer age;

  @Builder(toBuilder = true)
  public TestSqliteEntity(String name, Integer age) {
    if (age == null) {
      age = 20;
    } else {
      age = age * 2;
    }

    this.name = name;
    this.age = age;
  }
}
