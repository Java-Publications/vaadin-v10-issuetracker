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
package com.vaadin.tutorial.issues.webapp;


import static com.vaadin.tutorial.issues.webapp.VaadinApp.ROUTE;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.appName")
@Route(value = ROUTE)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class VaadinApp extends VerticalLayout implements HasLogger {

  public static final String ROUTE = "";

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);

    Object accepted = VaadinSession.getCurrent().getAttribute(LoginView.ACCEPTED);
    String targetName = (accepted != null && (Boolean) accepted)
                                               ? "mustBeDefinedViaProperty"
                                               : LoginView.ROUTE;
    logger().info("[VaadinApp] - onAttach - targetView " + targetName);
    UI
        .getCurrent()
        .navigate(targetName);
  }
}
