package com.vaadin.tutorial.issues.webapp.ui.error;

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
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.issues.webapp.services.i18npagetitle.I18NPageTitle;
import com.vaadin.tutorial.issues.webapp.ui.layout.MainLayout;

@ParentLayout(MainLayout.class)
@HtmlImport("/frontend/styles/shared-styles.html")
@I18NPageTitle(messageKey = "com.vaadin.tutorial.webapp.pageNotFound")
public class RouteNotFoundErrorView
    extends Composite<VerticalLayout>
    implements HasErrorParameter<NotFoundException> {


  @Override
  public int setErrorParameter(BeforeEnterEvent event , ErrorParameter<NotFoundException> parameter) {

    H1 title = new H1(getTranslation("com.vaadin.tutorial.webapp.pageNotFound"));

    Div message = new Div();
    message.setText(getTranslation("com.vaadin.tutorial.webapp.pageNotFoundMessage"));

    getContent().add(title , message);
    getContent().setSizeFull();
    getContent().getElement().setAttribute("theme" , Lumo.DARK);
    getContent().setPadding(true);

    return HttpServletResponse.SC_NOT_FOUND;
  }
}
