package org.rapidpm.vaadin.v10.issuetracker.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BusinessAppEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
