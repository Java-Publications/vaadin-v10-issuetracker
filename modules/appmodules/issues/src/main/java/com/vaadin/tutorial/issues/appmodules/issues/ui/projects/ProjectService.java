package com.vaadin.tutorial.issues.appmodules.issues.ui.projects;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.tutorial.issues.persistence.entities.module.issues.ProjectRepository;
import com.vaadin.tutorial.issues.persistence.entities.module.security.UserRepository;

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
