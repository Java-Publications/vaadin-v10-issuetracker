package org.rapidpm.vaadin.v10.issuetracker.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.MainLayout;

import static org.rapidpm.vaadin.v10.issuetracker.views.IssueView.NAV_ISSUE_VIEW;


@Route(NAV_ISSUE_VIEW)
public class IssueView extends MainLayout implements HasLogger {
  public static final String NAV_ISSUE_VIEW = "issue";

  public IssueView() {
    super();
    logger().info("customizing now the issue ui content..");
    middle().add(new Span("I am now the issue View..  add Content here"));
  }

}
