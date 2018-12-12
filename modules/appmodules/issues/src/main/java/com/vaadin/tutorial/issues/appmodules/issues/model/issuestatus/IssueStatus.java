package com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus;

import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;

import java.io.Serializable;

public enum IssueStatus implements Serializable {

  SUBMITTED("com.vaadin.tutorial.issues.submitted"),
  OPEN("com.vaadin.tutorial.issues.open"),
  IN_PROGRESS("com.vaadin.tutorial.issues.in-progress"),
  ON_HOLD("com.vaadin.tutorial.issues.on-hold"),
  DONE("com.vaadin.tutorial.issues.done"),
  OBSOLETE("com.vaadin.tutorial.issues.obsolete");

  final String nameProperty;

  IssueStatus(String nameProperty) {
    this.nameProperty = nameProperty;
  }

  public String getNameProperty() {
    return nameProperty;
  }

  public static IssueStatus byNameProperty(String key) {
    return match(
        matchCase(() -> failure("requested key has no corresponding ENUM  " + key)) ,
        matchCase(() -> key.equals(SUBMITTED.nameProperty) , () -> success(SUBMITTED)) ,
        matchCase(() -> key.equals(OPEN.nameProperty) , () -> success(OPEN)) ,
        matchCase(() -> key.equals(IN_PROGRESS.nameProperty) , () -> success(IN_PROGRESS)) ,
        matchCase(() -> key.equals(ON_HOLD.nameProperty) , () -> success(ON_HOLD)),
        matchCase(() -> key.equals(DONE.nameProperty) , () -> success(DONE)),
        matchCase(() -> key.equals(OBSOLETE.nameProperty) , () -> success(OBSOLETE))
    )
        .ifFailed(message -> {
          throw new RuntimeException(message);
        })
        .get();
  }

}
