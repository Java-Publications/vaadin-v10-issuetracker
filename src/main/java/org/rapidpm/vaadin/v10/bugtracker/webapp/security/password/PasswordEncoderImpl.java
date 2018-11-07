package org.rapidpm.vaadin.v10.bugtracker.webapp.security.password;

import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class PasswordEncoderImpl implements PasswordEncoder {
  @Override
  public String encode(String password) {
    //TODO implement with strong encryption algo.
    return password;
  }
}
