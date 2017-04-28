package com.excilys.arnaud.servlets.tags;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FormatDate extends SimpleTagSupport {
  private String date = "";
  private DateTimeFormatter before;
  private DateTimeFormatter after;
  
  public FormatDate(){
    
  }
  
  public void setDate(String date) {
    this.date = date;
  }
  
  public void setBefore(String format) {
    before = DateTimeFormatter.ofPattern(format);
  }
  
  public void setAfter(String format) {
    after = DateTimeFormatter.ofPattern(format);
  }
  

  @Override
  public void doTag() throws IOException {
    String newDate = "";
    if (date.compareTo("") != 0) {
      System.out.println(date);
      newDate = LocalDateTime.from(LocalDate.parse(
        date, before).atStartOfDay()).format(after);
      System.out.println(newDate);
    }
    getJspContext().getOut().write(newDate);
  }
}
