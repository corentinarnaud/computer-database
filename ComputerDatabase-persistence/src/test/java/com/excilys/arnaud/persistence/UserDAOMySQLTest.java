package com.excilys.arnaud.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.arnaud.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class UserDAOMySQLTest {

  @Autowired
  private UserDAO userDAO;
  
  
  @Test
  public void existTestWhenExist(){
    User user = new User("admin", "admin", Arrays.asList("ROLE_USER"));
    assertTrue(userDAO.exist(user));
  }
  
  @Test
  public void findTestWhenExist(){
    User user = new User("admin", "admin", Arrays.asList("ROLE_USER"));
    assertTrue(userDAO.findByName(user.getName()).isPresent());
  }
  
  @Test
  public void existTestWhenNotExist(){
    User user = new User("dub", "ami", Arrays.asList("ROLE_USER"));
    assertFalse(userDAO.exist(user));
  }
  
  @Test
  public void addTest(){
    User user = new User("toto", "toto", Arrays.asList("ROLE_USER"));
    assertTrue(userDAO.add(user));
    assertTrue(userDAO.exist(user));
  }
  
  @Test
  public void addTestWhenAlreadyExist(){
    try{
      User user = new User("admin", "admin", Arrays.asList("ROLE_USER"));
      userDAO.add(user);
      assertTrue(false);
    } catch (Exception e ){
      assertTrue(true);
    }

  }
}
