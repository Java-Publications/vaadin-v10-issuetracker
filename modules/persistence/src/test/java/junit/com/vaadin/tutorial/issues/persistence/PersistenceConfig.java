package junit.com.vaadin.tutorial.issues.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.rapidpm.dependencies.core.logger.HasLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@ApplicationScoped
public class PersistenceConfig implements HasLogger {

  @Produces
  @ApplicationScoped
  public DataSource dataSource() {
    // Examines both filesystem and classpath for .properties file
    HikariConfig config = new HikariConfig("/META-INF/hikari.properties");
    HikariDataSource ds = new HikariDataSource(config);
    dbMigration(ds);
    //TODO init OpenTracing
    return ds;
  }

  private void dbMigration(DataSource ds) {
    logger().info("Running flyway db migrations");
    Flyway flyway = Flyway.configure().dataSource(ds).load();
    flyway.migrate();
    logger().info("Flyway migrations done");
  }

}
