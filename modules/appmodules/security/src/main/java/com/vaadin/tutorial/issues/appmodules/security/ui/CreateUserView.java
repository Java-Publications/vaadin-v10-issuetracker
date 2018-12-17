package com.vaadin.tutorial.issues.appmodules.security.ui;

import static com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole.ADMIN;
import static com.vaadin.tutorial.issues.appmodules.security.ui.CreateUserView.ROUTE;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.webapp.security.model.user.User;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserService;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRoleService;
import com.vaadin.tutorial.issues.webapp.security.ValidationService;
import com.vaadin.tutorial.issues.webapp.security.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.createUser")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(ADMIN)
public class CreateUserView extends Composite<VerticalLayout> {

  public static final String ROUTE = "create-user";

  @Inject private UserService userService;
  @Inject private UserRoleService userRoleService;
  @Inject private ValidationService validationService;

  private TextField name = new TextField(getTranslation("com.vaadin.tutorial.issues.name"));
  private TextField email = new TextField(getTranslation("com.vaadin.tutorial.issues.email"));
  private PasswordField password = new PasswordField(getTranslation("com.vaadin.tutorial.issues.password"));
  private ComboBox<UserRole> role = new ComboBox<>(getTranslation("com.vaadin.tutorial.issues.role") , UserRole.values());


  @PostConstruct
  private void postConstruct() {
    role.setItems(userRoleService.findAll());
  }


  public CreateUserView() {

    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.createUser"));
    viewTitle.addClassName("view-title");

    name.focus();

    role.setItemLabelGenerator(role -> getTranslation(role.getNameProperty()));

    FormLayout formLayout = new FormLayout(name , email , password , role);
    formLayout.setWidth("100%");

    Button save = new Button(getTranslation("com.vaadin.tutorial.issues.save") , e -> create());
    save.getElement().setAttribute("theme" , "primary");

    getContent().removeAll();
    getContent().add(viewTitle , formLayout , save);
    getContent().setSizeFull();
    getContent().setAlignSelf(FlexComponent.Alignment.END , save);
  }

  private void create() {
    try {
      User user = new User();
      BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
      binder.bindInstanceFields(this);
      binder.removeBinding(password);
      binder.writeBean(user);
      user.setPassword(password.getValue());
      validationService.validateProperty(user , "password" , password);

      userService.save(user);
      UI.getCurrent().navigate(UsersView.class);

    } catch (ValidationException | javax.validation.ValidationException e) {
      Notification.show(getTranslation("com.vaadin.tutorial.issues.validationError"));
    }
  }

}
