package my.jpawithsqlite.application.persistence;

public interface TestSqliteRepository<E, I> {

  void createTable();

  I save(E entity);

  E findById(I id);

  Iterable<E> findAll();
}
