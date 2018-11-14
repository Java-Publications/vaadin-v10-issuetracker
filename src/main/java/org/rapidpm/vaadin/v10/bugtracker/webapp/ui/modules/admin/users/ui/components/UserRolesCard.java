package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users.ui.components;

import static java.util.stream.Collectors.joining;

import java.util.Objects;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class UserRolesCard extends Composite<Div> implements HasLogger {

  public static final String ID_PREFIX = UserRolesCard.class.getSimpleName().toLowerCase();
  private User user;

  public UserRolesCard(User user) {
    Objects.requireNonNull(user);
    getContent().setId(ID_PREFIX + "-" + user.getUserId());
    this.user = user;

    final VerticalLayout contentLayout = new VerticalLayout();
    contentLayout.getStyle()
                 .set("border", "1px solid gray")
                 .set("padding", "10px")
                 .set("boxSizing", "border-box")
                 .set("width", "100%");
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
