package org.rapidpm.vaadin.v10.issuetracker.components.userform;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.Set;

import static java.util.concurrent.ConcurrentHashMap.newKeySet;

public class UserForm extends Composite<FormLayout> {

  private final TextField firstName = new TextField();
  private final TextField lastName  = new TextField();
  private final TextField email     = new TextField();

  private final ComboBox<UserStatus> status = new ComboBox<>();

  private final DatePicker birthday = new DatePicker();

  private final Button save   = new Button();
  private final Button delete = new Button();


  private final Binder<User>     beanBinder      = new Binder<>(User.class);
  private final Set<UpdateEvent> saveListeners   = newKeySet();
  private final Set<UpdateEvent> deleteListeners = newKeySet();
  private       User             user;

  public void setCustomer(User user) {
    this.user = user;
    beanBinder.setBean(user);

    // Show delete button for only customers already in the database
    //delete.setVisible(user.isPersisted());
    setVisible(true);
//    firstName.selectAll();
  }

  private void delete() {
    setVisible(false);
    deleteListeners.forEach(listener -> listener.update(user));
  }

  private void save() {
    setVisible(false);
    saveListeners.forEach(listener -> listener.update(user));
  }

  public Registration registerSaveListener(UpdateEvent updateEvent) {
    saveListeners.add(updateEvent);
    return () -> saveListeners.remove(updateEvent);
  }

  public Registration registerDeleteListener(UpdateEvent updateEvent) {
    deleteListeners.add(updateEvent);
    return () -> deleteListeners.remove(updateEvent);
  }

  public interface UpdateEvent {
    void update(User user);
  }
}
