/**
 * Copyright © 2018 Sven Ruppert (sven.ruppert@gmail.com)
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
package org.rapidpm.vaadin.v10.issuetracker.views.login;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import static org.rapidpm.vaadin.v10.issuetracker.VaadinApp.subject2View;
import static org.rapidpm.vaadin.v10.issuetracker.views.login.LoginView.NAV_LOGIN_VIEW;


@Route(NAV_LOGIN_VIEW)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class LoginView extends Composite<HorizontalLayout> {


  public static final String NAV_LOGIN_VIEW = "login";

  private final TextField     username   = new TextField();
  private final PasswordField password   = new PasswordField();
  private final Checkbox      rememberMe = new Checkbox();
  private final Button        btnLogin   = new Button();
  private final Button        btnCancel  = new Button();

  private final VerticalLayout layout = new VerticalLayout(
      new HorizontalLayout(username, password),
      rememberMe,
      new HorizontalLayout(btnLogin, btnCancel)
  );

  public LoginView() {

    username.setPlaceholder("username");

    password.setRequired(true);
    password.setPlaceholder("password");

    rememberMe.setLabel("remember me");
    rememberMe.setIndeterminate(false);

    btnLogin.setText("Login");
    btnLogin.addClickListener(e -> validate());

    btnCancel.setText("Cancel");
    btnCancel.addClickListener(e -> clearFields());

    layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);
    layout.setSizeUndefined();

    final HorizontalLayout wrappedLayout = getContent();
    wrappedLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    wrappedLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    wrappedLayout.add(layout);
    wrappedLayout.setSizeFull();
  }

  private void clearFields() {
    username.clear();
    password.clear();
  }

  public String username() {
    return username.getValue();
  }

  public String password() {
    return password.getValue();
  }

  public Boolean rememberMe() {
    return rememberMe.getValue();
  }

  private void validate() {
    boolean isValid = checkCredentials();
    if (isValid) {
      navigateToApp();
    } else {
      reactOnFailedLogin();
    }
    clearFields();
  }

  public void reactOnFailedLogin() {
    Notification.show("Wrong credentials! ");
  }

  public void navigateToApp() {
    Subject activeUser = SecurityUtils.getSubject();

    LoginView.this.getUI()
                  .ifPresentOrElse(
                      (ui) -> ui.navigate(subject2View().apply(activeUser)),
                      () -> {/*logging*/}
                  );
  }

  /**
   * Subject currentUser = SecurityUtils.getSubject();
   * try {
   * currentUser.login(token);
   * } catch  ( UnknownAccountException uae ) { ...
   * } catch  ( IncorrectCredentialsException ice ) { ...
   * } catch  ( LockedAccountException lae ) { ...
   * } catch  ( ExcessiveAttemptsException eae ) { ...
   * } ...  your own ...
   * } catch ( AuthenticationException ae ) {
   * //unexpected error?
   * }
   */
  public boolean checkCredentials() {
    final UsernamePasswordToken token = new UsernamePasswordToken(username(),
                                                                  password()
    );
//    token.setRememberMe(rememberMe.getValue());

    Subject subject = null;
    try {
      subject = SecurityUtils.getSubject();
      subject.login(token);
      return subject.isAuthenticated();
    } catch (AuthenticationException e) {
      e.printStackTrace();
      return false;
    }
  }
}
