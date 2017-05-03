package com.excilys.arnaud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Column(name = "name", updatable = false, nullable = false, unique=true)
  private String name;
  @Column(name = "password", nullable = false)
  private String password;
  
  public User(){}
  
  public User(String name, String password){
    name = this.name;
    password = this.password;
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

  
  
}
