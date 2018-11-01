package org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ui;

import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.IssuesModule;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.Project;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ProjectService;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.UserService;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.MainLayout;
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

@Route(value = CreateProjectView.ROUTE, layout = MainLayout.class)
public class CreateProjectView extends Composite<VerticalLayout> {

    public static final String ROUTE = "create-project";




    private TextField name = new TextField(getTranslation("com.example.issues.name"));
    private Grid<User> grid = new Grid<>();
    private MultiSelect<Grid<User>, User> members;

    @Inject private  ProjectService projectService;
    @Inject private  UserService userService;
    @Inject private  IssuesModule issuesModule;

    public CreateProjectView() {
        setPageTitel().accept(this , "com.example.issues.createProject");

        Span viewTitle = new Span(getTranslation("com.example.issues.createProject"));
        viewTitle.addClassName("view-title");

        name.setSizeFull();
        name.focus();

        grid.setId("members");
        grid.setWidth("100%");
        grid.addColumn(User::getName).setHeader(getTranslation("com.example.issues.name"));
        grid.addColumn(User::getEmail).setHeader(getTranslation("com.example.issues.email"));
        grid.addColumn(user -> getTranslation(user.getRole().getNameProperty()))
                .setHeader(getTranslation("com.example.issues.role"));
        grid.setItems(this.userService.findAll());
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        members = grid.asMultiSelect();

        Label membersLabel = new Label(getTranslation("com.example.issues.members"));
        membersLabel.setFor(grid);

        Button create = new Button(getTranslation("com.example.issues.create"), e -> create());
        create.getElement().setAttribute("theme", "primary");

        VerticalLayout formLayout = new VerticalLayout(viewTitle, name, membersLabel, grid, create);
        formLayout.setPadding(false);
        formLayout.setMargin(false);

        Div mainLayout = new Div(formLayout);
        mainLayout.setWidth("100%");

        getContent().add(mainLayout);
        getContent().setAlignSelf(FlexComponent.Alignment.END, create);
    }

    private void create() {
        try {
            Project project = new Project();
            BeanValidationBinder<Project> binder = new BeanValidationBinder<>(Project.class);
            binder.bindInstanceFields(this);
            binder.writeBean(project);
            projectService.saveOrUpdate(project);
            issuesModule.updateProjectsSelector();
            UI.getCurrent().navigate(ProjectsView.class);

        } catch (ValidationException e) {
            Notification.show(getTranslation("com.example.issues.validationError"));
        }
    }

}
