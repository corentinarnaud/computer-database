package com.excilys.arnaud.springConfig;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.excilys.arnaud.persistance.ComputerDAO;
import com.excilys.arnaud.persistance.DataBaseManager;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.springConfig.MainConfig;
import com.zaxxer.hikari.HikariDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainConfig.class)
public class SpringConfigTest {

    @Autowired
    private HikariDataSource dataSource;
    @Autowired
    private DataBaseManager dataBaseManager;    
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private ComputerService computerService;

    
    

    @Test
    public void springConfiguration() {
      assertNotNull(dataSource);
      assertNotNull(dataBaseManager);
      assertNotNull(wac);
      assertNotNull(computerDAO);
      assertNotNull(computerService);

    }
}
