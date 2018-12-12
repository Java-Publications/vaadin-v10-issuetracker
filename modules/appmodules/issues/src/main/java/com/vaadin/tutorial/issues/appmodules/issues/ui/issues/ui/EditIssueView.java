package com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui;


import static com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui.EditIssueView.ROUTE;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.appmodules.issues.model.issue.Issue;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatus;
import com.vaadin.tutorial.issues.appmodules.issues.ui.issues.IssueService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.dialog.ConfirmDialog;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.editIssue")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(DEVELOPER)
@UIScoped
public class EditIssueView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

  public static final String ROUTE = "edit-issue";


  @Inject private IssueService issueService;
  @Inject private UserService userService;

  private TextField title = new TextField(getTranslation("com.vaadin.tutorial.issues.title"));
  private TextArea description = new TextArea(getTranslation("com.vaadin.tutorial.issues.description"));
  private ComboBox<User> owner = new ComboBox<>(getTranslation("com.vaadin.tutorial.issues.owner"));
  private ComboBox<IssueStatus> status = new ComboBox<>(getTranslation("com.vaadin.tutorial.issues.status") , IssueStatus.values());

  private BeanValidationBinder<Issue> binder = new BeanValidationBinder<>(Issue.class);

  @Override
  public void setParameter(BeforeEvent event , Long issueId) {
    Optional<Issue> issue = issueService.findById(issueId);
    if (! issue.isPresent()) {
      UI.getCurrent().navigate(IssuesView.class);
    } else {
      editIssue(issue.get());
    }
  }

  private void editIssue(Issue issue) {
    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.editIssue"));
    viewTitle.addClassName("view-title");

    title.setWidth("100%");
    title.focus();

    description.setWidth("100%");

    Set<User> users = userService.find("" , UserRole.DEVELOPER);
    owner.setItems(users);
    owner.setItemLabelGenerator(User::getName);

    status.setItemLabelGenerator(status -> getTranslation(status.getNameProperty()));

    HorizontalLayout infoLayout = new HorizontalLayout(owner , status);
    infoLayout.setWidth("100%");

    Button delete = new Button(getTranslation("com.vaadin.tutorial.issues.delete") , e -> delete(issue));
    delete.getElement().setAttribute("theme" , "error");

    Button save = new Button(getTranslation("com.vaadin.tutorial.issues.save") , e -> save(issue));
    save.getElement().setAttribute("theme" , "primary");

    HorizontalLayout actionsLayout = new HorizontalLayout(delete , save);

    getContent().removeAll();
    getContent().add(viewTitle , title , description , infoLayout , actionsLayout);
    getContent().setAlignSelf(FlexComponent.Alignment.END , actionsLayout);

    binder.bindInstanceFields(this);
    binder.setBean(issue);
  }

  private void delete(Issue issue) {
    new ConfirmDialog(getTranslation("com.vaadin.tutorial.issues.deleteIssueConfirmation") ,
                      getTranslation("com.vaadin.tutorial.issues.yes") ,
                      getTranslation("com.vaadin.tutorial.issues.no") ,
                      e -> {
                        issueService.delete(issue);
                        UI.getCurrent().navigate(IssuesView.class);
                      }).open();
  }

  private void save(Issue issue) {
    if (binder.validate().hasErrors()) {
      Notification.show(getTranslation("com.vaadin.tutorial.issues.validationError"));
    } else {
      issueService.update(issue);
      UI.getCurrent().navigate(IssuesView.class);
    }
  }

}
