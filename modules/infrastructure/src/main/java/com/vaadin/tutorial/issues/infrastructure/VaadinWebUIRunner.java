package com.vaadin.tutorial.issues.infrastructure;

import static java.lang.System.setProperty;

import org.apache.meecrowave.Meecrowave;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.VaadinI18NProvider;

public class VaadinWebUIRunner {

  public static void main(String[] args) throws Exception {

    setProperty("vaadin.i18n.provider" , VaadinI18NProvider.class.getName());

    new Meecrowave(new Meecrowave.Builder() {
      {
//        randomHttpPort();
        setHttpPort(8080);
        setTomcatScanning(true);
        setTomcatAutoSetup(true);
        setHttp2(true);

      }
    })
        .bake()
        .await();
  }
}
