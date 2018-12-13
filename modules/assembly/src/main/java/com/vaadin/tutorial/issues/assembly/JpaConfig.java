package com.vaadin.tutorial.issues.assembly;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.apache.meecrowave.jpa.api.PersistenceUnitInfoBuilder;
import org.rapidpm.dependencies.core.logger.HasLogger;

@ApplicationScoped
public class JpaConfig implements HasLogger {

  public static final String PERSISTENCE_UNIT = "persistenceUnit";

  @Produces
  public PersistenceUnitInfoBuilder unit(final DataSource ds) {
    return new PersistenceUnitInfoBuilder()
        .setUnitName(PERSISTENCE_UNIT)
        .setDataSource(ds)
        .setExcludeUnlistedClasses(false)
//        .addManagedClazz(CoreEntity.class)
//        .addManagedClazz(UserEntity.class)
//        .addManagedClazz(UserRoleEntity.class)
//        .addManagedClazz(IssueStatusEntity.class)
//        .addManagedClazz(IssueEntity.class)
//        .addManagedClazz(ProjectEntity.class)
        .addProperty("openjpa.RuntimeUnenhancedClasses" , "supported");
    //INFO do not use this with flyway
//        .addProperty("openjpa.jdbc.SynchronizeMappings" , "buildSchema");
  }


}
