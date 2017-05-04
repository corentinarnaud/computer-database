package com.excilys.arnaud.persistence;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.arnaud.persistence.ComputerDAO;
import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.ComputerList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class ComputerDAOMySQLTest {
  @Autowired
  private ComputerDAO computerDAO;
  
  @Test
  public void findByIdTest() {
    Optional<Computer> computer;

    computer = computerDAO.findById(7);
    assertTrue(computer.isPresent());
    assertTrue(computer.get().getId() == 7);
    assertTrue(computer.get().getName().compareTo("Apple IIe") == 0);
    assertTrue(computer.get().getIntroduced() == null);
    assertTrue(computer.get().getDiscontinued() == null);
    assertTrue(computer.get().getCompany() == null);

    computer = computerDAO.findById(12);
    assertTrue(computer.isPresent());
    assertTrue(computer.get().getId() == 12);
    assertTrue(computer.get().getName().compareTo("Apple III") == 0);
    assertTrue(computer.get().getIntroduced().equals(
        LocalDateTime.from(LocalDate.parse("1980-05-01").atStartOfDay())));
    assertTrue(computer.get().getDiscontinued().equals(
        LocalDateTime.from(LocalDate.parse("1984-04-01").atStartOfDay())));
    assertTrue(computer.get().getCompany().getId() == 1);
    assertTrue(computer.get().getCompany().getName().compareTo("Apple Inc.") == 0);

    computer = computerDAO.findById(-1);
    assertFalse(computer.isPresent());

    computer = computerDAO.findById(963);
    assertFalse(computer.isPresent());
  }
  
  
  @Test
  public void findByNameTest() {
    Optional<Computer> computer;

    computer = computerDAO.findByName("Apple IIe");
    assertTrue(computer.isPresent());
    assertTrue(computer.get().getId() == 7);
    assertTrue(computer.get().getName().compareTo("Apple IIe") == 0);
    assertTrue(computer.get().getIntroduced() == null);
    assertTrue(computer.get().getDiscontinued() == null);
    assertTrue(computer.get().getCompany() == null);

    computer = computerDAO.findByName("Apple III");
    assertTrue(computer.isPresent());
    assertTrue(computer.get().getId() == 12);
    assertTrue(computer.get().getName().compareTo("Apple III") == 0);
    assertTrue(computer.get().getIntroduced().equals(
        LocalDateTime.from(LocalDate.parse("1980-05-01").atStartOfDay())));
    assertTrue(
        computer.get().getDiscontinued().equals(
            LocalDateTime.from(LocalDate.parse("1984-04-01").atStartOfDay())));
    assertTrue(computer.get().getCompany().getId() == 1);
    assertTrue(computer.get().getCompany().getName().compareTo("Apple Inc.") == 0);

    computer = computerDAO.findByName("dgsqoimuvh");
    assertFalse(computer.isPresent());

    computer = computerDAO.findByName(null);
    assertFalse(computer.isPresent());
  }
  
  @Test
  public void getComputersTest() {
    ComputerList computerList = computerDAO.getComputers();
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 574);
    assertTrue(computerList.get(5).getId() == 5 + 1);
  }
  
  @Test
  public void getNComputersTest() {
    ComputerList computerList = computerDAO.getNComputers(0, 10, 0);
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 10);
    assertTrue(computerList.get(5).getId() == 5 + 1);
    assertTrue(computerList.get(6).getId() == 6 + 1);
    assertNotNull(computerList.get(0).getCompany());
    
    computerList = computerDAO.getNComputers(-5, 10, 0);
    assertTrue(computerList.isEmpty());
    
    computerList = computerDAO.getNComputers(0, -10, 0);
    assertTrue(computerList.isEmpty());

  }
  
  @Test
  public void getNComputersTestOrderBy1() {
    ComputerList computerList = computerDAO.getNComputers(0, 10, 1);
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 10);
    assertTrue(computerList.get(0).getId() == 381);
    assertTrue(computerList.get(1).getId() == 330);

  }
  
  @Test
  public void getNComputersTestOrderBy2() {
    ComputerList computerList = computerDAO.getNComputers(0, 10, 2);
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 10);
    assertTrue(computerList.get(0).getId() == 480);
    assertTrue(computerList.get(1).getId() == 286);

  }
  
  @Test
  public void getNComputersTestOrderBy3() {
    ComputerList computerList = computerDAO.getNComputers(0, 10, 3);
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 10);
    assertTrue(computerList.get(0).getId() == 358);
    assertTrue(computerList.get(1).getId() == 377);

  }
  
  @Test
  public void getNComputersPatternTest() {
    ComputerList computerList = computerDAO.getNComputers("apple", 10, 10, 0);
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 10);
    assertTrue(computerList.get(1).getId() == 21);
    
    computerList = computerDAO.getNComputers(null, 0, 10, 0);
    assertFalse(computerList == null);
    assertTrue(computerList.size() == 10);
    assertTrue(computerList.get(5).getId() == 5 + 1);
  }
 
  
  @Test
  public void addTestNull() {
    long id;
    id = computerDAO.add(null);
    assertTrue(id == -1);
  }
    

  @Test
  public void addTestComputerNoOptional() {
    long id;
    Optional<Computer> resultat;
    Computer computer1 = new Computer("toto", null, null, null);

    id = computerDAO.add(computer1);
    resultat = computerDAO.findById(id);
    assertTrue(resultat.isPresent());
    assertTrue(resultat.get().equals(computer1));

    computerDAO.del(id);
  }
  
  @Test
  public void addTestComputerFull() {
    long id;
    Optional<Computer> resultat;
    Computer computer2 = new Computer("toto2", new Company(1, "Apple Inc."),
        LocalDate.parse("1980-05-01").atStartOfDay(), LocalDate.parse("1984-04-01").atStartOfDay());
    
    id = computerDAO.add(computer2);
    resultat = computerDAO.findById(id);
    assertTrue(resultat.isPresent());
    assertTrue(resultat.get().equals(computer2));

    computerDAO.del(id);
  }
  
  @Test
  public void dellTest() {
    Computer computer1 = new Computer("toto", null, null, null);
    long id = computerDAO.add(computer1);

    computerDAO.del(id);
    assertFalse(computerDAO.findById(id).isPresent());
  }
  
  @Test
  public void dellTestNotExist() {

    assertFalse(computerDAO.del(-5));

  }
  
  
  @Test
  public void dellsTest() {
    List<Long> ids = new ArrayList<>();
    Computer computer1 = new Computer("toto", null, null, null);
    ids.add(computerDAO.add(computer1));
    
    computer1 = new Computer("toto", null, null, null);
    ids.add(computerDAO.add(computer1));

    computerDAO.dels(ids);
    assertFalse(computerDAO.findById(ids.get(0)).isPresent());
    assertFalse(computerDAO.findById(ids.get(1)).isPresent());
  }
  
  @Test
  public void dellsFromCompanyTest() {
    Company company = new Company(44, "Test");
    List<Long> ids = new ArrayList<>();
    Computer computer1 = new Computer("toto", company, null, null);
    ids.add(computerDAO.add(computer1));
    
    computer1 = new Computer("toto", company, null, null);
    ids.add(computerDAO.add(computer1));

    computerDAO.delsFromCompany(44);
    assertFalse(computerDAO.findById(ids.get(0)).isPresent());
    assertFalse(computerDAO.findById(ids.get(1)).isPresent());
  }
  
  
  @Test
  public void updateTest() {
    boolean update;
    long id;
    Optional<Computer> resultat;
    Computer computer1 = new Computer("toto", null, null, null);
    Computer computer2 = new Computer("toto2", new Company(1, "Apple Inc."),
        LocalDate.parse("1980-05-01").atStartOfDay(), LocalDate.parse("1984-04-01").atStartOfDay());

    update = computerDAO.update(null);
    assertTrue(update == false);

    id = computerDAO.add(computer1);
    computer2.setId(id);

    update = computerDAO.update(computer2);
    resultat = computerDAO.findById(id);
    assertTrue(update == true);
    assertTrue(resultat.get().equals(computer2));

    update = computerDAO.update(computer1);
    resultat = computerDAO.findById(id);
    assertTrue(update == true);
    assertTrue(resultat.get().equals(computer1));

    computerDAO.del(id);
  }
  
  @Test
  public void getNumberOfCompanyTest(){
    int nb = computerDAO.getNumberOfComputer();
    assertTrue(nb == 574);
  }
  
  @Test
  public void getNumberOfCompanyTestPattern(){
    int nb = computerDAO.getNumberOfComputer("s");
    assertTrue(nb == 44);
  }
  
}
