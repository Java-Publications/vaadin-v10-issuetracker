package com.vaadin.tutorial.issues.webapp.security;

import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.flow.router.Location;
import com.vaadin.tutorial.issues.webapp.LoginView;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.webapp.security.navigation.Access;
import com.vaadin.tutorial.issues.webapp.security.navigation.AccessEvaluator;

public class RoleBasedAccessEvaluator implements AccessEvaluator<VisibleTo>, HasLogger {

  @Inject private SecurityService securityService;

  @Override
  public Access evaluate(Location location , Class<?> navigationTarget , VisibleTo visibleTo) {

    logger().info("evaluate " + location.getPathWithQueryParameters());
    Set<UserRole> userRoles = securityService.activeUserHasRoles();

    final boolean hasRole = userRoles.contains(visibleTo.value());
    if (! hasRole) logger().warning("User " + securityService.activeUsername()
                                    + " tried to access the following path ## "
                                    + location.getPathWithQueryParameters() + " ##");

    //TODO who is handling the logout?
    return hasRole ? Access.granted() : Access.restricted(LoginView.ROUTE);


  }
}
