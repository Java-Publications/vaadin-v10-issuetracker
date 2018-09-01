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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.rapidpm.vaadin.v10.issuetracker.views.admin.AdminView;
import org.rapidpm.vaadin.v10.issuetracker.views.login.LoginView;
import org.rapidpm.vaadin.v10.issuetracker.views.reports.ReportsView;
import org.rapidpm.vaadin.v10.issuetracker.views.search.SearchView;

import java.util.function.Function;

import static org.rapidpm.vaadin.v10.issuetracker.RolesToView.*;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class VaadinApp extends VerticalLayout {

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    final Subject activeUser = SecurityUtils.getSubject();

    VaadinApp.this.getUI()
                  .ifPresentOrElse(
                      (ui) -> ui.navigate(subject2View().apply(activeUser)),
                      () -> {/*logging*/}
                  );
  }


  public static Function<Subject, Class<? extends Composite>> subject2View() {
    return (subject) -> (subject.hasRole(ADMIN.roleName()))
                        ? AdminView.class
                        : (subject.hasRole(USER.roleName()))
                          ? SearchView.class
                          : (subject.hasRole(REPORTS.roleName()))
                            ? ReportsView.class
                            : (subject.hasRole(OBSERVER.roleName()))
                              ? SearchView.class
                              : LoginView.class;
  }

}
