package org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.UserRepository;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class ProjectService {

    @Inject private UserRepository userRepository;
    @Inject private ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    public void delete(Project project) {
        project.setDeleted(true);
        projectRepository.save(project);
    }

    public void saveOrUpdate(Project project) {
        projectRepository.save(project);
    }

}
