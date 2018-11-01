package org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.ui;

import static org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.ui.CreateIssueView.ROUTE;
import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.domain.Role;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.Issue;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.IssueService;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.UserService;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.issuetracker.ui.security.AuthorizationService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route(value = ROUTE, layout = MainLayout.class)
public class CreateIssueView extends Composite<VerticalLayout> {

  public static final String ROUTE = "create-issue";

  private TextField title = new TextField(getTranslation("com.example.issues.title"));
  private TextArea description = new TextArea(getTranslation("com.example.issues.description"));
  private ComboBox<User> owner = new ComboBox<>();

  @Inject private UserService userService;
  @Inject private IssueService issueService;
  @Inject private AuthorizationService authorizationService;

  public CreateIssueView() {
    setPageTitel().accept(this , "com.example.issues.createIssue");

    title.setSizeFull();
    title.focus();

    description.setSizeFull();
    description.setHeight("20em");

    Set<User> users = userService.findByRole(Role.DEVELOPER);
    owner.setItems(users);
    owner.setItemLabelGenerator(User::getName);
    owner.setPlaceholder(getTranslation("com.example.issues.owner"));

    Button create = new Button(getTranslation("com.example.issues.create") , e -> create());
    create.getElement().setAttribute("theme" , "primary");

    Span viewTitle = new Span(getTranslation("com.example.issues.createIssue"));
    viewTitle.addClassName("view-title");
    VerticalLayout formLayout = new VerticalLayout(viewTitle ,
                                                   title ,
                                                   description ,
                                                   authorizationService.secureComponent(owner , Role.ADMIN , Role.DEVELOPER) ,
                                                   create);
    formLayout.setPadding(false);
    formLayout.setMargin(false);
    formLayout.setAlignSelf(FlexComponent.Alignment.END , owner , create);

    Div mainLayout = new Div(formLayout);
    mainLayout.setWidth("100%");
    getContent().add(mainLayout);
  }

  private void create() {
    try {
      Issue issue = new Issue();
      BeanValidationBinder<Issue> binder = new BeanValidationBinder<>(Issue.class);
      binder.bindInstanceFields(this);
      binder.writeBean(issue);
      issueService.create(issue);
      UI.getCurrent().navigate(IssueView.class , issue.getId());

    } catch (ValidationException e) {
      Notification.show(getTranslation("com.example.issues.validationError"));
    }
  }

}
