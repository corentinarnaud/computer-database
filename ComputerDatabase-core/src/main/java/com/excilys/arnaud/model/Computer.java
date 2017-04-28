package com.excilys.arnaud.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="computer")
public class Computer implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4034527847960439679L;
  private static final String FORMAT = "MM/dd/yyyy";
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private long          id = -1;
  @Column(name="name", nullable = false)
  private String        name;
  @ManyToOne(fetch=FetchType.EAGER, targetEntity=Company.class)
  @JoinColumn(name="company_id", referencedColumnName = "id")
  private Company       company;
  @Column
  private LocalDateTime introduced;
  @Column
  private LocalDateTime discontinued;

  public Computer(long id, String name, Company company, 
      LocalDateTime introduced, LocalDateTime discontinued) {
    this.id = id;
    this.name = name;

    this.company = company;

    this.introduced = introduced;
    this.discontinued = discontinued;
  }

  public Computer(String name, Company company, 
      LocalDateTime introduced, LocalDateTime discontinued) {
    this(-1, name, company, introduced, discontinued);
  }

  public Computer(){
    
  }
  
  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
    String string = id + "\t" + name + "\t\t\t";
    if (company != null) {
      string += company.getName();
    } else {
      string += "???";
    }
    string += "\t\t";
    if (introduced != null) {
      string += introduced.format(formatter) + "\t";
      if (discontinued != null) {
        string += discontinued.format(formatter);
      } else {
        string += "???";
      }
    } else {
      string += "???\t\t???";
    }

    return string;
  }

  public String getIntroducedFormated(){
	 if(introduced!=null){
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		 return introduced.format(formatter);
	  } else {
		  return "";
	  }
  }
  
  public String getDiscontinuedFormated(){
    if(discontinued!=null){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
      return discontinued.format(formatter);
    } else {
      return "";
    }
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDateTime introduced) {
    this.introduced = introduced;
  }

  public LocalDateTime getDiscontinued() {
    return discontinued;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    // On peut changer l'id que si il n'as pas été initialiser
    if (this.id == -1)
      this.id = id;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public void setDiscontinued(LocalDateTime discontinued) {
    this.discontinued = discontinued;
  }

  public Company getCompany() {
    return company;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null)
        return false;
    } else if (!company.equals(other.company))
      return false;
    if (discontinued == null) {
      if (other.discontinued != null)
        return false;
    } else if (!discontinued.equals(other.discontinued))
      return false;
    if (id != other.id)
      return false;
    if (introduced == null) {
      if (other.introduced != null)
        return false;
    } else if (!introduced.equals(other.introduced))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
