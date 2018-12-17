package com.vaadin.tutorial.issues.appmodules.issues.model.project;


import org.mapstruct.Mapper;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;
import com.vaadin.tutorial.issues.persistence.entities.module.issues.ProjectEntity;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserMapper;

@Mapper(
    uses = {
        UserMapper.class
    },
    componentModel = "cdi")
public interface ProjectMapper {

  Project fromEntity(ProjectEntity entity);


}
