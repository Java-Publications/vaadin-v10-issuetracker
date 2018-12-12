package com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ui;


import static com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ui.ProjectsView.ROUTE;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ProjectService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.projects")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(USER)
@UIScoped
public class ProjectsView extends Composite<VerticalLayout> {

  public static final String ROUTE = "projects";

  private final Grid<Project> grid = new Grid<>();

  @Inject private ProjectService projectService;

  public ProjectsView() {
    Span viewTitle = new Span(getTranslation("com.vaadin.tutorial.issues.projects"));
    viewTitle.addClassName("view-title");

    grid.addColumn(Project::getName).setHeader(getTranslation("com.vaadin.tutorial.issues.name"));
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
