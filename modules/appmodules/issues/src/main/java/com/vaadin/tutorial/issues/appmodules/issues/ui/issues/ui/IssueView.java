package com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui;


import static com.vaadin.tutorial.issues.appmodules.issues.ui.issues.ui.IssueView.ROUTE;

import java.util.Optional;

import javax.inject.Inject;

import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.issues.appmodules.issues.model.issue.Issue;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatus;
import com.vaadin.tutorial.issues.appmodules.issues.ui.issues.IssueService;
import com.vaadin.tutorial.issues.webapp.security.navigation.VisibleTo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@I18NPageTitle(messageKey = "com.vaadin.tutorial.issues.issue")
@Route(value = ROUTE, layout = MainLayout.class)
@VisibleTo(USER)
@UIScoped
public class IssueView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {

    public static final String ROUTE = "issue";

    @Inject private IssueService issueService;

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
        Span viewTitle = new Span("#" + issue.getIssueId() + " - " + issue.getTitle());
        viewTitle.addClassName("view-title");

        Span status = new Span(
                getTranslation("com.vaadin.tutorial.issues.status") + ": " + getTranslation(issue.getStatus().getNameProperty()));
        status.addClassName("issue-view-status");
        if (IssueStatus.OPEN.equals(issue.getStatus())) {
            status.addClassName("green");
        } else {
            status.addClassName("red");
        }

        Span owner = new Span(getTranslation("com.vaadin.tutorial.issues.owner") + ": " +
                              (issue.getOwner() != null ? issue.getOwner().getName() : "?"));
        owner.addClassName("issue-view-owner");
        if (issue.getOwner() != null) {
            owner.addClassName("blue");
        } else {
            owner.addClassName("red");
        }

        Span date = new Span(getTranslation("com.vaadin.tutorial.issues.date") + ": " + issue.getDate());
        date.addClassName("issue-view-date");

        HorizontalLayout infoLayout = new HorizontalLayout(status, owner, date);
        infoLayout.setSpacing(true);

        Span description = new Span(issue.getDescription());

        getContent().removeAll();
        getContent().add(viewTitle, infoLayout, description);
        getContent().setSizeFull();
    }

}
