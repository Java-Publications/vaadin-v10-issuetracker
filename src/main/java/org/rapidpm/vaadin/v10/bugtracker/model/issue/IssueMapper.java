package org.rapidpm.vaadin.v10.bugtracker.model.issue;

import java.util.List;
import org.mapstruct.Mapper;
import org.rapidpm.vaadin.v10.bugtracker.model.user.UserMapper;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.IssueEntity;

@Mapper(uses = UserMapper.class, componentModel = "cdi")
public interface IssueMapper {
  Issue map(IssueEntity entity);

  List<Issue> map(List<IssueEntity> entities);
}
