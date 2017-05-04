package com.excilys.arnaud.dto;



import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.excilys.arnaud.dto.validation.PasswordMatches;

@PasswordMatches
public class UserDto {
  @Override
  public String toString() {
    return "UserDto [name=" + name + ", password=" + password + ", matchingPassword=" + matchingPassword + "]";
  }
  

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  private String password;
  
  @NotNull
  @NotEmpty
  private String matchingPassword;
  
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getMatchingPassword() {
    return matchingPassword;
  }
  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }



}
