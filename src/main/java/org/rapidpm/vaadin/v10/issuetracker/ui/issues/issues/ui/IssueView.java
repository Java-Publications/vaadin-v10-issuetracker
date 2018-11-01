package org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.ui;

import static org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.ui.IssueView.ROUTE;
import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import java.util.Optional;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.Issue;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.IssueService;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.Status;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = ROUTE, layout = MainLayout.class)
public class IssueView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

    public static final String ROUTE = "issue";

    @Inject private IssueService issueService;

    public IssueView() {
        setPageTitel().accept(this , "com.example.issues.issue");
    }

    @Override
    public void setParameter(BeforeEvent event, Long issueId) {
        Optional<Issue> issue = issueService.findById(issueId);
        if (!issue.isPresent()) {
            UI.getCurrent().navigate(IssuesView.class);
            UI.getCurrent().getPage().executeJavaScript("location.reload()");
        } else {
            showIssue(issue.get());
        }
    }

    private void showIssue(Issue issue) {
        Span viewTitle = new Span("#" + issue.getId() + " - " + issue.getTitle());
        viewTitle.addClassName("view-title");

        Span status = new Span(
                getTranslation("com.example.issues.status") + ": " + getTranslation(issue.getStatus().getNameProperty()));
        status.addClassName("issue-view-status");
        if (Status.OPEN.equals(issue.getStatus())) {
            status.addClassName("green");
        } else {
            status.addClassName("red");
        }

        Span owner = new Span(getTranslation("com.example.issues.owner") + ": " +
                              (issue.getOwner() != null ? issue.getOwner().getName() : "?"));
        owner.addClassName("issue-view-owner");
        if (issue.getOwner() != null) {
            owner.addClassName("blue");
        } else {
            owner.addClassName("red");
        }

        Span date = new Span(getTranslation("com.example.issues.date") + ": " + issue.getDate());
        date.addClassName("issue-view-date");

        HorizontalLayout infoLayout = new HorizontalLayout(status, owner, date);
        infoLayout.setSpacing(true);

        Span description = new Span(issue.getDescription());

        getContent().removeAll();
        getContent().add(viewTitle, infoLayout, description);
        getContent().setSizeFull();
    }

}
