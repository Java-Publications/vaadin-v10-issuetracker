package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.ui;

import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.DEVELOPER;
import static org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.ui.UsersView.ROUTE;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation.VisibleTo;
import org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle.I18NPageTitle;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.UserService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.ui.components.UserRolesCard;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@I18NPageTitle(messageKey = "com.example.issues.users")
@VisibleTo(DEVELOPER)
@Route(value = ROUTE, layout = MainLayout.class)
public class UsersView extends Composite<VerticalLayout> {

  private TextField name = new TextField();
  private ComboBox<UserRole> role = new ComboBox<>();
  private Grid<User> grid = new Grid<>();

  public static final String ROUTE = "users";


  @Inject private UserService userService;

  public UsersView() {
    Span viewTitle = new Span(getTranslation("com.example.issues.users"));
    viewTitle.addClassName("view-title");

    name.setHeight("100%");
    name.setPlaceholder(getTranslation("com.example.issues.name"));
    name.addValueChangeListener(e -> refreshGrid());

    role.setPlaceholder(getTranslation("com.example.issues.role"));
    role.setItems(UserRole.values());
    role.setItemLabelGenerator(role -> getTranslation(role.getNameProperty()));
    role.addValueChangeListener(e -> refreshGrid());

    RouterLink createNew = new RouterLink(getTranslation("com.example.issues.create") , CreateUserView.class);

    HorizontalLayout actionsLayout = new HorizontalLayout(VaadinIcon.PLUS.create() , createNew);
    actionsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    HorizontalLayout filterLayout = new HorizontalLayout(name , role , actionsLayout);
    filterLayout.setWidth("100%");
    filterLayout.setFlexGrow(1 , actionsLayout);

    grid.setItemDetailsRenderer(new ComponentRenderer<>(UserRolesCard::new));



    grid.addColumn(User::getName).setHeader(getTranslation("com.example.issues.name"));
    grid.addColumn(User::getEmail).setHeader(getTranslation("com.example.issues.email"));


    grid.addColumn(user -> user.getRoles().size())
        .setHeader(getTranslation("com.example.issues.role"));


    grid.addComponentColumn(u -> new Button(VaadinIcon.EDIT.create() ,
                                            e -> UI.getCurrent().navigate(EditUserView.class , u.getUserId())));

    getContent().add(viewTitle , filterLayout , grid);
    getContent().setSizeFull();

  }

  @PostConstruct
  private void postConstruct(){
    refreshGrid();
  }

  private void refreshGrid() {
    Set<User> users = userService.find(name.getValue() , role.getValue());
    grid.setItems(users);
  }

}
