package my.jpawithsqlite.presentation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import my.jpawithsqlite.application.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @PostMapping("/api/test/jpa")
  public ResponseEntity<Long> test1() {
    final Long id = testService.testJpa();

    return ResponseEntity.ok(id);
  }

  @PostMapping("/api/test/sqlite")
  public ResponseEntity<Void> test2() {
    CompletableFuture.runAsync(testService::testSqlite, executor);

    return ResponseEntity.accepted().build();
  }
}
