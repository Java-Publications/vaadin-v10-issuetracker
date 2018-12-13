package junit.com.vaadin.tutorial.issues.persistence;

import static com.vaadin.tutorial.issues.persistence.PersistenceConfigConstants.PERSISTENCE_UNIT;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.apache.meecrowave.jpa.api.PersistenceUnitInfoBuilder;
import org.rapidpm.dependencies.core.logger.HasLogger;

@ApplicationScoped
public class JpaConfig implements HasLogger {

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
