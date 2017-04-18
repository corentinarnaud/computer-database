package com.excilys.arnaud.service;

import com.excilys.arnaud.mapper.ComputerMapper;
import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.metier.Computer;
import com.excilys.arnaud.persistance.ComputerDAO;
import com.excilys.arnaud.persistance.DAOFactory;

import java.util.Optional;

public enum ComputerService {
  COMPUTERSERVICE;
  private ComputerDAO computerDAO = DAOFactory.DAOFACTORY.getComputerDAO();
  private int numberComputer;
  
  private ComputerService() {
    numberComputer = DAOFactory.DAOFACTORY.getComputerDAO().getNumberOfComputer();
  }

  /** Ask to DAO to add computerDto.
   * @param computerDto a computerDto to add
   * @return The id generated for the computer
   * @throws ServiceException if a DAO error occurred
   */
  public long add(ComputerDto computerDto) throws ServiceException {
    Computer computer = ComputerMapper.computerDtoToComputer(computerDto);
    Validator.checkDate(computer);
    Validator.checkName(computer.getName());
    long newId = computerDAO.add(computer);
    if (newId > 0) {
      synchronized (this) {
        numberComputer++;
      }
    }
    return newId;
  }

  /** Ask to DAO to update computerDto.
   * @param computerDto a computerDto to add
   * @return true if computer is updated
   * @throws ServiceException if a DAO error occurred
   */
  public boolean update(ComputerDto computerDto) throws ServiceException {
    Computer computer = ComputerMapper.computerDtoToComputer(computerDto);
    Validator.checkDate(computer);
    Validator.checkName(computer.getName());
    return computerDAO.update(computer);
  }

  public boolean delComputer(long id) {
    if (computerDAO.del(id)) {
      synchronized (this) {
        numberComputer--;
      }
      return true;
    }
    return false;
  }

  /** Look for the computer with id equals to the input.
   * @param id of the computer to find
   * @return an optional of the computer
   */
  public Optional<ComputerDto> findById(long id) {
    Optional<Computer> computer = computerDAO.findById(id);
    if (computer.isPresent()) {
      return Optional.of(
          ComputerMapper.computerToComputerDto(computer.get()));
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
          ComputerMapper.computerToComputerDto(computer.get()));
    }
    return Optional.empty();
  }

  public ComputerPage getComputers() {
    return new ComputerPage();
  }
  
  public ComputerPage getComputers(String pattern) {
    return new ComputerPage(pattern);
  }


  public boolean[] delComputers(long[] longIds) {
    if(longIds.length==1){
      return new boolean[]{ delComputer(longIds[0])};
    }
    boolean[] dels = computerDAO.dels(longIds);
    int nbDels = 0;
    for (boolean id : dels) {
      if (id == true) {
        nbDels++;
      }
    }
    synchronized (this) {
      numberComputer -= nbDels;
    }
    return dels;
    
  }
  
  public int getNumberComputer() {
    return numberComputer;
  }
  

}
