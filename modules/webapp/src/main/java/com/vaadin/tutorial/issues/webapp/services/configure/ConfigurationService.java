package com.vaadin.tutorial.issues.webapp.services.configure;

import static java.lang.System.setProperty;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.tutorial.issues.webapp.VaadinI18NProvider;

public class ConfigurationService implements VaadinServiceInitListener , HasLogger {

  @Override
  public void serviceInit(ServiceInitEvent serviceInitEvent) {
    logger().info("started the config service right now " + ConfigurationService.class.getName());
    setProperty("vaadin.i18n.provider" , VaadinI18NProvider.class.getName());
  }

}
