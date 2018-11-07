package org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import org.apache.meecrowave.jpa.api.PersistenceUnitInfoBuilder;
import org.flywaydb.core.Flyway;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@ApplicationScoped
public class JpaConfig implements HasLogger {

  public static final String PERSISTENCE_UNIT = "persistenceUnit";

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

  @Produces
  public PersistenceUnitInfoBuilder unit(final DataSource ds) {
    return new PersistenceUnitInfoBuilder()
        .setUnitName(PERSISTENCE_UNIT)
        .setDataSource(ds)
 //       .setExcludeUnlistedClasses(true)
//        .addManagedClazz(T0001UsersEntity.class)
        .addManagedClazz(UserEntity.class)
     //   .addManagedClazz(UserRoleEntity.class)
        .addProperty("openjpa.RuntimeUnenhancedClasses" , "supported")
        .addProperty("openjpa.jdbc.SynchronizeMappings" , "buildSchema");
  }


}
