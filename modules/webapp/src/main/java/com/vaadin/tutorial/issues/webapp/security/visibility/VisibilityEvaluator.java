package com.vaadin.tutorial.issues.webapp.security.visibility;

import java.lang.annotation.Annotation;

public interface VisibilityEvaluator<ANNOTATION extends Annotation> {

    default boolean evaluateVisibility() {
        return evaluateVisibility(null);
    }

    boolean evaluateVisibility(ANNOTATION annotation);
}
