package com.excilys.arnaud.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;

import com.excilys.arnaud.model.work.Computer;



public class ValidatorTest {

  @Test
  public void checkDateTest() throws ServiceException {
    Computer computer = new Computer("toto", null, 
        LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()),
        LocalDateTime.from(LocalDate.parse("1994-12-10").atStartOfDay()));
    try {
      Validator.checkDate(computer);
      assertTrue(true);
    } catch (ServiceException e) {
      fail("Eception Was Thrown" + e.getMessage());
    }
    computer = new Computer("toto", null, 
        LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()), null);
    try {
      Validator.checkDate(computer);
      assertTrue(true);
    } catch (ServiceException e) {
      fail("Eception Was Thrown" + e.getMessage());
    }
    
    computer = new Computer("toto", null, null, 
        LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()));
    try {
      Validator.checkDate(computer);
      assertTrue(true);
    } catch (ServiceException e) {
      fail("Eception Was Thrown" + e.getMessage());
    }

    computer = new Computer("toto", null, null, null);
    try {
      Validator.checkDate(computer);
      assertTrue(true);
    } catch (ServiceException e) {
      fail("Eception Was Thrown" + e.getMessage());
    }
    
    
    try {
      computer = new Computer("toto", null, 
          LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()),
          LocalDateTime.from(LocalDate.parse("1992-12-10").atStartOfDay()));
      Validator.checkDate(computer);
      fail("Eception Was Not Thrown");
    } catch (ServiceException e) {
      assertTrue(e.getMessage().contains("must be after date of introduction"));
    }

  }
}
