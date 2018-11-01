package org.rapidpm.vaadin.v10.issuetracker.ui.issues;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.BusinessAppModule;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.Session;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.ui.CreateIssueView;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.ui.IssuesView;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.Project;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ProjectRepository;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ui.CreateProjectView;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ui.ProjectsView;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.UserRepository;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.ui.UsersView;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.menu.UIConfiguration;
import org.rapidpm.vaadin.v10.issuetracker.ui.security.AuthenticationService;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.VaadinSession;

@VaadinSessionScoped
public class IssuesModule implements BusinessAppModule {


  @Inject private UIConfiguration uiConfiguration;
  @Inject private ProjectRepository projectRepository;
  @Inject private UserRepository userRepository;
  @Inject private Session session;
  private ComboBox<Project> projectsSelector;

  @Override
  public void initialize() {
    Long userId = (Long) VaadinSession.getCurrent().getAttribute(AuthenticationService.USER_ID_SESSION_KEY);
    User user = userRepository.findById(userId).get(); //TODO NPE
    session.setUserId(userId);
    session.setRole(user.getRole());

    List<Project> projects = projectRepository.findByMembersIn(user);
    setDefaultProject(projects);
    addHeaderOptions();
    addMenuOptions();
  }

  public void updateProjectsSelector() {
    User user = userRepository.findById(session.getUserId()).get();
    List<Project> projects = projectRepository.findByMembersIn(user);
    projectsSelector.setItems(projects);

    if (! projects.isEmpty()) {
      long projectId = session.getProjectId();
      Optional<Project> project = projects.stream().filter(p -> p.getId().equals(projectId)).findFirst();
      projectsSelector.setValue(project.orElse(projects.get(0)));
    }
  }

  private void setDefaultProject(List<Project> allProjects) {
    if (session.getProjectId() == null && ! allProjects.isEmpty()) {
      session.setProjectId(allProjects.get(0).getId());
    }
  }

  private void addHeaderOptions() {
    uiConfiguration.addHeaderComponent(() -> {
      projectsSelector = new ComboBox<>(null);
      projectsSelector.setItemLabelGenerator(Project::getName);
      updateProjectsSelector();
      projectsSelector.addValueChangeListener(e -> selectProject(e.getValue()));
      return projectsSelector;
    });
  }

  private void selectProject(Project selectedProject) {
    if (selectedProject != null) {
      session.setProjectId(selectedProject.getId());
      UI.getCurrent().navigate("");
    }
  }

  private void addMenuOptions() {
    uiConfiguration.addMenuOption(IssuesView.class , "com.example.issues.issues" , VaadinIcon.BUG);
    uiConfiguration.addMenuOption(CreateIssueView.class ,
                                  "com.example.issues.createIssue" ,
                                  VaadinIcon.PLUS
    );
    uiConfiguration.addMenuOption(ProjectsView.class , "com.example.issues.projects" , VaadinIcon.CODE);
    uiConfiguration.addMenuOption(CreateProjectView.class ,
                                  "com.example.issues.createProject" ,
                                  VaadinIcon.PLUS_SQUARE_O
    );
    uiConfiguration.addMenuOption(UsersView.class , "com.example.issues.users" , VaadinIcon.USERS);
  }

}
