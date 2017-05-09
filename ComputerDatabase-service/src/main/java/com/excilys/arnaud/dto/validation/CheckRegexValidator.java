package com.excilys.arnaud.dto.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckRegexValidator 
  implements ConstraintValidator<CheckRegex, String> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CheckRegexValidator.class);
  
  private String regex;

  @Override
  public void initialize(CheckRegex constraintAnnotation) {
    regex = constraintAnnotation.value();
    
  }

  @Override
  public boolean isValid(String field, ConstraintValidatorContext constraintContext) {
    if(field == null || field.isEmpty()) {
      return true;
    }
    LOGGER.debug("Date is : "+field);
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(field);
    return m.matches();
  }
  
  

}
