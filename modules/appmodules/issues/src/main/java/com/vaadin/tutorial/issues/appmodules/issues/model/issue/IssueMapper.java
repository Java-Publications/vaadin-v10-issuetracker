package com.vaadin.tutorial.issues.appmodules.issues.model.issue;


import org.mapstruct.Mapper;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatusMapper;
import com.vaadin.tutorial.issues.persistence.entities.module.issues.IssueEntity;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserMapper;

@Mapper(
    uses = {
        UserMapper.class,
        IssueStatusMapper.class
    },
    componentModel = "cdi")
public interface IssueMapper {

    Issue fromEntity(IssueEntity entity);

}
