package org.rapidpm.vaadin.v10.issuetracker.ui.layout;

import java.util.function.BiConsumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;

public interface LayoutFunctions {

  static BiConsumer<Component, String> setPageTitel() {
    return (component, msgKey) -> UI.getCurrent()
                                  .getPage()
                                  .setTitle(component.getTranslation(msgKey)
                + " | "
                + component.getTranslation("com.example.appName"));
  }


}
