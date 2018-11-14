package org.rapidpm.vaadin.v10.bugtracker.model.userrole;

import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;

public enum UserRole {
  ADMIN("com.example.role.admin"),
  DEVELOPER("com.example.role.developer"),
  USER("com.example.role.user");

  final String nameProperty;

  UserRole(String nameProperty) {
    this.nameProperty = nameProperty;
  }

  public String getNameProperty() {
    return nameProperty;
  }

  public static UserRole byNameProperty(String key) {
    return match(
        matchCase(() -> failure("requested key has no corresponding ENUM  " + key)) ,
        matchCase(() -> key.equals(ADMIN.nameProperty) , () -> success(ADMIN)) ,
        matchCase(() -> key.equals(DEVELOPER.nameProperty) , () -> success(DEVELOPER)) ,
        matchCase(() -> key.equals(USER.nameProperty) , () -> success(USER))
    )
        .ifFailed(message -> {
          throw new RuntimeException(message);
        })
        .get();
  }
}
