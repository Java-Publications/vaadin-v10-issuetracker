package org.rapidpm.vaadin.v10.issuetracker.views.search;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.rapidpm.vaadin.v10.issuetracker.MainLayout;

import java.util.stream.Stream;

import static org.rapidpm.vaadin.v10.issuetracker.views.search.SearchView.NAV_SEARCH_VIEW;

@Route(NAV_SEARCH_VIEW)
public class SearchView extends MainLayout {
  public static final String NAV_SEARCH_VIEW = "search";


  private final TextField        fulltextSearchField  = new TextField();
  private final TextField        projectIDSearchField = new TextField();
  private final TextField        issueIDSearchField   = new TextField();
  private final ComboBox<String> typeSearchField      = new ComboBox<>();
  private final ComboBox<String> prioritySearchField  = new ComboBox<>();
  private final ComboBox<String> statusSearchField    = new ComboBox<>();
  private final TextField        assigneeSearchField  = new TextField();

  private final FormLayout filterBar = new FormLayout(projectIDSearchField,
                                                      issueIDSearchField,
                                                      typeSearchField,
                                                      prioritySearchField,
                                                      statusSearchField,
                                                      assigneeSearchField
  );

  private final Grid<SearchResultItem> searchResultGrid = new Grid<>();
  private final VerticalLayout         searchViewLayout = new VerticalLayout(
      fulltextSearchField,
      filterBar,
      searchResultGrid
  );


  public SearchView() {
    super();

    fulltextSearchField.setPlaceholder("full text search..");
    fulltextSearchField.setPrefixComponent(VaadinIcon.SEARCH.create());
    fulltextSearchField.setWidth("100%");

    fulltextSearchField.setValueChangeMode(ValueChangeMode.EAGER);
    fulltextSearchField.addValueChangeListener(event -> System.out.println(" search field value changed... " + event.getValue()));

    projectIDSearchField.setPlaceholder("project ID");
    projectIDSearchField.setValueChangeMode(ValueChangeMode.EAGER);
    projectIDSearchField.addValueChangeListener(event -> System.out.println(" project id field value changed... " + event.getValue()));

    issueIDSearchField.setPlaceholder("issue ID");
    issueIDSearchField.setValueChangeMode(ValueChangeMode.EAGER);
    issueIDSearchField.addValueChangeListener(event -> System.out.println(" issue id field value changed... " + event.getValue()));

    typeSearchField.setPlaceholder("type");
    typeSearchField.setItems(Stream.of("Bug", "Task", "Epic", "SubTask", "Question"));
    typeSearchField.addValueChangeListener(event -> System.out.println(" type value changed... " + event.getValue()));

    prioritySearchField.setPlaceholder("priority");
    prioritySearchField.setItems(Stream.of("Critical", "Major", "Minor", "Useless"));
    prioritySearchField.addValueChangeListener(event -> System.out.println(" priority field value changed... " + event.getValue()));

    statusSearchField.setPlaceholder("status");
    statusSearchField.setItems(Stream.of("On Hold", "Accepted", "In Progress", "Done"));
    statusSearchField.addValueChangeListener(event -> System.out.println(" status field value changed... " + event.getValue()));

    filterBar.setWidth("100%");

    searchResultGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
    searchResultGrid.setMultiSort(true);

    //TODO if role issue -> issue ID as link to view

    searchResultGrid.addColumn(SearchResultItem::getProjectID).setHeader("Project ID").setSortable(true).setResizable(true);
    searchResultGrid.addColumn(SearchResultItem::getIssueID).setHeader("Issue ID").setSortable(true).setResizable(true);
    searchResultGrid.addColumn(SearchResultItem::getSubject).setHeader("Subject").setSortable(true).setResizable(true);
    searchResultGrid.addColumn(SearchResultItem::getType).setHeader("Type").setSortable(true).setResizable(true);
    searchResultGrid.addColumn(SearchResultItem::getPriority).setHeader("Priority").setSortable(true).setResizable(true);
    searchResultGrid.addColumn(SearchResultItem::getStatus).setHeader("Status").setSortable(true).setResizable(true);
    searchResultGrid.addColumn(SearchResultItem::getAssignee).setHeader("Assignee").setSortable(true).setResizable(true);

    searchViewLayout.expand(searchResultGrid);

    assigneeSearchField.setPlaceholder("assignee");
    assigneeSearchField.setValueChangeMode(ValueChangeMode.ON_CHANGE);
    assigneeSearchField.addValueChangeListener(event -> System.out.println(" assignee field value changed... " + event.getValue()));

    statusSearchField.setPlaceholder("status");
    statusSearchField.setItems(Stream.of("On Hold", "Accepted", "In Progress", "Done"));

    statusSearchField.addValueChangeListener(event -> System.out.println(" status field value changed... " + event.getValue()));

    middle().add(searchViewLayout);
    middle().expand(searchViewLayout);
  }
}
