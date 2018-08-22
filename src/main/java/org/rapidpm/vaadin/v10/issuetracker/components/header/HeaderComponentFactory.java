/**
 * Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.vaadin.v10.issuetracker.components.header;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.rapidpm.dependencies.core.logger.HasLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.rapidpm.vaadin.addons.framework.ComponentIDGenerator.buttonID;
import static org.rapidpm.vaadin.v10.issuetracker.RolesToView.*;
import static org.rapidpm.vaadin.v10.issuetracker.components.header.HeaderComponent.componentIDGenerator;

public class HeaderComponentFactory implements HasLogger {

  public static final String HEADER_COMPONENT_ID = componentIDGenerator().apply(HeaderComponent.class, "header");

  public HeaderComponent createBasedOnRoles() {
    final HeaderComponent headerComponent = new HeaderComponent();

    headerComponent.setId(HEADER_COMPONENT_ID);
    Subject activeUser = SecurityUtils.getSubject();

    buttonMap
        .keySet()
        .stream()
        .filter(role -> activeUser.hasRole(role) || activeUser.hasRole(ADMIN.roleName()))
        .sorted(String::compareToIgnoreCase)
        .map(role -> buttonMap.get(role))
        .map(Supplier::get)
        .forEach(headerComponent::addButton);

    boolean authenticated = activeUser.isAuthenticated();
    if (authenticated) headerComponent.addButton(logoutButton());

    return headerComponent;
  }


  private Map<String, Supplier<Button>> buttonMap = new HashMap<>();

  {
    buttonMap.put(ADMIN.roleName(), this::adminButton);
    buttonMap.put(USER.roleName(), this::userButton);
    buttonMap.put(REPORTER.roleName(), this::roleAButton);
    buttonMap.put(ROLE_B.roleName(), this::roleBButton);
    buttonMap.put(ROLE_C.roleName(), this::roleCButton);

  }

  public static final String BTN_LOGOUT_ID = buttonID().apply(HeaderComponentFactory.class, "logout");
  public static final String BTN_ADMIN_ID  = buttonID().apply(HeaderComponentFactory.class, ADMIN.viewName());
  public static final String BTN_USER_ID   = buttonID().apply(HeaderComponentFactory.class, USER.viewName());
  public static final String BTN_ROLE_A_ID = buttonID().apply(HeaderComponentFactory.class, REPORTER.viewName());
  public static final String BTN_ROLE_B_ID = buttonID().apply(HeaderComponentFactory.class, ROLE_B.viewName());
  public static final String BTN_ROLE_C_ID = buttonID().apply(HeaderComponentFactory.class, ROLE_C.viewName());


  private Button adminButton() {
    return new Button() {{
      setId(BTN_ADMIN_ID);
      setText("admin");
      addClickListener(e -> UI.getCurrent().navigate(ADMIN.viewName()));
    }};
  }

  private Button userButton() {
    return new Button() {{
      setId(BTN_USER_ID);
      setText("user");
      addClickListener(e -> UI.getCurrent().navigate(USER.viewName()));
    }};
  }

  private Button roleAButton() {
    return new Button() {{
      setId(BTN_ROLE_A_ID);
      setText("role A");
      addClickListener(e -> UI.getCurrent().navigate(REPORTER.viewName()));
    }};
  }

  private Button roleBButton() {
    return new Button() {{
      setId(BTN_ROLE_B_ID);
      setText("role B");
      addClickListener(e -> UI.getCurrent().navigate(ROLE_B.viewName()));
    }};
  }

  private Button roleCButton() {
    return new Button() {{
      setId(BTN_ROLE_C_ID);
      setText("role C");
      addClickListener(e -> UI.getCurrent().navigate(ROLE_C.viewName()));
    }};
  }


  private Button logoutButton() {
    return new Button() {{
      setId(BTN_LOGOUT_ID);
      setIcon(VaadinIcon.EXIT.create());
      addClickListener(e -> {
        Subject subject = SecurityUtils.getSubject();
        logger().info("logout for user .. " + subject.getPrincipal());
        subject.logout();
        UI.getCurrent().navigate("");
        UI.getCurrent().getPage().executeJavaScript("location.reload();");
      });
    }};
  }


}
