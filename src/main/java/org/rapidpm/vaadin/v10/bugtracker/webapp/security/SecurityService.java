package org.rapidpm.vaadin.v10.bugtracker.webapp.security;

import static java.util.Collections.emptySet;
import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.v10.bugtracker.model.User;
import org.rapidpm.vaadin.v10.bugtracker.model.UserRepository;
import org.rapidpm.vaadin.v10.bugtracker.model.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.visibility.manual.IlayVisibility;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.VaadinSession;

@VaadinSessionScoped
public class SecurityService implements HasLogger {
  public static final String ANONYMOUS = "anonymous";
  public static final String USER_ID_SESSION_KEY = "user_id";
@Inject
  private UserRepository userRepository;


  public Set<UserRole> activeUserHasRoles() {

    final VaadinSession vaadinSession = VaadinSession.getCurrent();
    final User sessionUser = vaadinSession.getAttribute(User.class);

    return (sessionUser != null && sessionUser.getRole() != null)
           ? Set.of(sessionUser.getRole())
           : emptySet();
  }

  public String activeUsername() {
    final VaadinSession vaadinSession = VaadinSession.getCurrent();
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
                  () -> success(userRepository.find(1L))) //TODO Impl
    )
        .ifFailed(failed -> logger().warning(failed));
  }


  public <T extends Component> T checkAgainstRoles(T component, Set<UserRole> roles){
    Objects.requireNonNull(roles);

    IlayVisibility.register(component ,
                            () -> activeUserHasRoles()
                                .stream()
                                .filter(roles::contains)
                                .count() >= 1);
    return component;
  }
}
