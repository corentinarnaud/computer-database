package com.excilys.arnaud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.arnaud.model.metier.Computer;

public class ServiceUtils {
  private static Logger logger = LoggerFactory.getLogger(ServiceUtils.class);
  
  /** Check if name contains forbidden character.
   * @param name a String
   * @throws ServiceException if name contains [<>&${}()[]]
   */
  public static void checkName(String name) throws ServiceException {
    if (name.matches("(.*)[<>&\\$\\{}\\()\\[\\]](.*)")) {
      logger.debug("Name shall not contain [<>&${}()[]]");
      throw new ServiceException("Name shall not contain [<>&${}()[]]");
    }
  }
  
  /** Check if introduced Date is before discontinued Date.
   * @param computer The computer to test
   * @throws ServiceException if introduced date is after discontinued date
   */
  public static void checkDate(Computer computer) throws ServiceException {
    if (computer.getIntroduced() == null || computer.getDiscontinued() == null
        || computer.getIntroduced().isBefore(computer.getDiscontinued())) {
      return;
    }
    logger.debug("Date of discontinuation "
        + computer.getDiscontinued() + " must be after date of introduction "
        + computer.getIntroduced());
    throw new ServiceException("Date of discontinuation "
        + computer.getDiscontinued() + " must be after date of introduction "
        + computer.getIntroduced());
  }

}
