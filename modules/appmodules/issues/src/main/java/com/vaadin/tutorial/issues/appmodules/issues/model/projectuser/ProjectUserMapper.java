package com.vaadin.tutorial.issues.appmodules.issues.model.projectuser;


import org.mapstruct.Mapper;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatusMapper;
import com.vaadin.tutorial.issues.persistence.entities.module.issues.ProjectUserEntity;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserMapper;

@Mapper(
    uses = {
        UserMapper.class,
        IssueStatusMapper.class
    },
    componentModel = "cdi")
public interface ProjectUserMapper {


  ProjectUser fromEntytiy(ProjectUserEntity entity);

  ProjectUserEntity fromPojo(ProjectUser pojo);



}
