package com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui;

import static com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui.CreateIssueView.ROUTE;
import static java.util.Set.of;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
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
import com.vaadin.tutorial.issues.appmodules.issues.model.issue.Issue;
import com.vaadin.tutorial.issues.appmodules.issues.ui.issues.IssueService;
import com.vaadin.tutorial.issues.webapp.security.SecurityService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.createIssue")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(DEVELOPER)
@UIScoped
public class CreateIssueView extends Composite<VerticalLayout> {

  public static final String ROUTE = "create-issue";

  private TextField title = new TextField(getTranslation("com.vaadin.tutorial.issues.title"));
  private TextArea description = new TextArea(getTranslation("com.vaadin.tutorial.issues.description"));
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
    owner.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.owner"));

    Button create = new Button(getTranslation("com.vaadin.tutorial.issues.create") , e -> create());
    create.getElement().setAttribute("theme" , "primary");

    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.createIssue"));
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
      Notification.show(getTranslation("com.vaadin.tutorial.issues.validationError"));
    }
  }

}
