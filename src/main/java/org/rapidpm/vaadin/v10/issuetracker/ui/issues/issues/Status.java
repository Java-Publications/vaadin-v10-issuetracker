package org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues;

public enum Status {

    OPEN("com.example.issues.statusOpen"),
    IN_PROGRESS("com.example.issues.statusInProgress"),
    CLOSED("com.example.issues.statusClosed");

    final String nameProperty;

    Status(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }

}
