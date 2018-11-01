package org.rapidpm.vaadin.v10.issuetracker.ui.layout.menu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.ui.security.AuthorizationService;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableSupplier;

@VaadinSessionScoped
public class UIConfiguration {

  @Inject private AuthorizationService authorizationService;

  private List<MenuOption> menuOptions = new ArrayList<>();
  private List<SerializableSupplier<Component>> headerComponentSuppliers = new ArrayList<>();

  public void addHeaderComponent(SerializableSupplier<Component> componentSupplier) {
    headerComponentSuppliers.add(componentSupplier);
  }

  public boolean addMenuOption(Class<? extends Component> viewClass , String text , VaadinIcon icon) {
    if (authorizationService.userCanAccess(viewClass)) {
      menuOptions.add(new MenuOption(viewClass , text , icon));
      return true;
    }

    return false;
  }

  public List<MenuOption> getMenuOptions() {
    return menuOptions;
  }

  public List<SerializableSupplier<Component>> getHeaderComponentSuppliers() {
    return headerComponentSuppliers;
  }

}
