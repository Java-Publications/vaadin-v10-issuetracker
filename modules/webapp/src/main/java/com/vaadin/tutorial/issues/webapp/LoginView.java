package com.vaadin.tutorial.issues.webapp;


import static com.vaadin.tutorial.issues.webapp.LoginView.ROUTE;

import javax.inject.Inject;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.issues.webapp.security.PermissionsChangedEvent;
import com.vaadin.tutorial.issues.webapp.security.SecurityService;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.webapp.signIn")
@Route(value = ROUTE)
@HtmlImport("/frontend/styles/login-view-styles.html")
@HtmlImport("/frontend/styles/shared-styles.html")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class LoginView extends Composite<VerticalLayout> {

  public static final String ROUTE = "login";
  public static final String ACCEPTED = "ACCEPTED";

  private TextField email = new TextField(getTranslation("com.vaadin.tutorial.webapp.email"));
  private PasswordField password = new PasswordField(getTranslation("com.vaadin.tutorial.webapp.password"));
  private Button signIn = new Button(getTranslation("com.vaadin.tutorial.webapp.signIn") , e -> signInClicked());


  @Inject private SecurityService securityService;

  public LoginView() {
    Image logo = new Image("/frontend/images/app-logo.png" , "App logo");
    Span appName = new Span(getTranslation("com.vaadin.tutorial.appName"));
    appName.addClassName("header-app-name");
    HorizontalLayout header = new HorizontalLayout(logo , appName);


    email.setWidth("100%");
    email.setValue("marcus@vaadin.com");

    password.setWidth("100%");
    password.setValue("password");

    signIn.getElement().setAttribute("theme" , "primary");

    VerticalLayout formLayout = new VerticalLayout(
        new H2(getTranslation("com.vaadin.tutorial.webapp.signIn")) ,
        email ,
        password ,
        signIn);


    formLayout.setSizeUndefined();
    formLayout.setAlignSelf(FlexComponent.Alignment.END , signIn);
    formLayout.addClassName("login-view-form-layout");

    VerticalLayout contentLayout = new VerticalLayout(header , formLayout);
    contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    contentLayout.setWidth(null);

    HorizontalLayout verticalLayout = new HorizontalLayout(contentLayout);
    verticalLayout.setSizeFull();
    verticalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

    getContent().add(verticalLayout);
    getContent().setSizeFull();
    getContent().setFlexGrow(1 , contentLayout);
    getContent().setPadding(false);
    getContent().setMargin(false);
    getContent().setAlignSelf(FlexComponent.Alignment.CENTER , contentLayout);

    email.focus();
    password.addKeyPressListener(Key.ENTER , e -> signIn.click());
  }

  private void signInClicked() {
    if (checkCredentials()) navigateToApp();
    else reactOnFailedLogin();
  }


  public String username() {
    return this.email.getValue();
  }

  public String password() {
    return this.password.getValue();
  }


  private void reactOnFailedLogin() {
    Notification.show(getTranslation("global.wrong-credentials"));
//    ComponentUtil.fireEvent(UI.getCurrent(), new PermissionsChangedEvent());
    VaadinSession vaadinSession = VaadinSession.getCurrent();
    vaadinSession.setAttribute(User.class , null);
    vaadinSession.setAttribute(ACCEPTED , false);
  }

  private void navigateToApp() {
    ComponentUtil.fireEvent(UI.getCurrent() , new PermissionsChangedEvent());
    UI.getCurrent().navigate(VaadinApp.class);
  }

  private boolean checkCredentials() {
    return securityService.checkCredentials(username() , password())
                          .ifPresent(user -> {
                            VaadinSession vaadinSession = VaadinSession.getCurrent();
                            vaadinSession.setAttribute(ACCEPTED , true);
                            vaadinSession.setAttribute(User.class , user);
                          })
                          .isPresent();
  }

}
