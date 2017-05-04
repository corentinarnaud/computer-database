package com.excilys.arnaud.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @Column(name = "name", updatable = false, nullable = false, unique=true)
  private String name;
  @Column(name = "password", nullable = false)
  private String password;
  
  //private List<String> roles;
  
  public User(){}
  
  public User(String name, String password, List<String> roles){
    this.name = name;
    this.password = password;
    //this.roles = roles;
  }

  public List<String> getRoles() {
    return Arrays.asList("USER");
  }

  public void setRoles(List<String> roles) {
    //this.roles = roles;
  }

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

  @Override
  public String toString() {
    return "User [name=" + name + "]";
  }

  
  
}
