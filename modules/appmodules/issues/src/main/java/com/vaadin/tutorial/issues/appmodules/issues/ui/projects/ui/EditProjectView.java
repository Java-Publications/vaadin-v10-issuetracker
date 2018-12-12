package com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ui;


import java.util.Optional;

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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.appmodules.issues.BugsModule;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ProjectService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.dialog.ConfirmDialog;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.editProject")
@Route(value = "edit-project", layout = MainLayout.class)
@VisibleTo(DEVELOPER)
@UIScoped
public class EditProjectView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

  public static final String ROUTE = "issuetracker";

  @Inject private ProjectService projectService;
  @Inject private UserService userService;
  @Inject private BugsModule bugsModule;

  private TextField name = new TextField(getTranslation("com.vaadin.tutorial.issues.name"));
  private Grid<User> grid = new Grid<>();
  private MultiSelect<Grid<User>, User> members;


  private BeanValidationBinder<Project> binder = new BeanValidationBinder<>(Project.class);

  @Override
  public void setParameter(BeforeEvent event , Long projectId) {
    Optional<Project> project = projectService.findById(projectId);
    if (! project.isPresent()) {
      UI.getCurrent().navigate(ProjectsView.class);
    } else {
      editProject(project.get());
    }
  }

  private void editProject(Project project) {
    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.editProject"));
    viewTitle.addClassName("view-title");

    name.setSizeFull();
    name.focus();

    grid.setId("members");
    grid.setWidth("100%");
    grid.addColumn(User::getName).setHeader(getTranslation("com.vaadin.tutorial.issues.name"));
    grid.addColumn(User::getEmail).setHeader(getTranslation("com.vaadin.tutorial.issues.email"));
//    grid.addColumn(user -> getTranslation(user.getRole().getNameProperty()))
//        .setHeader(getTranslation("com.vaadin.tutorial.issuetracker.role"));
    grid.setItems(this.userService.findAll());
    grid.setSelectionMode(Grid.SelectionMode.MULTI);
    members = grid.asMultiSelect();

    Label membersLabel = new Label(getTranslation("com.vaadin.tutorial.issues.members"));
    membersLabel.setFor(grid);

    Button delete = new Button(getTranslation("com.vaadin.tutorial.issues.delete") , e -> delete(project));
    delete.getElement().setAttribute("theme" , "error");

    Button save = new Button(getTranslation("com.vaadin.tutorial.issues.save") , e -> save(project));
    save.getElement().setAttribute("theme" , "primary");

    HorizontalLayout actionsLayout = new HorizontalLayout(delete , save);

    VerticalLayout formLayout = new VerticalLayout(viewTitle , name , membersLabel , grid , actionsLayout);
    formLayout.setPadding(false);
    formLayout.setMargin(false);

    Div mainLayout = new Div(formLayout);
    mainLayout.setWidth("100%");

    getContent().add(mainLayout);
    getContent().setAlignSelf(FlexComponent.Alignment.END , actionsLayout);

    binder.bindInstanceFields(this);
    binder.setBean(project);
  }

  private void delete(Project project) {
    new ConfirmDialog(getTranslation("com.vaadin.tutorial.issues.deleteProjectConfirmation") ,
                      getTranslation("com.vaadin.tutorial.issues.yes") ,
                      getTranslation("com.vaadin.tutorial.issues.no") ,
                      e -> {
                        projectService.delete(project);
                        bugsModule.updateProjectsSelectorItems();
                        UI.getCurrent().navigate(ProjectsView.class);
                      }).open();
  }

  private void save(Project project) {
    if (binder.validate().hasErrors()) {
      Notification.show(getTranslation("com.vaadin.tutorial.issues.validationError"));
    } else {
      projectService.saveOrUpdate(project);
      bugsModule.updateProjectsSelectorItems();
      UI.getCurrent().navigate(ProjectsView.class);
    }
  }

}
