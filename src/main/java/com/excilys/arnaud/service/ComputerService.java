package com.excilys.arnaud.service;

import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.metier.Computer;
import com.excilys.arnaud.persistance.ComputerDAO;
import com.excilys.arnaud.persistance.DAOFactory;
import com.excilys.arnaud.service.mapper.ComputerMapper;

import java.util.Optional;

public enum ComputerService {
  COMPUTERSERVICE;
  private ComputerDAO computerDAO = DAOFactory.DAOFACTORY.getComputerDAO();

  /** Ask to DAO to add computerDto.
   * @param computerDto a computerDto to add
   * @return The id generated for the computer
   * @throws ServiceException if a DAO error occurred
   */
  public long add(ComputerDto computerDto) throws ServiceException {
    Computer computer = ComputerMapper.COMPUTERMAPPER.computerDtoToComputer(computerDto);
    checkDate(computer);
    return computerDAO.add(computer);
  }

  /** Ask to DAO to update computerDto.
   * @param computerDto a computerDto to add
   * @return true if computer is updated
   * @throws ServiceException if a DAO error occurred
   */
  public boolean update(ComputerDto computerDto) throws ServiceException {
    Computer computer = ComputerMapper.COMPUTERMAPPER.computerDtoToComputer(computerDto);
    checkDate(computer);
    return computerDAO.update(computer);
  }

  public boolean del(long id) {
    return computerDAO.del(id);
  }

  /** Look for the computer with id equals to the input.
   * @param id of the computer to find
   * @return an optional of the computer
   */
  public Optional<ComputerDto> findById(long id) {
    Optional<Computer> computer = computerDAO.findById(id);
    if (computer.isPresent()) {
      return Optional.of(
          ComputerMapper.COMPUTERMAPPER.computerToComputerDto(computer.get()));
    }
    return Optional.empty();
  }

  /** Look for a computer with name equals to the input.
   * @param name of the computer to find
   * @return an optional of the computer
   */
  public Optional<ComputerDto> findByName(String name) {
    Optional<Computer> computer = computerDAO.findByName(name);
    if (computer.isPresent()) {
      return Optional.of(
          ComputerMapper.COMPUTERMAPPER.computerToComputerDto(computer.get()));
    }
    return Optional.empty();
  }

  public ComputerPage getComputers() {
    return new ComputerPage();
  }
  
  public ComputerPage getComputers(String pattern) {
    return new ComputerPage(pattern);
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
    throw new ServiceException("Date of discontinuation "
        + computer.getDiscontinued() + " must be after date of introduction "
        + computer.getIntroduced());
  }

  public boolean[] dels(long[] longIds) {
    return computerDAO.dels(longIds);
    
  }

}
