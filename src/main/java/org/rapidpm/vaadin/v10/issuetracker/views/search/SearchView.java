package org.rapidpm.vaadin.v10.issuetracker.views.search;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.MainLayout;

import java.util.stream.Stream;

import static org.rapidpm.vaadin.addons.framework.ComponentIDGenerator.*;
import static org.rapidpm.vaadin.v10.issuetracker.views.search.SearchView.NAV_SEARCH_VIEW;

@Route(NAV_SEARCH_VIEW)
public class SearchView extends MainLayout implements HasLogger {
  public static final String NAV_SEARCH_VIEW = "search";

  public static final String ID_SEARCH_VIEW_LAYOUT = verticalLayoutID().apply(SearchView.class, "search_view_layout");
  public static final String ID_SEARCH_RESULT_GRID = gridID().apply(SearchView.class, "search-result-grid");

  public static final String ID_FULLTEXT_SEARCH_TF = textfieldID().apply(SearchView.class, "fulltext-search-tf");

  public static final String ID_PROJECT_TF  = textfieldID().apply(SearchView.class, "project-id-tf");
  public static final String ID_ISSUEID_TF  = textfieldID().apply(SearchView.class, "issue-id-tf");
  public static final String ID_TYPE_CB     = comboBoxID().apply(SearchView.class, "type-cb");
  public static final String ID_PRIORITY_CB = comboBoxID().apply(SearchView.class, "priority-cb");
  public static final String ID_STATUS_CB   = comboBoxID().apply(SearchView.class, "status-cb");
  public static final String ID_ASSIGNEE_TF = textfieldID().apply(SearchView.class, "assignee-tf");

  public static final String ID_FILTER_BAR_LAYOUT = horizontalLayoutID().apply(SearchView.class, "filter_bar_layout");

  private final TextField fulltextSearchField = new TextField() {{
    setId(ID_FULLTEXT_SEARCH_TF);
    setPlaceholder("full text search..");
    setPrefixComponent(VaadinIcon.SEARCH.create());
    setWidth("100%");

    setValueChangeMode(ValueChangeMode.EAGER);
    addValueChangeListener( event -> logger().info(" search field value changed... " + event.getValue()));

  }};

  private final TextField projectIDSearchField = new TextField() {{
    setId(ID_PROJECT_TF);
    setPlaceholder("project ID");
    setValueChangeMode(ValueChangeMode.EAGER);
    addValueChangeListener( event -> logger().info(" project id field value changed... " + event.getValue()));
  }};

  private final TextField issueIDSearchField = new TextField() {{
    setId(ID_ISSUEID_TF);
    setPlaceholder("issue ID");
    setValueChangeMode(ValueChangeMode.EAGER);
    addValueChangeListener( event -> logger().info(" issue id field value changed... " + event.getValue()));
  }};

  private final ComboBox<String> typeSearchField = new ComboBox<>() {{
    setId(ID_TYPE_CB);
    setPlaceholder("type");
    setItems(Stream.of("Bug", "Task", "Epic", "SubTask", "Question"));

    addValueChangeListener( event -> logger().info(" type value changed... " + event.getValue()));
  }};

  private final ComboBox<String> prioritySearchField = new ComboBox<>() {{
    setId(ID_PRIORITY_CB);
    setPlaceholder("priority");
    setItems(Stream.of("Critical", "Major", "Minor", "Useless"));

    addValueChangeListener( event -> logger().info(" priority field value changed... " + event.getValue()));
  }};

  private final ComboBox<String> statusSearchField   = new ComboBox<>() {{
    setId(ID_STATUS_CB);
    setPlaceholder("status");
    setItems(Stream.of("On Hold", "Accepted", "In Progress", "Done"));

    addValueChangeListener( event -> logger().info(" status field value changed... " + event.getValue()));
  }};

  private final TextField assigneeSearchField = new TextField() {{
    setId(ID_ASSIGNEE_TF);
    setPlaceholder("assignee");

    setValueChangeMode(ValueChangeMode.ON_CHANGE);
    addValueChangeListener( event -> logger().info(" assignee field value changed... " + event.getValue()));
  }};


  private final FormLayout filterBar = new FormLayout() {{
    setId(ID_FILTER_BAR_LAYOUT);

    add(projectIDSearchField,
        issueIDSearchField,
        typeSearchField,
        prioritySearchField,
        statusSearchField,
        assigneeSearchField
    );

    setWidth("100%");
  }};




  private final Grid<SearchResultItem> searchResultGrid = new Grid<>() {{
    setId(ID_SEARCH_RESULT_GRID);
    setSelectionMode(SelectionMode.SINGLE);
    setMultiSort(true);

    //TODO if role issue -> issue ID as link to view

    addColumn(SearchResultItem::getProjectID).setHeader("Project ID").setSortable(true).setResizable(true);
    addColumn(SearchResultItem::getIssueID).setHeader("Issue ID").setSortable(true).setResizable(true);
    addColumn(SearchResultItem::getSubject).setHeader("Subject").setSortable(true).setResizable(true);
    addColumn(SearchResultItem::getType).setHeader("Type").setSortable(true).setResizable(true);
    addColumn(SearchResultItem::getPriority).setHeader("Priority").setSortable(true).setResizable(true);
    addColumn(SearchResultItem::getStatus).setHeader("Status").setSortable(true).setResizable(true);
    addColumn(SearchResultItem::getAssignee).setHeader("Assignee").setSortable(true).setResizable(true);





  }};


  private final VerticalLayout searchViewLayout = new VerticalLayout() {{
    setId(ID_SEARCH_VIEW_LAYOUT);


    add(fulltextSearchField);
    add(filterBar);

    add(searchResultGrid);
    expand(searchResultGrid);

  }};


  public SearchView() {
    super();
    logger().info("customizing now the search ui content..");


    middle().add(searchViewLayout);
    middle().expand(searchViewLayout);
  }
}
