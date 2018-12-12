package com.vaadin.tutorial.issues.webapp.ui.error;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@ParentLayout(MainLayout.class)
@HtmlImport("/frontend/styles/shared-styles.html")
@I18NPageTitle(messageKey = "com.vaadin.tutorial.webapp.error")
public class InternalServerErrorView
    extends Composite<VerticalLayout>
    implements HasErrorParameter<Exception>, HasLogger {


  @Override
  public int setErrorParameter(BeforeEnterEvent beforeEnterEvent , ErrorParameter<Exception> errorParameter) {
    LocalDateTime now = LocalDateTime.now();
    logger().warning("Error-" + now , errorParameter.getException());

    H1 title = new H1(getTranslation("com.vaadin.tutorial.webapp.error"));
    title.addClassName("red");

    Div message = new Div();
    message.setText(getTranslation("com.vaadin.tutorial.webapp.errorMessage"));

    Span date = new Span(now.toString());
    date.getStyle().set("font-weight" , "bold");

    HorizontalLayout info =
        new HorizontalLayout(new Span(getTranslation("com.vaadin.tutorial.webapp.timeStamp")) , date);

    getContent().add(title , message , info);

    return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  }

}
