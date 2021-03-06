package com.vaadin.tutorial.issues.webapp.ui.layout;

import static com.vaadin.flow.server.VaadinService.getCurrentRequest;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.cdi.annotation.UIScoped;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.issues.webapp.security.SecurityService;
import com.vaadin.tutorial.issues.webapp.ui.layout.menu.MainMenu;
import com.vaadin.tutorial.issues.webapp.ui.layout.menu.UIConfiguration;

@UIScoped
@HtmlImport("/frontend/styles/shared-styles.html")
@HtmlImport("/frontend/styles/main-menu-styles.html")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends Composite<VerticalLayout> implements RouterLayout, HasLogger {


  @Inject private UIConfiguration uiConfiguration;
  @Inject private SecurityService securityService;

  private VerticalLayout contentLayout = new VerticalLayout();
  private MainHeader mainHeader = new MainHeader("com.vaadin.tutorial.appName");
  private MainMenu mainMenu = new MainMenu();


  @PostConstruct
  private void postConstruct() {

    if (! uiConfiguration.getHeaderComponentSuppliers().isEmpty()) {
      uiConfiguration.getHeaderComponentSuppliers()
                     .stream()
                     .map(SerializableSupplier::get)
                     .peek(e -> logger().info(e.getClass().getSimpleName()))
                     .forEach(mainHeader::add);
    }

    if (securityService.isAnonymous()) {
      logger().info("Anonymous User need no #signOut# button");
    } else {
      //TODO const -> hold in sync with all Route Const
      Anchor signOut = new Anchor("/logout" , getTranslation("com.vaadin.tutorial.webapp.signOut"));
      signOut.addClassName("mainHeader-signout");
      mainHeader.add(signOut);
    }

    uiConfiguration.getMenuOptions().forEach(mainMenu::addOption);

  }


  public MainLayout() {
    Locale locale = new Locale(getCurrentRequest().getLocale().getLanguage());
    UI.getCurrent().setLocale(locale);

    contentLayout.setPadding(false);
    contentLayout.setSpacing(false);

    HorizontalLayout horizontalLayout = new HorizontalLayout(mainMenu , contentLayout);
    horizontalLayout.setSizeFull();
    horizontalLayout.setMargin(false);
    horizontalLayout.setPadding(false);
    horizontalLayout.setSpacing(false);

    getContent().add(mainHeader , horizontalLayout);
    getContent().setAlignSelf(FlexComponent.Alignment.CENTER , horizontalLayout);
    getContent().setSizeFull();
    getContent().setPadding(false);
    getContent().setSpacing(false);
  }

  @Override
  public void showRouterLayoutContent(HasElement content) {
    contentLayout.removeAll();
    contentLayout.getElement().appendChild(content.getElement());
  }

}
