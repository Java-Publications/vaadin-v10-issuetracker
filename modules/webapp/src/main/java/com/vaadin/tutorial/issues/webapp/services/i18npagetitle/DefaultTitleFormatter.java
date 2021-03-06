package com.vaadin.tutorial.issues.webapp.services.i18npagetitle;

import java.util.Locale;

import com.vaadin.flow.i18n.I18NProvider;

public class DefaultTitleFormatter implements TitleFormatter {

  @Override
  public String applyWithException(I18NProvider i18NProvider , Locale locale , String key) throws Exception {
    return i18NProvider.getTranslation(key, locale);
  }
}
