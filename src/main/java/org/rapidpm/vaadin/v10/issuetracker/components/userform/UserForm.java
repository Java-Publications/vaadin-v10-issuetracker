package org.rapidpm.vaadin.v10.issuetracker.components.userform;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.addons.framework.Registration;

import java.util.Set;

import static java.util.concurrent.ConcurrentHashMap.newKeySet;
import static org.rapidpm.vaadin.addons.framework.ComponentIDGenerator.*;

public class UserForm extends Composite<FormLayout> implements HasLogger {

  public static final String TF_FIRST_NAME_ID      = textfieldID().apply(UserForm.class, "first_name");
  public static final String TF_FIRST_NAME_CAPTION = caption().apply(TF_FIRST_NAME_ID);

  public static final String TF_LAST_NAME_ID      = textfieldID().apply(UserForm.class, "last_name");
  public static final String TF_LAST_NAME_CAPTION = caption().apply(TF_LAST_NAME_ID);

  public static final String TF_EMAIL_ID      = textfieldID().apply(UserForm.class, "email");
  public static final String TF_EMAIL_CAPTION = caption().apply(TF_EMAIL_ID);

  public static final String CB_STATUS_ID      = comboBoxID().apply(UserForm.class, "status");
  public static final String CB_STATUS_CAPTION = caption().apply(CB_STATUS_ID);

  public static final String DP_BIRTHDAY_ID      = datePickerID().apply(UserForm.class, "birthday");
  public static final String DP_BIRTHDAY_CAPTION = caption().apply(DP_BIRTHDAY_ID);

  public static final String BTN_SAVE_ID      = buttonID().apply(UserForm.class, "btn_save");
  public static final String BTN_SAVE_CAPTION = caption().apply(BTN_SAVE_ID);

  public static final String BTN_DELETE_ID      = buttonID().apply(UserForm.class, "btn_delete");
  public static final String BTN_DELETE_CAPTION = caption().apply(BTN_DELETE_ID);


  private final TextField firstName = new TextField() {{

    setId(TF_FIRST_NAME_ID);

  }};

  private final TextField lastName = new TextField() {{

    setId(TF_LAST_NAME_ID);
  }};

  private final TextField email = new TextField() {{

    setId(TF_EMAIL_ID);
  }};

  private final ComboBox<UserStatus> status = new ComboBox<>() {{

    setId(CB_STATUS_ID);
  }};

  private final DatePicker birthday = new DatePicker() {{

    setId(CB_STATUS_ID);
  }};

  private final Button save = new Button() {{

    setId(BTN_SAVE_ID);
  }};

  private final Button delete = new Button() {{

    setId(BTN_DELETE_ID);
  }};


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
