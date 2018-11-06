package org.rapidpm.vaadin.v10.bugtracker.webapp.services.i18npagetitle;

import java.util.Locale;

import org.rapidpm.frp.functions.CheckedTriFunction;
import com.vaadin.flow.i18n.I18NProvider;

public interface TitleFormatter extends CheckedTriFunction<I18NProvider, Locale, String, String> {
}
