package org.rapidpm.vaadin.v10.issuetracker.ui.security.impl;

import org.rapidpm.vaadin.v10.issuetracker.ui.security.AuthenticationService;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class AuthenticationServiceImpl implements AuthenticationService {
  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public boolean authenticate(String username , String password) {
    return false;
  }
}
