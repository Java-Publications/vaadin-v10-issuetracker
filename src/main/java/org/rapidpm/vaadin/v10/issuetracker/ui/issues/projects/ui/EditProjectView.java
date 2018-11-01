package org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ui;

import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import java.util.Optional;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.dialog.ConfirmDialog;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "edit-project", layout = MainLayout.class)
public class EditProjectView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

    public static final String ROUTE = "issues";

    @Inject private ProjectService projectService;
    @Inject private UserService userService;
    @Inject private  IssuesModule issuesModule;

    private TextField name = new TextField(getTranslation("com.example.issues.name"));
    private Grid<User> grid = new Grid<>();
    private MultiSelect<Grid<User>, User> members;


    private BeanValidationBinder<Project> binder = new BeanValidationBinder<>(Project.class);

    public EditProjectView() {
        setPageTitel().accept(this , "com.example.issues.editProject");
    }

    @Override
    public void setParameter(BeforeEvent event, Long projectId) {
        Optional<Project> project = projectService.findById(projectId);
        if (!project.isPresent()) {
            UI.getCurrent().navigate(ProjectsView.class);
        } else {
            editProject(project.get());
        }
    }

    private void editProject(Project project) {
        Span viewTitle = new Span(getTranslation("com.example.issues.editProject"));
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

        Button delete = new Button(getTranslation("com.example.issues.delete"), e -> delete(project));
        delete.getElement().setAttribute("theme", "error");

        Button save = new Button(getTranslation("com.example.issues.save"), e -> save(project));
        save.getElement().setAttribute("theme", "primary");

        HorizontalLayout actionsLayout = new HorizontalLayout(delete, save);

        VerticalLayout formLayout = new VerticalLayout(viewTitle, name, membersLabel, grid, actionsLayout);
        formLayout.setPadding(false);
        formLayout.setMargin(false);

        Div mainLayout = new Div(formLayout);
        mainLayout.setWidth("100%");

        getContent().add(mainLayout);
        getContent().setAlignSelf(FlexComponent.Alignment.END, actionsLayout);

        binder.bindInstanceFields(this);
        binder.setBean(project);
    }

    private void delete(Project project) {
        new ConfirmDialog(getTranslation("com.example.issues.deleteProjectConfirmation"),
                          getTranslation("com.example.issues.yes"),
                          getTranslation("com.example.issues.no"),
                e -> {
                    projectService.delete(project);
                    issuesModule.updateProjectsSelector();
                    UI.getCurrent().navigate(ProjectsView.class);
                }).open();
    }

    private void save(Project project) {
        if (binder.validate().hasErrors()) {
            Notification.show(getTranslation("com.example.issues.validationError"));
        } else {
            projectService.saveOrUpdate(project);
            issuesModule.updateProjectsSelector();
            UI.getCurrent().navigate(ProjectsView.class);
        }
    }

}
