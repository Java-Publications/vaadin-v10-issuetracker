package org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.ui;

import static org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.ui.CreateUserView.ROUTE;
import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.domain.Role;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.UserService;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.issuetracker.ui.security.ValidationService;
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

@Route(value = ROUTE, layout = MainLayout.class)
public class CreateUserView extends Composite<VerticalLayout> {

    public static final String ROUTE = "create-user";

    @Inject private UserService userService;
    @Inject private ValidationService validationService;

    private TextField name = new TextField(getTranslation("com.example.issues.name"));
    private TextField email = new TextField(getTranslation("com.example.issues.email"));
    private PasswordField password = new PasswordField(getTranslation("com.example.issues.password"));
    private ComboBox<Role> role = new ComboBox<>(getTranslation("com.example.issues.role"), Role.values());

    public CreateUserView() {
        setPageTitel().accept(this , "com.example.issues.createUser");

        Span viewTitle = new Span(getTranslation("com.example.issues.createUser"));
        viewTitle.addClassName("view-title");

        name.focus();

        role.setItemLabelGenerator(role -> getTranslation(role.getNameProperty()));

        FormLayout formLayout = new FormLayout(name, email, password, role);
        formLayout.setWidth("100%");

        Button save = new Button(getTranslation("com.example.issues.save"), e -> create());
        save.getElement().setAttribute("theme", "primary");

        getContent().removeAll();
        getContent().add(viewTitle, formLayout, save);
        getContent().setSizeFull();
        getContent().setAlignSelf(FlexComponent.Alignment.END, save);
    }

    private void create() {
        try {
            User user = new User();
            BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
            binder.bindInstanceFields(this);
            binder.removeBinding(password);
            binder.writeBean(user);
            user.setPassword(password.getValue());
            validationService.validateProperty(user, "password", password);

            userService.save(user);
            UI.getCurrent().navigate(UsersView.class);

        } catch (ValidationException | javax.validation.ValidationException e) {
            Notification.show(getTranslation("com.example.issues.validationError"));
        }
    }

}
