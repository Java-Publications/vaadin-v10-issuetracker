package org.rapidpm.vaadin.v10.issuetracker.views.issue;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import org.rapidpm.vaadin.v10.issuetracker.MainLayout;

import static org.rapidpm.vaadin.v10.issuetracker.views.issue.IssueView.NAV_ISSUE_VIEW;


@Route(NAV_ISSUE_VIEW)
public class IssueView extends MainLayout {
  public static final String NAV_ISSUE_VIEW = "issue";


  private String issueID;
  private String projectID;
  private String subject;
  private String type;
  private String priority;
  private String status;
  private String assignee;


  public IssueView() {
    super();
    middle().add(new Span("I am now the issue View..  add Content here"));
  }

}
