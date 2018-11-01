package org.rapidpm.vaadin.v10.issuetracker.ui.security.impl;

import org.rapidpm.vaadin.v10.issuetracker.domain.Role;
import org.rapidpm.vaadin.v10.issuetracker.ui.security.AuthorizationService;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.flow.component.Component;

@VaadinSessionScoped
public class AuthorizationServiceImpl implements AuthorizationService {
  @Override
  public boolean userCanAccess(Class<? extends Component> viewClass) {
    return false;
  }

  @Override
  public Component secureComponent(Component component , Role... roles) {
    return null;
  }
}
