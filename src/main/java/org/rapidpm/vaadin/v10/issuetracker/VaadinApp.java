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
package org.rapidpm.vaadin.v10.issuetracker;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.component.login.LoginView;
import org.rapidpm.vaadin.v10.issuetracker.views.admin.AdminView;
import org.rapidpm.vaadin.v10.issuetracker.views.login.MyLoginView;
import org.rapidpm.vaadin.v10.issuetracker.views.reports.ReportsView;
import org.rapidpm.vaadin.v10.issuetracker.views.search.SearchView;

import java.util.function.Function;

import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.success;
import static org.rapidpm.vaadin.v10.issuetracker.RolesToView.*;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class VaadinApp extends VerticalLayout implements HasLogger {

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    final Subject activeUser = SecurityUtils.getSubject();
    logger().info("onAttach - activeUser - " + activeUser.getPrincipal());
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


  public static Function<Subject, Result<Class<? extends Composite>>> roleToDefaultView() {
    return (activeUser) -> match(
        matchCase(() -> success(MyLoginView.class)),
        matchCase(() -> activeUser.hasRole(ADMIN.roleName()), () -> success(AdminView.class)),
        matchCase(() -> activeUser.hasRole(USER.roleName()), () -> success(SearchView.class)),
        matchCase(() -> activeUser.hasRole(REPORTS.roleName()), () -> success(ReportsView.class)),
        matchCase(() -> activeUser.hasRole(OBSERVER.roleName()), () -> success(SearchView.class))
    );
  }

}
