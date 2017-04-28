package com.excilys.arnaud.service;

import static org.junit.Assert.assertTrue;


import org.junit.Before;

import com.excilys.arnaud.dto.ComputerDtoList;
import com.excilys.arnaud.dto.ComputerPage;
import com.excilys.arnaud.model.ComputerList;
import com.excilys.arnaud.persistence.implem.ComputerDAOMySQL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class ComputerServiceTest {

  @Mock
  ComputerDAOMySQL mockDAO;
  
  @InjectMocks
  @Autowired
  ComputerService computerService;
  

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(computerService, "computerDAO", mockDAO);
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
    ReflectionTestUtils.setField(computerService, "computerDAO", mockDAO);
    Mockito.when(mockDAO.getNComputers(pattern, begin, nbComputer, orderBy)).thenReturn(computerList);


    
    
    ComputerPage res = computerService.getComputerPage(0, pattern, orderBy, nbComputer);
    assertTrue(res.getPage().equals(computerDtoList));

    assertTrue(res.getNbElement() == 10);
    assertTrue(res.getCurrentPage() == 0);
    assertTrue(res.getNbPage() == 1);

  }

}
