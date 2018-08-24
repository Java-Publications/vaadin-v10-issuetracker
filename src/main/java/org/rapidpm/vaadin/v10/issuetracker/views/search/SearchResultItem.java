package org.rapidpm.vaadin.v10.issuetracker.views.search;

import java.io.Serializable;
import java.util.Objects;

public class SearchResultItem implements Serializable {

  private String issueID;
  private String projectID;
  private String subject;
  private String type;
  private String priority;
  private String status;
  private String assignee;

  @Override
  public String toString() {
    return "SearchResultItem{" +
           "issueID='" + issueID + '\'' +
           ", projectID='" + projectID + '\'' +
           ", subject='" + subject + '\'' +
           ", type='" + type + '\'' +
           ", priority='" + priority + '\'' +
           ", status='" + status + '\'' +
           ", assignee='" + assignee + '\'' +
           '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SearchResultItem)) return false;
    SearchResultItem that = (SearchResultItem) o;
    return Objects.equals(issueID, that.issueID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issueID, projectID, subject, type, priority, status, assignee);
  }

  public String getIssueID() {
    return issueID;
  }

  public void setIssueID(String issueID) {
    this.issueID = issueID;
  }

  public String getProjectID() {
    return projectID;
  }

  public void setProjectID(String projectID) {
    this.projectID = projectID;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }
}
