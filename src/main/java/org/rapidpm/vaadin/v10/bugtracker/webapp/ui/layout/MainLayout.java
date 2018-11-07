package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout;

import static com.vaadin.flow.server.VaadinService.getCurrentRequest;

import java.util.Locale;

import javax.inject.Inject;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.SecurityService;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.menu.MainMenu;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.layout.menu.UIConfiguration;
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

@UIScoped
@HtmlImport("/frontend/styles/shared-styles.html")
@HtmlImport("/frontend/styles/main-menu-styles.html")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends Composite<VerticalLayout> implements RouterLayout, HasLogger {


  @Inject private UIConfiguration uiConfiguration;
  @Inject private SecurityService securityService;

  private VerticalLayout contentLayout = new VerticalLayout();


  public MainLayout() {
    Locale locale = new Locale(getCurrentRequest().getLocale().getLanguage());
    UI.getCurrent().setLocale(locale);

    Anchor signOut = new Anchor("/logout" , getTranslation("com.example.webapp.signOut"));
    signOut.addClassName("mainHeader-signout");

    MainHeader mainHeader = new MainHeader(getTranslation("com.example.appName"));

    if (! uiConfiguration.getHeaderComponentSuppliers().isEmpty()) {
      uiConfiguration.getHeaderComponentSuppliers().stream().map(SerializableSupplier::get).forEach(mainHeader::add);
    }

    if (securityService.isAnonymous()) {
      logger().info("Anonymous User need no #signOut# button" );
    } else {
      mainHeader.add(signOut);
    }

    MainMenu mainMenu = new MainMenu();
    uiConfiguration.getMenuOptions().forEach(mainMenu::addOption);

    contentLayout.setMargin(false);
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
