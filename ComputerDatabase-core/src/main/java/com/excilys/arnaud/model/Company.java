package com.excilys.arnaud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -2699461159618380772L;
  @Id
  private long   id;
  @Column(name="name")
  private String name;

  public Company(){
    
  }
  
  public Company(long id, String name) {
    this.id = id;
    this.name = name;
  }


  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return id + "\t" + name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Company other = (Company) obj;
    if (id != other.id) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

}