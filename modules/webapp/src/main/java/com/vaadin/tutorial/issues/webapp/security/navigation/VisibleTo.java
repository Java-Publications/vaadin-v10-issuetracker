package com.vaadin.tutorial.issues.webapp.security.navigation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@RestrictionAnnotation(RoleBasedAccessEvaluator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisibleTo {
  UserRole value();
}
