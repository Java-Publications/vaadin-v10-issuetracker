package com.vaadin.tutorial.issues.webapp.security;

import static com.vaadin.flow.server.VaadinSession.getCurrent;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;

import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.tutorial.issues.webapp.security.model.user.User;
import com.vaadin.tutorial.issues.webapp.security.model.user.UserService;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.webapp.security.visibility.manual.IlayVisibility;

@VaadinSessionScoped
public class SecurityService implements HasLogger {
  public static final String ANONYMOUS = "anonymous";

  @Inject private UserService userRepository;


  public Set<UserRole> activeUserHasRoles() {

    final VaadinSession vaadinSession = getCurrent();
    final User sessionUser = vaadinSession.getAttribute(User.class);

    return (sessionUser != null
            && sessionUser.getRoles() != null
            && ! sessionUser.getRoles().isEmpty())
           ? unmodifiableSet(sessionUser.getRoles())
           : emptySet();
  }

  public String activeUsername() {
    final VaadinSession vaadinSession = getCurrent();
    final User sessionUser = vaadinSession.getAttribute(User.class);

    return (sessionUser != null) ? sessionUser.getEmail() : ANONYMOUS;
  }

  public boolean isAnonymous() {
    return activeUsername().equals(ANONYMOUS);
  }

  public Result<User> checkCredentials(String login , String passwd) {
    return match(
        matchCase(() -> failure("no user found with this credentials.. user = " + login)) ,
        matchCase(() -> (login == null || login.isEmpty()) ,
                  () -> failure("no user found with this credentials.. user = " + login)) ,
        matchCase(() -> (passwd == null || passwd.isEmpty()) ,
                  () -> failure("no user found with this credentials.. user = " + login)) ,
        matchCase(() -> (userRepository.checkCredentials(login , passwd)) ,
                  () -> userRepository.loadFor(login , passwd))
    )
        .ifFailed(failed -> logger().warning(failed));
  }


  public <T extends Component> T checkAgainstRoles(T component , Set<UserRole> roles) {
    Objects.requireNonNull(roles);

    IlayVisibility.register(component ,
                            () -> activeUserHasRoles()
                                      .stream()
                                      .filter(roles::contains)
                                      .count() >= 1);
    return component;
  }
}
