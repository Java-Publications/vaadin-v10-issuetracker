package org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation;

import java.util.Objects;
import java.util.function.BiFunction;

import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import com.vaadin.flow.component.Component;

public interface NavigationUtil {





  static BiFunction<Class<? extends Component>, UserRole, Boolean> checkClassForRole() {
    return (viewClass, userRole) -> {
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
