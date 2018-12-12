package com.vaadin.tutorial.issues.webapp.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MainHeader extends Composite<HorizontalLayout> {

    private HorizontalLayout contentLayout = new HorizontalLayout();

    public MainHeader(String titleMsgKey) {
        Image logo = new Image("/frontend/images/app-logo.png", "App logo");
        logo.addClassName("header-logo");
        Span appName = new Span(getTranslation(titleMsgKey));
        appName.addClassName("header-app-name");

        contentLayout.setPadding(true);
        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        contentLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        getContent().add(logo, appName, contentLayout);
        getContent().setFlexGrow(1, contentLayout);
        getContent().addClassName("header");
        getContent().setWidth("100%");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, appName);
    }

    public void add(Component component) {
        contentLayout.add(component);
    }

}
