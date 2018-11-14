package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui;

import static java.util.Set.of;
import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.ADMIN;
import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.DEVELOPER;
import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.USER;
import static org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui.IssuesView.ROUTE;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.webapp.security.SecurityService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation.VisibleTo;
import org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle.I18NPageTitle;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.BugsModule;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Issue;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.IssueService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Status;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@I18NPageTitle(messageKey = "com.example.issues.issues")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(USER)
public class IssuesView extends Composite<VerticalLayout> {

  public static final String ROUTE = "issues";


  @Inject private IssueService issueService;
  @Inject private SecurityService securityService;

  @Inject private BugsModule bugsModule;

  private final Span viewTitle = new Span(getTranslation("com.example.issues.issues"));
  private final TextField title = new TextField();
  private final TextField reporter = new TextField();
  private final TextField owner = new TextField();
  private final ComboBox<Status> status = new ComboBox<>(null , Status.values());
  private final DatePicker date = new DatePicker();
  private final Grid<Issue> grid = new Grid<>();


  @PostConstruct
  private void postConstruct() {
    refreshGrid();

  }

  public IssuesView() {
    viewTitle.addClassName("view-title");

    title.setPlaceholder(getTranslation("com.example.issues.title"));
    title.setWidth("100%");
    title.addValueChangeListener(e -> refreshGrid());
    title.setSizeFull();

    reporter.setPlaceholder(getTranslation("com.example.issues.reporter"));
    reporter.setWidth("100%");
    reporter.addValueChangeListener(e -> refreshGrid());
    reporter.setSizeFull();

    owner.setPlaceholder(getTranslation("com.example.issues.owner"));
    owner.setWidth("100%");
    owner.addValueChangeListener(e -> refreshGrid());
    owner.setSizeFull();

    status.setValue(Status.OPEN);
    status.setPlaceholder(getTranslation("com.example.issues.status"));
    status.setWidth("100%");
    status.addValueChangeListener(e -> refreshGrid());
    status.setItemLabelGenerator(status -> getTranslation(status.getNameProperty()));

    date.setPlaceholder(getTranslation("com.example.issues.date"));
    date.setWidth("100%");

    HorizontalLayout filterLayout = new HorizontalLayout(title , owner , reporter , status , date);
    filterLayout.setWidth("100%");
    grid.addColumn(i -> "#" + i.getIssueId()).setFlexGrow(0);
    grid.addColumn(Issue::getTitle).setHeader(getTranslation("com.example.issues.title")).setFlexGrow(1);
    grid.addColumn(i -> i.getOwner() != null ? i.getOwner().getName() : "")
        .setHeader(getTranslation("com.example.issues.owner"))
        .setFlexGrow(0);
    grid.addColumn(i -> i.getReporter() != null ? i.getReporter().getName() : "")
        .setHeader(getTranslation("com.example.issues.reporter"))
        .setFlexGrow(0);
    grid.addColumn(issue -> getTranslation(issue.getStatus().getNameProperty()))
        .setHeader(getTranslation("com.example.issues.status"))
        .setFlexGrow(0);
    grid.addColumn(Issue::getDate)
        .setHeader(getTranslation("com.example.issues.date"))
        .setFlexGrow(0)
        .setWidth("10em"); //TODO navigate to issue function

    grid.addComponentColumn(
        i -> {
          final Button btnEdit = new Button(VaadinIcon.EDIT.create() ,
                                            e -> UI.getCurrent().navigate(EditIssueView.class , i.getIssueId()));

          final Button btnView = new Button(VaadinIcon.EYE.create() ,
                                            e -> UI.getCurrent().navigate(IssueView.class , i.getIssueId()));
          return new HorizontalLayout(
              btnView ,
              securityService.checkAgainstRoles(btnEdit , of(DEVELOPER , ADMIN)));
        }
    )
        .setFlexGrow(0)
        .setWidth("10em");
    grid.setSizeFull();

    getContent().add(viewTitle , filterLayout , grid);
    getContent().expand(grid);
    getContent().setSizeFull();

  }

  private void refreshGrid() {
    Set<Issue> issues = issueService.find(title.getValue() ,
                                          owner.getValue() ,
                                          reporter.getValue() ,
                                          status.getValue() ,
                                          date.getValue());
    grid.setItems(issues);
  }

}
