package org.rapidpm.vaadin.v10.bugtracker.webapp.security.visibility;

import static java.util.Arrays.stream;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.VaadinService;

public class VisibilityUtil {

  private static final Map<
      Class<? extends Component>,
      AnnotationVisibilityEvaluatorTuple<? extends Annotation>> cache = new ConcurrentHashMap<>();

  public static boolean hasVisibilityAnnotation(Class<?> clazz) {
    Objects.requireNonNull(clazz);

    final int visibilityAnnotationsCount = (int) stream(clazz.getAnnotations())
        .map(Annotation::annotationType)
        .filter(at -> at.isAnnotationPresent(VisibilityAnnotation.class))
        .count();

    switch (visibilityAnnotationsCount) {
      case 0:
        return false;
      case 1: {
        if (! Component.class.isAssignableFrom(clazz)) {
          throw new IllegalStateException(clazz + " has visibilityAnnotation but is not a component");
        }
        return true;
      }
      default:
        throw new IllegalStateException("more than one visibilityAnnotation found at " + clazz);
    }
  }

  @SuppressWarnings("unchecked")
  public static <ANNOTATION extends Annotation> void evaluateVisibility(Component component) {
    component.setVisible(VisibilityUtil.<ANNOTATION>evaluateVisibilityOnClass(component.getClass()));
  }

  public static <ANNOTATION extends Annotation> boolean evaluateVisibilityOnClass(Class<? extends Component> componentClass) {
    Objects.requireNonNull(componentClass);

    AnnotationVisibilityEvaluatorTuple<ANNOTATION> tuple = (AnnotationVisibilityEvaluatorTuple<ANNOTATION>) cache.computeIfAbsent(
        componentClass ,
        c -> stream(c.getAnnotations())
            .filter(a -> a.annotationType().isAnnotationPresent(VisibilityAnnotation.class))
            .findFirst()
            .map(a -> new AnnotationVisibilityEvaluatorTuple(a , a.annotationType().getAnnotation(VisibilityAnnotation.class).value()))
            .orElseThrow(IllegalStateException::new)
    );
    return VaadinService
        .getCurrent()
        .getInstantiator()
        .getOrCreate(tuple.getVisibilityEvaluatorClass())
        .evaluateVisibility(tuple.getAnnotation());
  }


  private static class AnnotationVisibilityEvaluatorTuple<ANNOTATION extends Annotation> {
    private final ANNOTATION annotation;
    private final Class<? extends VisibilityEvaluator<ANNOTATION>> visibilityEvaluatorClass;

    AnnotationVisibilityEvaluatorTuple(ANNOTATION annotation , Class<? extends VisibilityEvaluator<ANNOTATION>> visibilityEvaluatorClass) {
      this.annotation = annotation;
      this.visibilityEvaluatorClass = visibilityEvaluatorClass;
    }

    ANNOTATION getAnnotation() {
      return annotation;
    }

    Class<? extends VisibilityEvaluator<ANNOTATION>> getVisibilityEvaluatorClass() {
      return visibilityEvaluatorClass;
    }
  }
}
