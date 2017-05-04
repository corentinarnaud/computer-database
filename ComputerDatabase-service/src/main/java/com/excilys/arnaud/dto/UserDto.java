package com.excilys.arnaud.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.excilys.arnaud.dto.validation.PasswordMatches;

@PasswordMatches
public class UserDto {
  @NotNull
  @Size(min=1)
  private String name;

  @NotNull
  @Size(min=1)
  private String password;
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
