/**
 * Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.vaadin.v10.issuetracker.components.header;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import static org.rapidpm.vaadin.v10.issuetracker.RolesToView.*;


public class HeaderComponent extends Composite<HorizontalLayout> {

  private final HorizontalLayout buttonBar     = new HorizontalLayout();
  private final Span             headerMessage = new Span();

  public HeaderComponent() {

    buttonBar.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    final HorizontalLayout wrappedLayout = getContent();

    wrappedLayout.add(headerMessage, buttonBar);
    wrappedLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, headerMessage);
    wrappedLayout.setVerticalComponentAlignment(FlexComponent.Alignment.AUTO, buttonBar);

    wrappedLayout.setFlexGrow(1, headerMessage);
    wrappedLayout.expand(headerMessage);
    wrappedLayout.setSizeFull();

    //full init here now
    Subject activeUser = SecurityUtils.getSubject();

    if (activeUser.hasRole(ADMIN.roleName())) {
      buttonBar.add(adminButton());
    }
    if (activeUser.hasRole(USER.roleName()) || activeUser.hasRole(ADMIN.roleName())) {
      buttonBar.add(issueButton());
    }
    if (activeUser.hasRole(REPORTS.roleName()) || activeUser.hasRole(ADMIN.roleName())) {
      buttonBar.add(searchButton());
    }
    if (activeUser.hasRole(OBSERVER.roleName()) || activeUser.hasRole(ADMIN.roleName())) {
      buttonBar.add(reportsButton());
    }
    if (activeUser.isAuthenticated()) buttonBar.add(logoutButton());
  }


  public void setHeaderMessage(String msg) {
    headerMessage.setText(msg);
  }

  //to complex for this example
//  public Registration addButton(Button button) {
//    buttonBar.add(button);
//    return (Registration) () -> buttonBar.remove(button);
//  }


  private Button adminButton() {
    Button button = new Button();
    button.setText("admin");
    button.addClickListener(e -> UI.getCurrent().navigate(ADMIN.viewName()));
    return button;
  }

  private Button issueButton() {
    Button button = new Button();
    button.setText("issue");
    button.addClickListener(e -> UI.getCurrent().navigate(USER.viewName()));
    return button;
  }

  private Button searchButton() {
    Button button = new Button();
    button.setText("search");
    button.addClickListener(e -> UI.getCurrent().navigate(OBSERVER.viewName()));
    return button;
  }

  private Button reportsButton() {
    Button button = new Button();
    button.setText("reports");
    button.addClickListener(e -> UI.getCurrent().navigate(REPORTS.viewName()));
    return button;
  }

  private Button logoutButton() {
    Button button = new Button();
    button.setIcon(VaadinIcon.EXIT.create());
    button.addClickListener(e -> {
      Subject subject = SecurityUtils.getSubject();
      subject.logout();
      UI.getCurrent().navigate("");
      UI.getCurrent().getPage().executeJavaScript("location.reload();");
    });
    return button;
  }

}
