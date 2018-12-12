package com.vaadin.tutorial.issues.webapp.ui.layout.menu;

import static com.vaadin.tutorial.issues.webapp.security.navigation.NavigationUtil.checkClassForRole;
import static java.lang.Boolean.FALSE;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.tutorial.issues.webapp.security.SecurityService;

@UIScoped
public class UIConfiguration {

  @Inject private SecurityService securityService;

  private List<MenuOption> menuOptions = new ArrayList<>();
  private List<SerializableSupplier<Component>> headerComponentSuppliers = new ArrayList<>();

  public void addHeaderComponent(SerializableSupplier<Component> componentSupplier) {
    headerComponentSuppliers.add(componentSupplier);
  }

  public boolean addMenuOption(Class<? extends Component> viewClass , String messageKey , VaadinIcon icon) {
    return securityService
        .activeUserHasRoles()
        .stream()
        .filter(r -> checkClassForRole().apply(viewClass , r))
        .map(r -> new MenuOption(viewClass , messageKey , icon))
        .map(menuOptions::add)
        .findFirst()
        .orElse(FALSE);
  }

  public List<MenuOption> getMenuOptions() {
    return menuOptions;
  }

  public List<SerializableSupplier<Component>> getHeaderComponentSuppliers() {
    return headerComponentSuppliers;
  }

}
