package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.menu;

import java.util.Objects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

//TODO Kotlin DataClass ??
public class MenuOption {

  private final Class<? extends Component> viewClass;
  private final String messageKey;
  private final VaadinIcon icon;

  MenuOption(Class<? extends Component> viewClass , String messageKey , VaadinIcon icon) {
    this.viewClass = viewClass;
    this.messageKey = messageKey;
    this.icon = icon;
  }

  Class<? extends Component> getViewClass() {
    return viewClass;
  }


  String getMessageKey() {
    return messageKey;
  }

  VaadinIcon getIcon() {
    return icon;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (! (o instanceof MenuOption)) return false;
    MenuOption that = (MenuOption) o;
    return Objects.equals(viewClass , that.viewClass) &&
           Objects.equals(messageKey , that.messageKey) &&
           icon == that.icon;
  }

  @Override
  public int hashCode() {
    return Objects.hash(viewClass , messageKey , icon);
  }

  @Override
  public String toString() {
    return "MenuOption{" +
           "viewClass=" + viewClass +
           ", messageKey='" + messageKey + '\'' +
           ", icon=" + icon +
           '}';
  }
}
