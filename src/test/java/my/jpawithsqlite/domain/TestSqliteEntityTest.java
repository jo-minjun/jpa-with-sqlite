package my.jpawithsqlite.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSqliteEntityTest {

  @Test
  void test1() {
    TestSqliteEntity entity = TestSqliteEntity.builder()
        .name("내 나이는 입력 값의 두배이고, null을 입력거나 입력하지 않으면 20살이다.")
        .age(null)
        .build();

    System.out.println(entity.getAge());
  }

  @Test
  void test2() {
    TestSqliteEntity entity = TestSqliteEntity.builder()
        .name("내 나이는 입력 값의 두배이고, null을 입력거나 입력하지 않으면 20살이다.")
        .age(15)
        .build();

    TestSqliteEntity id = entity.toBuilder()
        .age(20)
        .build();

    System.out.println(entity);
    System.out.println(id);
  }
}