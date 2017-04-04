package com.excilys.arnaud.service;

import static org.junit.Assert.assertTrue;

import com.excilys.arnaud.model.dto.ComputerDtoList;
import com.excilys.arnaud.model.metier.ComputerList;
import com.excilys.arnaud.persistance.mysql.ComputerDAOMySQL;
import com.excilys.arnaud.service.mapper.ComputerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;






@RunWith(PowerMockRunner.class)
@PrepareForTest({ComputerDAOMySQL.class,
                 ComputerList.class, ComputerMapper.class})
public class ComputerPageTest {
  private ComputerDAOMySQL mockDAO;
  private ComputerMapper mockMapper;
  
  

  
  @Test
  public void constructorPage() {
    String pattern = "";
    int begin = 0;
    int nbComputer = 10;
    int orderBy = 0;
    
    ComputerDAOMySQL mockDAO = PowerMockito.mock(ComputerDAOMySQL.class);
    Whitebox.setInternalState(ComputerDAOMySQL.class, ComputerDAOMySQL.COMPUTERDAO, mockDAO);
    ComputerMapper mockMapper = PowerMockito.mock(ComputerMapper.class);
    Whitebox.setInternalState(ComputerMapper.class, ComputerMapper.COMPUTERMAPPER, mockMapper);
    
    ComputerList computerList = new ComputerList();
    ComputerDtoList computerDtoList = new ComputerDtoList();
    
    Mockito.when(mockDAO.getNComputers(pattern, begin, nbComputer, orderBy)).thenReturn(computerList);
    Mockito.when(mockDAO.getNumberOfComputer(pattern)).thenReturn(10);
    Mockito.when(mockMapper.computerListToComputerDtoList(computerList))
      .thenReturn(computerDtoList);
    
    ComputerPage page = new ComputerPage();
    assertTrue(page.getNbElement() == 10);
    assertTrue(page.getCurrentPage() == 0);
    assertTrue(page.getNbPage() == 1);

  }
  
  
  @Test
  public void testGetPage() {
    String pattern = "";
    int begin = 0;
    int nbComputer = 10;
    int orderBy = 0;
    
    ComputerDAOMySQL mockDAO = PowerMockito.mock(ComputerDAOMySQL.class);
    Whitebox.setInternalState(ComputerDAOMySQL.class, ComputerDAOMySQL.COMPUTERDAO, mockDAO);
    ComputerMapper mockMapper = PowerMockito.mock(ComputerMapper.class);
    Whitebox.setInternalState(ComputerMapper.class, ComputerMapper.COMPUTERMAPPER, mockMapper);
    
    
    ComputerList computerList = new ComputerList();
    ComputerDtoList computerDtoList = new ComputerDtoList();
    
    Mockito.when(mockDAO.getNComputers(pattern, begin, nbComputer, orderBy)).thenReturn(computerList);
    Mockito.when(mockDAO.getNumberOfComputer(pattern)).thenReturn(10);
    Mockito.when(mockMapper.computerListToComputerDtoList(computerList))
      .thenReturn(computerDtoList);
    
    ComputerPage page = new ComputerPage();
    ComputerDtoList res = page.getPage();
    assertTrue(res == computerDtoList);
    assertTrue(page.getNbElement() == 10);
    assertTrue(page.getCurrentPage() == 0);
    assertTrue(page.getNbPage() == 1);

  }

}
