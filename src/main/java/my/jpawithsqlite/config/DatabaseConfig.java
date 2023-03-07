package my.jpawithsqlite.config;

import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = {"my.jpawithsqlite.application.persistence"},
    includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*JpaRepository")
)
public class DatabaseConfig {
}
