package com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui;

import static com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui.IssuesView.ROUTE;
import static java.util.Set.of;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
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
import com.vaadin.tutorial.issues.appmodules.issues.BugsModule;
import com.vaadin.tutorial.issues.appmodules.issues.model.issue.Issue;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatus;
import com.vaadin.tutorial.issues.appmodules.issues.ui.issues.IssueService;
import com.vaadin.tutorial.issues.webapp.security.SecurityService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.issues")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(USER)
@UIScoped
public class IssuesView extends Composite<VerticalLayout> {

  public static final String ROUTE = "issuetracker";

  @Inject private IssueService issueService;
  @Inject private SecurityService securityService;

  @Inject private BugsModule bugsModule; //TODO Hack for init

  private final Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.issues"));
  private final TextField title = new TextField();
  private final TextField reporter = new TextField();
  private final TextField owner = new TextField();
  private final ComboBox<IssueStatus> status = new ComboBox<>(null , IssueStatus.values());
  private final DatePicker date = new DatePicker();
  private final Grid<Issue> grid = new Grid<>();


  @PostConstruct
  private void postConstruct() {
    refreshGrid();
  }

  public IssuesView() {
    viewTitle.addClassName("view-title");

    title.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.title"));
    title.setWidth("100%");
    title.addValueChangeListener(e -> refreshGrid());
    title.setSizeFull();

    reporter.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.reporter"));
    reporter.setWidth("100%");
    reporter.addValueChangeListener(e -> refreshGrid());
    reporter.setSizeFull();

    owner.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.owner"));
    owner.setWidth("100%");
    owner.addValueChangeListener(e -> refreshGrid());
    owner.setSizeFull();

    status.setValue(IssueStatus.OPEN);
    status.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.status"));
    status.setWidth("100%");
    status.addValueChangeListener(e -> refreshGrid());
    status.setItemLabelGenerator(status -> getTranslation(status.getNameProperty()));

    date.setPlaceholder(getTranslation("com.vaadin.tutorial.issues.date"));
    date.setWidth("100%");

    HorizontalLayout filterLayout = new HorizontalLayout(title , owner , reporter , status , date);
    filterLayout.setWidth("100%");
    grid.addColumn(i -> "#" + i.getIssueId())
        .setHeader(getTranslation("com.vaadin.tutorial.issues.id"))
        .setFlexGrow(0);
    grid.addColumn(Issue::getTitle)
        .setHeader(getTranslation("com.vaadin.tutorial.issues.title"))
        .setFlexGrow(1);
    grid.addColumn(i -> i.getOwner() != null ? i.getOwner().getName() : "")
        .setHeader(getTranslation("com.vaadin.tutorial.issues.owner"))
        .setFlexGrow(0);
    grid.addColumn(i -> i.getReporter() != null ? i.getReporter().getName() : "")
        .setHeader(getTranslation("com.vaadin.tutorial.issues.reporter"))
        .setFlexGrow(0);
    grid.addColumn(issue -> getTranslation(issue.getStatus().getNameProperty()))
        .setHeader(getTranslation("com.vaadin.tutorial.issues.status"))
        .setFlexGrow(0);
    grid.addColumn(Issue::getDate)
        .setHeader(getTranslation("com.vaadin.tutorial.issues.date"))
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
