package com.vaadin.tutorial.issues.persistence.entities.module.issues;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.vaadin.tutorial.issues.persistence.entities.CoreEntity;

@Entity
@Table(name = IssueStatusEntity.TABLE_NAME)
//@Cacheable
public class IssueStatusEntity extends CoreEntity {
  public static final String TABLE_NAME = "issuestatus";

  @NotNull
  @Column(name = "order_nr", unique = true)
  private Integer orderNr;

  @NotNull
  @NotEmpty
  @Column(unique = true)
  private String name;


  public Integer getOrderNr() {
    return orderNr;
  }

  public void setOrderNr(Integer orderNr) {
    this.orderNr = orderNr;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "IssueStatusEntity{" +
           "orderNr=" + orderNr +
           ", name='" + name + '\'' +
           '}';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (! (o instanceof IssueStatusEntity)) return false;
    IssueStatusEntity that = (IssueStatusEntity) o;
    return getId() == that.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
