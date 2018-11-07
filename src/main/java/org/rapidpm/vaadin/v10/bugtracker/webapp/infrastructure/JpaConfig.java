package org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.apache.meecrowave.jpa.api.PersistenceUnitInfoBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@ApplicationScoped
public class JpaConfig {

  public static final String PERSISTENCE_UNIT = "persistenceUnit";

  @Produces
  @ApplicationScoped
  public DataSource dataSource() {
    // Examines both filesystem and classpath for .properties file
    HikariConfig config = new HikariConfig("/META-INF/hikari.properties");
    HikariDataSource ds = new HikariDataSource(config);
    //TODO init OpenTracing
    return ds;
  }

  @Produces
  public PersistenceUnitInfoBuilder unit(final DataSource ds) {
    return new PersistenceUnitInfoBuilder()
        .setUnitName(PERSISTENCE_UNIT)
        .setDataSource(ds)
        .setExcludeUnlistedClasses(true)
//        .addManagedClazz(T0001UsersEntity.class)
        .addProperty("openjpa.RuntimeUnenhancedClasses" , "supported")
        .addProperty("openjpa.jdbc.SynchronizeMappings" , "buildSchema");
  }


}
