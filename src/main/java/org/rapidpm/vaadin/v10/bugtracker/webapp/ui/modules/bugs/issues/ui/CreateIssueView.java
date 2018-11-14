package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui;

import static java.util.Set.of;
import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.ADMIN;
import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.DEVELOPER;
import static org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui.CreateIssueView.ROUTE;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.SecurityService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation.VisibleTo;
import org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle.I18NPageTitle;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.UserService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Issue;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.IssueService;
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

@I18NPageTitle(messageKey = "com.example.issues.createIssue")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(DEVELOPER)
public class CreateIssueView extends Composite<VerticalLayout> {

  public static final String ROUTE = "create-issue";

  private TextField title = new TextField(getTranslation("com.example.issues.title"));
  private TextArea description = new TextArea(getTranslation("com.example.issues.description"));
  private ComboBox<User> owner = new ComboBox<>();

  @Inject private UserService userService;
  @Inject private IssueService issueService;
  @Inject private SecurityService securityService;

  public CreateIssueView() {
    title.setSizeFull();
    title.focus();

    description.setSizeFull();
    description.setHeight("20em");

    owner.setItemLabelGenerator(User::getName);
    owner.setPlaceholder(getTranslation("com.example.issues.owner"));

    Button create = new Button(getTranslation("com.example.issues.create") , e -> create());
    create.getElement().setAttribute("theme" , "primary");

    Span viewTitle = new Span(getTranslation("com.example.issues.createIssue"));
    viewTitle.addClassName("view-title");
    VerticalLayout formLayout = new VerticalLayout(viewTitle ,
                                                   title ,
                                                   description ,
                                                   owner ,
//                                                   securityService.checkAgainstRoles(owner , of(DEVELOPER , ADMIN)) ,
                                                   create);
    formLayout.setPadding(false);
    formLayout.setMargin(false);
    formLayout.setAlignSelf(FlexComponent.Alignment.END , owner , create);

    Div mainLayout = new Div(formLayout);
    mainLayout.setWidth("100%");
    getContent().add(mainLayout);
  }


  @PostConstruct
  private void postConstruct() {
    Set<User> users = userService.findByRole(UserRole.DEVELOPER);
    owner.setItems(users);

    securityService.checkAgainstRoles(owner , of(DEVELOPER , ADMIN));
  }

  private void create() {
    try {
      Issue issue = new Issue();
      BeanValidationBinder<Issue> binder = new BeanValidationBinder<>(Issue.class);
      binder.bindInstanceFields(this);
      binder.writeBean(issue);
      issueService.create(issue);
      UI.getCurrent().navigate(IssueView.class , issue.getIssueId());

    } catch (ValidationException e) {
      Notification.show(getTranslation("com.example.issues.validationError"));
    }
  }

}
