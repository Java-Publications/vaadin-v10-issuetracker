package org.rapidpm.vaadin.v10.bugtracker.persistence.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = StatusEntity.TABLE_NAME)
@Cacheable
public class StatusEntity extends CoreEntity {
  public static final String TABLE_NAME = "status";

  @NotNull
  @Column(unique = true)
  private Integer orderNr;

  @NotNull
  @NotEmpty
  @Column(unique = true)
  private String name;

}
