package org.rapidpm.vaadin.v10.bugtracker.model;

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

}
