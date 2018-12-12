package com.vaadin.tutorial.issues.webapp.ui.dialog;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ConfirmDialog extends Composite<Dialog> {

  public ConfirmDialog(String question ,
                       String yesCaption ,
                       String noCaption ,
                       ComponentEventListener<ClickEvent<Button>> confirmClickListener) {

    Text text = new Text(question);

    Button no = new Button(noCaption , e -> close());
    Button yes = new Button(yesCaption , e -> close());
    //TODO Vaadin 11/12 should be used -> set Theme Variant Method
    yes.getElement().setAttribute("theme" , "primary");
    yes.addClickListener(confirmClickListener);

    getContent().add(text , new HorizontalLayout(no , yes));
  }

  public void open() {
    getContent().open();
  }

  public void close() {
    getContent().close();
  }

}
