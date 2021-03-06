package com.vaadin.tutorial.issues.webapp.security;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.tutorial.issues.webapp.security.model.user.User;

public class ValidationService {

  @Inject private ValidatorFactory validatorFactory;

  public void validateProperty(User user , String propertyName , HasValidation field) throws
                                                                                      javax.validation.ValidationException {
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<User>> violations = validator.validateProperty(user , propertyName);

    if (! violations.isEmpty()) {
      ConstraintViolation<User> violation = violations.iterator().next();
      field.setErrorMessage(violation.getMessage());
      field.setInvalid(true);
      throw new javax.validation.ValidationException();
    }
  }

}
