package org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.rapidpm.vaadin.v10.bugtracker.model.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation.RestrictionAnnotation;

@RestrictionAnnotation(RoleBasedAccessEvaluator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisibleTo {
  UserRole value();
}
