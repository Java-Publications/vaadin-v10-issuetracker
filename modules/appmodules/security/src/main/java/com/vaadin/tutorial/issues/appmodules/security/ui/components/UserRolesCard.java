package com.vaadin.tutorial.issues.appmodules.security.ui.components;

import java.util.Objects;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.tutorial.issues.appmodules.security.model.user.User;

public class UserRolesCard extends Composite<Div> implements HasLogger {

  public static final String ID_PREFIX = UserRolesCard.class.getSimpleName().toLowerCase();
  private User user;

  public UserRolesCard(User user) {
    Objects.requireNonNull(user);
    getContent().setId(ID_PREFIX + "-" + user.getUserId());
    this.user = user;

    final VerticalLayout contentLayout = new VerticalLayout();
    contentLayout.getStyle()
                 .set("border" , "1px solid gray")
                 .set("padding" , "10px")
                 .set("boxSizing" , "border-box")
                 .set("width" , "100%");
    user
        .getRoles()
        .stream()
        .map(r -> getTranslation(r.getNameProperty()))
        .forEach(r -> contentLayout.add(new Label(r)));

    getContent().add(contentLayout);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (! (o instanceof UserRolesCard)) return false;
    UserRolesCard that = (UserRolesCard) o;
    return user.equals(that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user);
  }
}
