package com.vaadin.tutorial.issues.appmodules.security.ui;

import static com.vaadin.tutorial.issues.appmodules.security.ui.UsersView.ROUTE;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.tutorial.issues.webapp.security.model.user.User;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserService;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
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
import com.vaadin.tutorial.issues.webapp.security.VisibleTo;
import com.vaadin.tutorial.issues.appmodules.security.ui.components.UserRolesCard;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.users")
@VisibleTo(UserRole.DEVELOPER)
@Route(value = ROUTE, layout = MainLayout.class)
public class UsersView extends Composite<VerticalLayout> {

  private TextField name = new TextField();
  private ComboBox<UserRole> role = new ComboBox<>();
  private Grid<User> grid = new Grid<>();

  public static final String ROUTE = "users";


  @Inject private UserService userService;

  public UsersView() {
    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.users"));
    viewTitle.addClassName("view-title");

    name.setHeight("100%");
    name.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.name"));
    name.addValueChangeListener(e -> refreshGrid());

    role.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.role"));
    role.setItems(UserRole.values());
    role.setItemLabelGenerator(role -> getTranslation(role.getNameProperty()));
    role.addValueChangeListener(e -> refreshGrid());

    RouterLink createNew = new RouterLink(getTranslation("com.vaadin.tutorial.issues.create") , CreateUserView.class);

    HorizontalLayout actionsLayout = new HorizontalLayout(VaadinIcon.PLUS.create() , createNew);
    actionsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    HorizontalLayout filterLayout = new HorizontalLayout(name , role , actionsLayout);
    filterLayout.setWidth("100%");
    filterLayout.setFlexGrow(1 , actionsLayout);

    grid.setItemDetailsRenderer(new ComponentRenderer<>(UserRolesCard::new));



    grid.addColumn(User::getName).setHeader(getTranslation("com.vaadin.tutorial.issues.name"));
    grid.addColumn(User::getEmail).setHeader(getTranslation("com.vaadin.tutorial.issues.email"));


    grid.addColumn(user -> user.getRoles().size())
        .setHeader(getTranslation("com.vaadin.tutorial.issues.role"));


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
