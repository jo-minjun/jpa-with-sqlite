package my.jpawithsqlite.domain;

import jakarta.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class TestJpaEntityTest {

  @Test
  void test() {
    System.out.println("======================");

    Class<?> aClass = TestJpaEntity.class;
    Field[] fields = aClass.getDeclaredFields();

    System.out.println(fields.length);

    for (Field field : fields) {
      System.out.println("name: " + field.getName());
      System.out.println("type: " + field.getType().getSimpleName());

      Annotation[] annotations = field.getAnnotations();
      for (Annotation annotation : annotations) {
        if (annotation instanceof Column) {
          System.out.println("annotation: " + annotation.annotationType().getSimpleName());
          System.out.println("nullable: " + ((Column) annotation).nullable());
        }
      }

    }

    System.out.println("======================");
  }
}