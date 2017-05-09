package com.excilys.arnaud.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.excilys.arnaud.dto.UserDto;

public class PasswordMatchesValidator 
implements ConstraintValidator<PasswordMatches, UserDto> { 
   
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {       
  }
  @Override
  public boolean isValid(UserDto user, ConstraintValidatorContext context){   
      return user.getPassword().equals(user.getMatchingPassword());    
  }     
}
