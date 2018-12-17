package com.vaadin.tutorial.issues.webapp.security;

import java.util.Objects;
import java.util.function.BiFunction;

import com.vaadin.flow.component.Component;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;

public interface NavigationUtil {


  static BiFunction<Class<? extends Component>, UserRole, Boolean> checkClassForRole() {
    return (viewClass , userRole) -> {
      Objects.requireNonNull(viewClass);
      Objects.requireNonNull(userRole);

      VisibleTo annotation = viewClass.getAnnotation(VisibleTo.class);
      if (annotation == null) {
        return false;
      } else {
        UserRole value = annotation.value();
        Objects.requireNonNull(value);
        return value.equals(userRole);
      }
    };
  }
}
