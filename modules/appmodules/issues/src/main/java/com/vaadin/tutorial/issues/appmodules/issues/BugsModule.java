package com.vaadin.tutorial.issues.appmodules.issues;

import static java.util.Collections.unmodifiableSet;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.tutorial.issues.appmodules.api.BusinessAppModule;
import com.vaadin.tutorial.issues.appmodules.issues.model.project.ProjectRepository;
import com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui.CreateIssueView;
import com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui.IssuesView;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ui.CreateProjectView;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.ui.ProjectsView;
import com.vaadin.tutorial.issues.persistence.entities.UserRepository;
import com.vaadin.tutorial.issues.webapp.ui.layout.menu.UIConfiguration;

@UIScoped
public class BugsModule implements BusinessAppModule, HasLogger {

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

  public void updateProjectsSelectorItems() {
    Long userId = bugtrackerSessionState.getUserId();
    logger().info("updateProjectsSelectorItems for UserID " + userId);
    //TODO NPE
    User user = userRepository.findById(userId).get();
    List<Project> projects = projectRepository.findByMembersIn(user);
    projectsSelector.setItems(projects);

    if (! projects.isEmpty()) {
      long projectId = bugtrackerSessionState.getProjectId();
      Optional<Project> project = projects
          .stream()
          .filter(p -> p.getProjectId().equals(projectId))
          .findFirst();
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
      projectsSelector = new ComboBox<>();
      projectsSelector.setPlaceholder(projectsSelector.getTranslation("com.vaadin.tutorial.issues.project-selector"));
      projectsSelector.setItemLabelGenerator(Project::getName);
      projectsSelector.setWidth("250px");
      projectsSelector.addValueChangeListener(e -> selectProject(e.getValue()));
      updateProjectsSelectorItems();
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
    uiConfiguration.addMenuOption(IssuesView.class ,
                                  "com.vaadin.tutorial.issues.issues" ,
                                  VaadinIcon.BUG);
    uiConfiguration.addMenuOption(CreateIssueView.class ,
                                  "com.vaadin.tutorial.issues.createIssue" ,
                                  VaadinIcon.PLUS
    );
    uiConfiguration.addMenuOption(ProjectsView.class ,
                                  "com.vaadin.tutorial.issues.projects" ,
                                  VaadinIcon.CODE);
    uiConfiguration.addMenuOption(CreateProjectView.class ,
                                  "com.vaadin.tutorial.issues.createProject" ,
                                  VaadinIcon.PLUS_SQUARE_O
    );
//    uiConfiguration.addMenuOption(UsersView.class ,
//                                  "com.vaadin.tutorial.issues.users" ,
//                                  VaadinIcon.USERS);
  }

}
