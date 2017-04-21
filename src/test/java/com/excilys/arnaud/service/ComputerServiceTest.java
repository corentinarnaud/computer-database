package com.excilys.arnaud.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;

import com.excilys.arnaud.mapper.ComputerMapper;
import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.model.dto.ComputerDtoList;
import com.excilys.arnaud.model.dto.ComputerPage;
import com.excilys.arnaud.model.metier.ComputerList;
import com.excilys.arnaud.persistance.implem.ComputerDAOMySQL;
import com.excilys.arnaud.springConfig.MainConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;





@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainConfig.class)
@PrepareForTest({ComputerDAOMySQL.class, ComputerMapper.class, ComputerService.class})
public class ComputerServiceTest {

  @Mock
  ComputerDAOMySQL mockDAO;
  
  @InjectMocks
  @Autowired
  ComputerService computerService;
  

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
    Mockito.when(mockDAO.getNumberOfComputer()).thenReturn(10);
  }
  
  
  
  @Test
  public void testGetPage() {
    String pattern = "";
    int begin = 0;
    int nbComputer = 10;
    int orderBy = 0;
    
    
    
    ComputerList computerList = new ComputerList();
    ComputerDtoList computerDtoList = new ComputerDtoList();
    
    Mockito.when(mockDAO.getNComputers(pattern, begin, nbComputer, orderBy)).thenReturn(computerList);


    
    
    ComputerPage res = computerService.getComputerPage(0, pattern, orderBy, nbComputer);
    assertTrue(res.getPage().equals(computerDtoList));

    assertTrue(res.getNbElement() == 10);
    assertTrue(res.getCurrentPage() == 0);
    assertTrue(res.getNbPage() == 1);

  }

}
