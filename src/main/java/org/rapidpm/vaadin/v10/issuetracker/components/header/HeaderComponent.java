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

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.vaadin.addons.framework.Registration;
import org.rapidpm.vaadin.v10.issuetracker.TypedComponentIDGenerator;

import java.util.function.BiFunction;

import static org.rapidpm.vaadin.addons.framework.ComponentIDGenerator.spanID;

public class HeaderComponent extends Composite<HorizontalLayout> implements HasLogger {

  public static final String SP_HEADER_MESSAGE = spanID().apply(HeaderComponent.class, "header-message");

  private final HorizontalLayout buttonBar = new HorizontalLayout() {{
    setJustifyContentMode(JustifyContentMode.END);
  }};

  private final Span headerMessage = new Span() {{
    setId(SP_HEADER_MESSAGE);
  }};

  public HeaderComponent() {
    final HorizontalLayout wrappedLayout = getContent();

    wrappedLayout.add(headerMessage, buttonBar);
    wrappedLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, headerMessage);
    wrappedLayout.setVerticalComponentAlignment(FlexComponent.Alignment.AUTO, buttonBar);

    wrappedLayout.setFlexGrow(1, headerMessage);
    wrappedLayout.expand(headerMessage);
    wrappedLayout.setSizeFull();
  }


  public void setHeaderMessage(String msg) {
    headerMessage.setText(msg);
  }

  public Registration addButton(Button button) {
    buttonBar.add(button);
    return (Registration) () -> ((CheckedFunction<Button, Boolean>) b -> {
      buttonBar.remove(b);
      return Boolean.TRUE;
    })
        .apply(button)
        .ifFailed((msg) -> logger().warning(" button remove failed " + msg))
        .getOrElse(() -> Boolean.FALSE);
  }


  public static BiFunction<Class, String, String> componentIDGenerator() {
    return TypedComponentIDGenerator.typedComponentIDGenerator().apply(HeaderComponent.class);
  }
}
