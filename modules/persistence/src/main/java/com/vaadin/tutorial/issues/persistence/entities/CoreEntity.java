package com.vaadin.tutorial.issues.persistence.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CoreEntity {


  public static final String COL_NAME_ID = "id";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = COL_NAME_ID)
  private Long id;

  @Basic
  private boolean deleted;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

}
