package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues;

public enum Status {

    OPEN("issue.statusOpen"),
    IN_PROGRESS("issue.statusInProgress"),
    CLOSED("issue.statusClosed");

    final String nameProperty;

    Status(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }

}
