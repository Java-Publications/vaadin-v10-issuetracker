package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.menu;

import java.util.Objects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

public class MenuOption {

  private final Class<? extends Component> viewClass;
  private final String text;
  private final VaadinIcon icon;

  public MenuOption(Class<? extends Component> viewClass , String text , VaadinIcon icon) {
    this.viewClass = viewClass;
    this.text = text;
    this.icon = icon;
  }

  public Class<? extends Component> getViewClass() {
    return viewClass;
  }


  public String getText() {
    return text;
  }

  public VaadinIcon getIcon() {
    return icon;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (! (o instanceof MenuOption)) return false;
    MenuOption that = (MenuOption) o;
    return Objects.equals(viewClass , that.viewClass) &&
           Objects.equals(text , that.text) &&
           icon == that.icon;
  }

  @Override
  public int hashCode() {
    return Objects.hash(viewClass , text , icon);
  }

  @Override
  public String toString() {
    return "MenuOption{" +
           "viewClass=" + viewClass +
           ", text='" + text + '\'' +
           ", icon=" + icon +
           '}';
  }
}
