package org.rapidpm.vaadin.v10.issuetracker.ui.security;

import org.rapidpm.vaadin.v10.issuetracker.domain.Role;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;

public interface AuthorizationService {
  boolean userCanAccess(Class<? extends Component> viewClass);

  Component secureComponent(Component component , Role... roles);
}
