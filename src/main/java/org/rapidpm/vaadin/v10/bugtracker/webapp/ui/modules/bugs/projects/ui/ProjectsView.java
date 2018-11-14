package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ui;

import static org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole.USER;
import static org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ui.ProjectsView.ROUTE;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.webapp.security.navigation.VisibleTo;
import org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle.I18NPageTitle;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.MainLayout;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.Project;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ProjectService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@I18NPageTitle(messageKey = "com.example.issues.projects")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(USER)
public class ProjectsView extends Composite<VerticalLayout> {

  public static final String ROUTE = "projects";

  private final Grid<Project> grid = new Grid<>();

  @Inject private ProjectService projectService;

  public ProjectsView() {
    Span viewTitle = new Span(getTranslation("com.example.issues.projects"));
    viewTitle.addClassName("view-title");

    grid.addColumn(Project::getName).setHeader(getTranslation("com.example.issues.name"));
    grid.addComponentColumn(p -> new Button(null ,
                                            VaadinIcon.EDIT.create() ,
                                            e -> UI.getCurrent().navigate(EditProjectView.class , p.getProjectId())));


    getContent().add(viewTitle , grid);
    getContent().setSizeFull();
  }


  @PostConstruct
  private void postConstruct(){
    grid.setItems(projectService.findAll());
  }

}
