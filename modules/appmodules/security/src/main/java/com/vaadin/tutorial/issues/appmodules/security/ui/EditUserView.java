package com.vaadin.tutorial.issues.appmodules.security.ui;

import static com.vaadin.tutorial.issues.appmodules.security.ui.EditUserView.ROUTE;
import static com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole.ADMIN;

import java.util.Optional;

import javax.inject.Inject;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.appmodules.security.ModuleSecuritySessionState;
import com.vaadin.tutorial.issues.webapp.security.model.user.User;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserService;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.webapp.security.ValidationService;
import com.vaadin.tutorial.issues.webapp.security.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.dialog.ConfirmDialog;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.editUser")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(ADMIN)
public class EditUserView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

  public static final String ROUTE = "edit-user";


  @Inject private UserService userService;
  @Inject private ModuleSecuritySessionState bugtrackerSessionState;
  @Inject private ValidationService validationService;

  private TextField name = new TextField(getTranslation("com.vaadin.tutorial.issues.name"));
  private TextField email = new TextField(getTranslation("com.vaadin.tutorial.issues.email"));
  private PasswordField password = new PasswordField(getTranslation("com.vaadin.tutorial.issues.password"));
  private ComboBox<UserRole> role = new ComboBox<>(getTranslation("com.vaadin.tutorial.issues.role") , UserRole.values());


  private BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
  private String originalPassword;

  @Override
  public void setParameter(BeforeEvent event , Long userId) {
    Optional<User> user = userService.findById(userId);
    if (! user.isPresent()) {
      UI.getCurrent().navigate(UsersView.class);
    } else {
      editUser(user.get());
    }
  }

  private void editUser(User user) {
    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.editUser"));
    viewTitle.addClassName("view-title");

    name.focus();

    role.setItemLabelGenerator(role -> getTranslation(role.getNameProperty()));

    FormLayout formLayout = new FormLayout(name , email , password , role);
    formLayout.setWidth("100%");

    Button delete = new Button(getTranslation("com.vaadin.tutorial.issues.delete") , e -> delete(user));
    delete.getElement().setAttribute("theme" , "error");

    Button save = new Button(getTranslation("com.vaadin.tutorial.issues.save") , e -> save(user));
    save.getElement().setAttribute("theme" , "primary");

    HorizontalLayout actionsLayout = new HorizontalLayout(delete , save);

    getContent().removeAll();
    getContent().add(viewTitle , formLayout , actionsLayout);
    getContent().setSizeFull();
    getContent().setAlignSelf(FlexComponent.Alignment.END , actionsLayout);


    originalPassword = user.getPassword();
    user.setPassword(null);

    binder.bindInstanceFields(this);
    binder.removeBinding(password);
    binder.setBean(user);
  }

  private void delete(User user) {
    new ConfirmDialog(getTranslation("com.vaadin.tutorial.issues.deleteUserConfirmation") ,
                      getTranslation("com.vaadin.tutorial.issues.yes") ,
                      getTranslation("com.vaadin.tutorial.issues.no") ,
                      e -> {
                        Long userId = user.getUserId();
                        userService.delete(userService.findById(userId).get());
                        if (bugtrackerSessionState.getUserId().equals(userId)) {
                          UI.getCurrent().getPage().executeJavaScript("window.location='/logout'");
                        } else {
                          UI.getCurrent().navigate(UsersView.class);
                        }
                      }).open();
  }

  private void save(User user) {
    boolean newPassword = ! password.getValue().isEmpty();
    try {
      if (! newPassword) {
        password.setValue(originalPassword);
      }

      binder.writeBean(user);
      user.setPassword(password.getValue());
      validationService.validateProperty(user , "password" , password);

      userService.update(user , newPassword);
      UI.getCurrent().navigate(UsersView.class);

    } catch (ValidationException | javax.validation.ValidationException e) {
      if (! newPassword) {
        user.setPassword(null);
        password.clear();
        password.setErrorMessage(null);
        password.setInvalid(false);
      }

      Notification.show(getTranslation("com.vaadin.tutorial.issues.validationError"));
    }
  }

}
