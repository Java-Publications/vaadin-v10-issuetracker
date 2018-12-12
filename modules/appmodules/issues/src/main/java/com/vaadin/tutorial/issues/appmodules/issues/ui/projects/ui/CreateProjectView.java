package com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ui;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.appmodules.issues.BugsModule;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ProjectService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.createProject")
@Route(value = CreateProjectView.ROUTE, layout = MainLayout.class)
@VisibleTo(ADMIN)
@UIScoped
public class CreateProjectView extends Composite<VerticalLayout> {

  public static final String ROUTE = "create-project";


  private TextField name = new TextField(getTranslation("com.vaadin.tutorial.issues.name"));
  private Grid<User> grid = new Grid<>();
  private MultiSelect<Grid<User>, User> members;

  @Inject private ProjectService projectService;
  @Inject private UserService userService;
  @Inject private BugsModule bugsModule;

  public CreateProjectView() {
    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.createProject"));
    viewTitle.addClassName("view-title");

    name.setSizeFull();
    name.focus();

    grid.setId("members");
    grid.setWidth("100%");
    grid.addColumn(User::getName).setHeader(getTranslation("com.vaadin.tutorial.issues.name"));
    grid.addColumn(User::getEmail).setHeader(getTranslation("com.vaadin.tutorial.issues.email"));
//    grid.addColumn(user -> getTranslation(user.getRole().getNameProperty()))
//        .setHeader(getTranslation("com.vaadin.tutorial.issuetracker.role"));

    grid.setSelectionMode(Grid.SelectionMode.MULTI);
    members = grid.asMultiSelect();

    Label membersLabel = new Label(getTranslation("com.vaadin.tutorial.issues.members"));
    membersLabel.setFor(grid);

    Button create = new Button(getTranslation("com.vaadin.tutorial.issues.create") , e -> create());
    create.getElement().setAttribute("theme" , "primary");

    VerticalLayout formLayout = new VerticalLayout(viewTitle , name , membersLabel , grid , create);
    formLayout.setPadding(false);
    formLayout.setMargin(false);

    Div mainLayout = new Div(formLayout);
    mainLayout.setWidth("100%");

    getContent().add(mainLayout);
    getContent().setAlignSelf(FlexComponent.Alignment.END , create);
  }

  @PostConstruct
  private void postConstruct(){
    grid.setItems(this.userService.findAll());
  }


  private void create() {
    try {
      Project project = new Project();
      BeanValidationBinder<Project> binder = new BeanValidationBinder<>(Project.class);
      binder.bindInstanceFields(this);
      binder.writeBean(project);
      projectService.saveOrUpdate(project);
      bugsModule.updateProjectsSelectorItems();
      UI.getCurrent().navigate(ProjectsView.class);

    } catch (ValidationException e) {
      Notification.show(getTranslation("com.vaadin.tutorial.issues.validationError"));
    }
  }

}
