package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui;

import static org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui.EditIssueView.ROUTE;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.model.User;
import org.rapidpm.vaadin.v10.bugtracker.model.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle.I18NPageTitle;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.dialog.ConfirmDialog;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.UserService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Issue;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.IssueService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Status;
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

@I18NPageTitle(messageKey = "com.example.issues.editIssue")
@Route(value = ROUTE, layout = MainLayout.class)
public class EditIssueView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

  public static final String ROUTE = "edit-issue";


  @Inject private IssueService issueService;
  @Inject private UserService userService;

  private TextField title = new TextField(getTranslation("com.example.issues.title"));
  private TextArea description = new TextArea(getTranslation("com.example.issues.description"));
  private ComboBox<User> owner = new ComboBox<>(getTranslation("com.example.issues.owner"));
  private ComboBox<Status> status = new ComboBox<>(getTranslation("com.example.issues.status") , Status.values());

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
    Span viewTitle = new Span(getTranslation("com.example.issues.editIssue"));
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

    Button delete = new Button(getTranslation("com.example.issues.delete") , e -> delete(issue));
    delete.getElement().setAttribute("theme" , "error");

    Button save = new Button(getTranslation("com.example.issues.save") , e -> save(issue));
    save.getElement().setAttribute("theme" , "primary");

    HorizontalLayout actionsLayout = new HorizontalLayout(delete , save);

    getContent().removeAll();
    getContent().add(viewTitle , title , description , infoLayout , actionsLayout);
    getContent().setAlignSelf(FlexComponent.Alignment.END , actionsLayout);

    binder.bindInstanceFields(this);
    binder.setBean(issue);
  }

  private void delete(Issue issue) {
    new ConfirmDialog(getTranslation("com.example.issues.deleteIssueConfirmation") ,
                      getTranslation("com.example.issues.yes") ,
                      getTranslation("com.example.issues.no") ,
                      e -> {
                        issueService.delete(issue);
                        UI.getCurrent().navigate(IssuesView.class);
                      }).open();
  }

  private void save(Issue issue) {
    if (binder.validate().hasErrors()) {
      Notification.show(getTranslation("com.example.issues.validationError"));
    } else {
      issueService.update(issue);
      UI.getCurrent().navigate(IssuesView.class);
    }
  }

}
