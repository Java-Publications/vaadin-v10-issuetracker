package org.rapidpm.vaadin.v10.bugtracker.model.issue;

public class IssueFilter {
  private String titleContains;
  private String descriptionContains;
  private String reporterNameContains;
  private String owenerNameContains;
  public String getTitleContains() {
    return titleContains;
  }
  public void setTitleContains(String titleContains) {
    this.titleContains = titleContains;
  }
  public String getDescriptionContains() {
    return descriptionContains;
  }
  public void setDescriptionContains(String descriptionContains) {
    this.descriptionContains = descriptionContains;
  }
  public String getReporterNameContains() {
    return reporterNameContains;
  }
  public void setReporterNameContains(String reporterNameContains) {
    this.reporterNameContains = reporterNameContains;
  }
  public String getOwenerNameContains() {
    return owenerNameContains;
  }
  public void setOwenerNameContains(String owenerNameContains) {
    this.owenerNameContains = owenerNameContains;
  }

}
