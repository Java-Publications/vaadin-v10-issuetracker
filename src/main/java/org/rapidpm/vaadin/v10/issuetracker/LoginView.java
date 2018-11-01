package org.rapidpm.vaadin.v10.issuetracker;

import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.ui.security.AuthenticationService;
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
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = LoginView.ROUTE)
@HtmlImport("/frontend/styles/login-view-styles.html")
@HtmlImport("/frontend/styles/shared-styles.html")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class LoginView extends Composite<VerticalLayout> {

    public static final String ROUTE = "login";

    private TextField email = new TextField(getTranslation("com.example.webapp.email"));
    private PasswordField password = new PasswordField(getTranslation("com.example.webapp.password"));

    @Inject private AuthenticationService authenticationService;

    public LoginView() {
        setPageTitel().accept(this , "com.example.webapp.signIn");

        Image logo = new Image("/frontend/images/app-logo.png", "App logo");
        Span appName = new Span(getTranslation("com.example.appName"));
        appName.addClassName("header-app-name");
        HorizontalLayout header = new HorizontalLayout(logo, appName);

        H2 title = new H2(getTranslation("com.example.webapp.signIn"));

        email.setWidth("100%");
        email.setValue("marcus@vaadin.com");

        password.setWidth("100%");
        password.setValue("password");

        Button signIn = new Button(getTranslation("com.example.webapp.signIn"), e -> signInClicked());
        signIn.getElement().setAttribute("theme", "primary");

        VerticalLayout formLayout = new VerticalLayout(title, email, password, signIn);
        formLayout.setSizeUndefined();
        formLayout.setAlignSelf(FlexComponent.Alignment.END, signIn);
        formLayout.addClassName("login-view-form-layout");

        VerticalLayout contentLayout = new VerticalLayout(header, formLayout);
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        contentLayout.setWidth(null);

        HorizontalLayout verticalLayout = new HorizontalLayout(contentLayout);
        verticalLayout.setSizeFull();
        verticalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(verticalLayout);
        getContent().setSizeFull();
        getContent().setFlexGrow(1, contentLayout);
        getContent().setPadding(false);
        getContent().setMargin(false);
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, contentLayout);

        email.focus();
        password.addKeyPressListener(Key.ENTER, e -> signIn.click());
    }

    private void signInClicked() {
        String username = this.email.getValue();
        String password = this.password.getValue();

        if (authenticationService.authenticate(username, password)) {
            UI.getCurrent().navigate(VaadinApp.ROUTE);
        } else {
            Notification.show(getTranslation("com.example.webapp.badCredentials"), 5000, Notification.Position.MIDDLE);
        }
    }

}
