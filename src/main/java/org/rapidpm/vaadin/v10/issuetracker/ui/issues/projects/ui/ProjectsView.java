package org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ui;

import static org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ui.ProjectsView.ROUTE;
import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.Project;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ProjectService;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = ROUTE, layout = MainLayout.class)
public class ProjectsView extends Composite<VerticalLayout> {

    public static final String ROUTE = "projects";


    @Inject private ProjectService projectService;

    public ProjectsView() {
        setPageTitel().accept(this , "com.example.issues.projects");

        Span viewTitle = new Span(getTranslation("com.example.issues.projects"));
        viewTitle.addClassName("view-title");

        Grid<Project> grid = new Grid<>();
        grid.addColumn(Project::getName).setHeader(getTranslation("com.example.issues.name"));
        grid.addComponentColumn(p -> new Button(null,
                VaadinIcon.EDIT.create(),
                e -> UI.getCurrent().navigate(EditProjectView.class, p.getId())));
        grid.setItems(projectService.findAll());

        getContent().add(viewTitle, grid);
        getContent().setSizeFull();
    }

}
