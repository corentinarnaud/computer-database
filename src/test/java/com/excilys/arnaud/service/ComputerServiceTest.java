package com.excilys.arnaud.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.arnaud.model.metier.Computer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;



public class ComputerServiceTest {

  @Test
  public void checkDateTest() throws ServiceException {
    Computer computer = new Computer("toto", null, 
        LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()),
        LocalDateTime.from(LocalDate.parse("1994-12-10").atStartOfDay()));
    assertTrue(ComputerService.checkDate(computer) == true);

    computer = new Computer("toto", null, 
        LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()), null);
    assertTrue(ComputerService.checkDate(computer) == true);

    computer = new Computer("toto", null, null, 
        LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()));
    assertTrue(ComputerService.checkDate(computer) == true);

    computer = new Computer("toto", null, null, null);
    assertTrue(ComputerService.checkDate(computer) == true);

    try {
      computer = new Computer("toto", null, 
          LocalDateTime.from(LocalDate.parse("1993-12-10").atStartOfDay()),
          LocalDateTime.from(LocalDate.parse("1992-12-10").atStartOfDay()));
      ComputerService.checkDate(computer);
      fail("Eception Was Not Thrown");
    } catch (ServiceException e) {
      assert (e.getMessage().contains("must be after date of introduction"));
    }

  }
}
