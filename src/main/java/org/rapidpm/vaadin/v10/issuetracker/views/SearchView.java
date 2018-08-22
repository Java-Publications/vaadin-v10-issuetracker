package org.rapidpm.vaadin.v10.issuetracker.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.MainLayout;

import static org.rapidpm.vaadin.v10.issuetracker.views.SearchView.NAV_SEARCH_VIEW;

@Route(NAV_SEARCH_VIEW)
public class SearchView extends MainLayout implements HasLogger {
  public static final String NAV_SEARCH_VIEW = "search";

  public SearchView() {
    super();
    logger().info("customizing now the search ui content..");
    middle().add(new Span("I am now the search View..  add Content here"));
  }
}
