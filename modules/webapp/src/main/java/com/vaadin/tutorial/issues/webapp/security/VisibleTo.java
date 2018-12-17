package com.vaadin.tutorial.issues.webapp.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.webapp.security.navigation.RestrictionAnnotation;


@RestrictionAnnotation(RoleBasedAccessEvaluator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisibleTo {
  UserRole value();
}
