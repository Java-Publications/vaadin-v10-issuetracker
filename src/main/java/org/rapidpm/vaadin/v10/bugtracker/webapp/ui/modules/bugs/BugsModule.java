package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs;

import static java.util.Collections.unmodifiableSet;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.model.user.UserRepository;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.menu.UIConfiguration;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.ui.UsersView;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui.CreateIssueView;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.ui.IssuesView;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.Project;
import org.rapidpm.vaadin.v10.bugtracker.model.ProjectRepository;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ui.CreateProjectView;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.ui.ProjectsView;
import org.rapidpm.vaadin.v10.issuetracker.BusinessAppModule;
import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.VaadinSession;

@UIScoped
public class BugsModule implements BusinessAppModule , HasLogger {


  @Inject private UIConfiguration uiConfiguration;
  @Inject private ProjectRepository projectRepository;
  @Inject private UserRepository userRepository;
  @Inject private BugtrackerSessionState bugtrackerSessionState;

  private ComboBox<Project> projectsSelector;

  @Override
  @PostConstruct
  public void initialize() {
    User sessionUser = VaadinSession.getCurrent().getAttribute(User.class);
    logger().info("active sessionUser " + sessionUser);

    if(sessionUser == null){
      logger().warning(" no User in Session.. skip ModuleInit for " + BugsModule.class.getSimpleName());
    } else {

//    User user = userRepository.findById(userId).get(); //TODO do not ask twice
      bugtrackerSessionState.setUserId(sessionUser.getUserId());
      bugtrackerSessionState.setRoles(unmodifiableSet(sessionUser.getRoles()));

      List<Project> projects = projectRepository.findByMembersIn(sessionUser);
      setDefaultProject(projects);
      addHeaderOptions();
      addMenuOptions();
    }
  }

  public void updateProjectsSelector() {
    Long userId = bugtrackerSessionState.getUserId();
    logger().info("updateProjectsSelector for UserID " + userId);
    User user = userRepository.findById(userId).get();
    List<Project> projects = projectRepository.findByMembersIn(user);
    projectsSelector.setItems(projects);

    if (! projects.isEmpty()) {
      long projectId = bugtrackerSessionState.getProjectId();
      //TODO Performance Problem against DB
      Optional<Project> project = projects.stream().filter(p -> p.getProjectId().equals(projectId)).findFirst();
      projectsSelector.setValue(project.orElse(projects.get(0)));
    }
  }

  private void setDefaultProject(List<Project> allProjects) {
    if (bugtrackerSessionState.getProjectId() == null && ! allProjects.isEmpty()) {
      bugtrackerSessionState.setProjectId(allProjects.get(0).getProjectId());
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
      bugtrackerSessionState.setProjectId(selectedProject.getProjectId());
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
