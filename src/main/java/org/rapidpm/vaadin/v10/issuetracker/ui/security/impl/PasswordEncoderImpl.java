package org.rapidpm.vaadin.v10.issuetracker.ui.security.impl;

import org.rapidpm.vaadin.v10.issuetracker.ui.security.PasswordEncoder;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class PasswordEncoderImpl implements PasswordEncoder {
  @Override
  public String encode(String password) {
    return null;
  }
}
