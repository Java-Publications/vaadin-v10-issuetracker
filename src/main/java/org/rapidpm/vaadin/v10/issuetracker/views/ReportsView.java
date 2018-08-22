package org.rapidpm.vaadin.v10.issuetracker.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.MainLayout;

import static org.rapidpm.vaadin.v10.issuetracker.views.ReportsView.NAV_REPORTS_VIEW;


@Route(NAV_REPORTS_VIEW)
public class ReportsView extends MainLayout implements HasLogger {
  public static final String NAV_REPORTS_VIEW = "reports";

  public ReportsView() {
    super();
    logger().info("customizing now the reports ui content..");
    middle().add(new Span("I am now the reports View..  add Content here"));
  }

}
