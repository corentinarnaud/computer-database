package com.excilys.arnaud.dto.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckRegexValidator.class)
@Documented
public @interface CheckRegex {
  String message() default "Wrong format";
  Class<?>[] groups() default {}; 
  Class<? extends Payload>[] payload() default {};
  
  String value();
}
