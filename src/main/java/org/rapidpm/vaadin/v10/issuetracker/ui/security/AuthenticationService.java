package org.rapidpm.vaadin.v10.issuetracker.ui.security;

public interface AuthenticationService {
  String  USER_ID_SESSION_KEY = "USER_ID_SESSION_KEY";

  boolean isAuthenticated();

  boolean authenticate(String username , String password);

}
