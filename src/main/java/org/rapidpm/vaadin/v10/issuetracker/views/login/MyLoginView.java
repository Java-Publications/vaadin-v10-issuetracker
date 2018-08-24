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
package org.rapidpm.vaadin.v10.issuetracker.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.vaadin.component.login.LoginView;

import java.time.LocalDateTime;

import static org.rapidpm.vaadin.v10.issuetracker.VaadinApp.roleToDefaultView;
import static org.rapidpm.vaadin.v10.issuetracker.views.login.MyLoginView.NAV_LOGIN_VIEW;


@Route(NAV_LOGIN_VIEW)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MyLoginView extends LoginView {

  public static final String NAV_LOGIN_VIEW = "login";

  @Override
  public void reactOnFailedLogin() {
    Notification.show("Wrong credentials! ");

  }

  @Override
  public void navigateToApp() {
    Subject activeUser = SecurityUtils.getSubject();

    roleToDefaultView()
        .apply(activeUser)
        .ifPresentOrElse(
            success -> UI.getCurrent().navigate(success),
            failed -> {
              logger().warning("this should never happen !!! " + failed);
              UI.getCurrent().navigate(LoginView.class);
            }
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
  @Override
  public boolean checkCredentials() {
    final UsernamePasswordToken token = new UsernamePasswordToken(username(),
                                                                  password()
    );
//    token.setRememberMe(rememberMe.getValue());

    return ((CheckedFunction<UsernamePasswordToken, Boolean>) t -> {
      Subject subject = SecurityUtils.getSubject();
      subject.login(t);
      return subject.isAuthenticated();
    })
        .apply(token)
        .ifFailed(failed -> logger().warning("Login was not accepted .. " + LocalDateTime.now()))
        .getOrElse(() -> Boolean.FALSE);
  }
}
