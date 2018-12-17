package com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus;


import org.mapstruct.Mapper;
import com.vaadin.tutorial.issues.persistence.entities.module.issues.IssueStatusEntity;

@Mapper(
    componentModel = "cdi")
public interface IssueStatusMapper {

  //Mapping from non-enum to enum type
  default IssueStatus toIssueStatus(String key) {
    return IssueStatus.byNameProperty(key);
  }

  default IssueStatus toIssueStatus(IssueStatusEntity e) {
    return IssueStatus.byNameProperty(e.getName());
  }
}
