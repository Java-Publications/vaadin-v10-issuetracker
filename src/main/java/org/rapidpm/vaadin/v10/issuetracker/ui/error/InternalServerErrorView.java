package org.rapidpm.vaadin.v10.issuetracker.ui.error;

import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.ui.layout.MainLayout;
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

@ParentLayout(MainLayout.class)
@HtmlImport("/frontend/styles/shared-styles.html")
public class InternalServerErrorView extends Composite<VerticalLayout> implements HasErrorParameter<Exception>, HasLogger {


  @Override
  public int setErrorParameter(BeforeEnterEvent beforeEnterEvent , ErrorParameter<Exception> errorParameter) {
    LocalDateTime now = LocalDateTime.now();
    logger().warning("Error-" + now , errorParameter.getException());

    setPageTitel().accept(this , "com.example.webapp.error");


    H1 title = new H1(getTranslation("com.example.webapp.error"));
    title.addClassName("red");

    Div message = new Div();
    message.setText(getTranslation("com.example.webapp.errorMessage"));

    Span date = new Span(now.toString());
    date.getStyle().set("font-weight" , "bold");

    HorizontalLayout info =
        new HorizontalLayout(new Span(getTranslation("com.example.webapp.timeStamp")) , date);

    getContent().add(title , message , info);

    return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  }

}
