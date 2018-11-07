package org.rapidpm.vaadin.v10.bugtracker.webapp.security.visibility;

import java.lang.annotation.Annotation;

public interface VisibilityEvaluator<ANNOTATION extends Annotation> {

    default boolean evaluateVisibility() {
        return evaluateVisibility(null);
    }

    boolean evaluateVisibility(ANNOTATION annotation);
}
