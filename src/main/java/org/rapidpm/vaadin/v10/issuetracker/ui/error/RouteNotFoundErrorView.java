package org.rapidpm.vaadin.v10.issuetracker.ui.error;

import static org.rapidpm.vaadin.v10.issuetracker.ui.layout.LayoutFunctions.setPageTitel;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.theme.lumo.Lumo;

@HtmlImport("/frontend/styles/shared-styles.html")
public class RouteNotFoundErrorView extends Composite<VerticalLayout> implements HasErrorParameter<NotFoundException> {


  @Override
  public int setErrorParameter(BeforeEnterEvent event , ErrorParameter<NotFoundException> parameter) {

    setPageTitel().accept(this , "com.example.webapp.pageNotFound");

    H1 title = new H1(getTranslation("com.example.webapp.pageNotFound"));

    Div message = new Div();
    message.setText(getTranslation("com.example.webapp.pageNotFoundMessage"));

    getContent().add(title , message);
    getContent().setSizeFull();
    getContent().getElement().setAttribute("theme" , Lumo.DARK);
    getContent().setPadding(true);

    return HttpServletResponse.SC_NOT_FOUND;
  }
}
