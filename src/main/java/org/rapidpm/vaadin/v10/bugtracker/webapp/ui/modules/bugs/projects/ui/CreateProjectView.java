package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ui;

import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.ADMIN;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation.VisibleTo;
import org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle.I18NPageTitle;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.UserService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.BugsModule;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.Project;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ProjectService;
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

@I18NPageTitle(messageKey = "com.example.issues.createProject")
@Route(value = CreateProjectView.ROUTE, layout = MainLayout.class)
@VisibleTo(ADMIN)
public class CreateProjectView extends Composite<VerticalLayout> {

  public static final String ROUTE = "create-project";


  private TextField name = new TextField(getTranslation("com.example.issues.name"));
  private Grid<User> grid = new Grid<>();
  private MultiSelect<Grid<User>, User> members;

  @Inject private ProjectService projectService;
  @Inject private UserService userService;
  @Inject private BugsModule bugsModule;

  public CreateProjectView() {
    Span viewTitle = new Span(getTranslation("com.example.issues.createProject"));
    viewTitle.addClassName("view-title");

    name.setSizeFull();
    name.focus();

    grid.setId("members");
    grid.setWidth("100%");
    grid.addColumn(User::getName).setHeader(getTranslation("com.example.issues.name"));
    grid.addColumn(User::getEmail).setHeader(getTranslation("com.example.issues.email"));
//    grid.addColumn(user -> getTranslation(user.getRole().getNameProperty()))
//        .setHeader(getTranslation("com.example.issues.role"));

    grid.setSelectionMode(Grid.SelectionMode.MULTI);
    members = grid.asMultiSelect();

    Label membersLabel = new Label(getTranslation("com.example.issues.members"));
    membersLabel.setFor(grid);

    Button create = new Button(getTranslation("com.example.issues.create") , e -> create());
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
      Notification.show(getTranslation("com.example.issues.validationError"));
    }
  }

}
