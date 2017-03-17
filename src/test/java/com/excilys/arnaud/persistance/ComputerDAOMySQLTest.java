package com.excilys.arnaud.persistance;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.ComputerList;

public class ComputerDAOMySQLTest {
	private ComputerDAOMySQL computerDAOMySQL = ComputerDAOMySQL.COMPUTERDAO;
	
	@Test
	public void findByIdTest(){
		Optional<Computer> computer;
		
		computer = computerDAOMySQL.findById(7);
		assertTrue(computer.isPresent());
		assertTrue(computer.get().getId()==7);
		assertTrue(computer.get().getName().compareTo("Apple IIe")==0);
		assertTrue(computer.get().getIntroduced()==null);
		assertTrue(computer.get().getDiscontinued()==null);
		assertTrue(computer.get().getCompany()==null);
		
		computer = computerDAOMySQL.findById(12);
		assertTrue(computer.isPresent());
		assertTrue(computer.get().getId()==12);
		assertTrue(computer.get().getName().compareTo("Apple III")==0);
		assertTrue(computer.get().getIntroduced().equals(
				LocalDateTime.from(LocalDate.parse("1980-05-01").atStartOfDay())));
		assertTrue(computer.get().getDiscontinued().equals(
				LocalDateTime.from(LocalDate.parse("1984-04-01").atStartOfDay())));
		assertTrue(computer.get().getCompany().getId()==1);
		assertTrue(computer.get().getCompany().getName().compareTo("Apple Inc.")==0);
		
		computer = computerDAOMySQL.findById(-1);
		assertFalse(computer.isPresent());
		
		computer = computerDAOMySQL.findById(963);
		assertFalse(computer.isPresent());
	}
	
	
	@Test
	public void findByIdName(){
		Optional<Computer> computer;
		
		computer = computerDAOMySQL.findByName("Apple IIe");
		assertTrue(computer.isPresent());
		assertTrue(computer.get().getId()==7);
		assertTrue(computer.get().getName().compareTo("Apple IIe")==0);
		assertTrue(computer.get().getIntroduced()==null);
		assertTrue(computer.get().getDiscontinued()==null);
		assertTrue(computer.get().getCompany()==null);
		
		computer = computerDAOMySQL.findByName("Apple III");
		assertTrue(computer.isPresent());
		assertTrue(computer.get().getId()==12);
		assertTrue(computer.get().getName().compareTo("Apple III")==0);
		assertTrue(computer.get().getIntroduced().equals(
				LocalDateTime.from(LocalDate.parse("1980-05-01").atStartOfDay())));
		assertTrue(computer.get().getDiscontinued().equals(
				LocalDateTime.from(LocalDate.parse("1984-04-01").atStartOfDay())));
		assertTrue(computer.get().getCompany().getId()==1);
		assertTrue(computer.get().getCompany().getName().compareTo("Apple Inc.")==0);
		
		computer = computerDAOMySQL.findByName("dgsqoimuvh");
		assertFalse(computer.isPresent());
		
		computer = computerDAOMySQL.findByName(null);
		assertFalse(computer.isPresent());
	}
	
	@Test
	public void getComputersTest(){
		ComputerList companyList = computerDAOMySQL.getComputers();
		assertFalse(companyList==null);
		assertTrue(companyList.size()==574);
		assertTrue(companyList.get(5).getId()==5+1);
	}

	
	@Test
	public void addTest(){
		long id;
		Optional<Computer> resultat;
		Computer computer1 = new Computer("toto", null, null, null);
		Computer computer2 = new Computer("toto2",
										  new Company(1,"Apple Inc."),
										  LocalDate.parse("1980-05-01").atStartOfDay(),
										  LocalDate.parse("1984-04-01").atStartOfDay());
		
		id = computerDAOMySQL.add(null);
		assertTrue(id==-1);
		
		id= computerDAOMySQL.add(computer1);
		resultat = computerDAOMySQL.findById(id);
		assertTrue(resultat.isPresent());
		assertTrue(resultat.get().equals(computer1));
		
		computerDAOMySQL.del(id);
		
		id= computerDAOMySQL.add(computer2);
		resultat = computerDAOMySQL.findById(id);
		assertTrue(resultat.isPresent());
		assertTrue(resultat.get().equals(computer2));

		computerDAOMySQL.del(id);
	}
	
	@Test
	public void dellTest(){
		Computer computer1 = new Computer("toto", null, null, null);
		long id = computerDAOMySQL.add(computer1);
		
		computerDAOMySQL.del(id);
		assertFalse(computerDAOMySQL.findById(id).isPresent());
	}
	
	
	@Test
	public void updateTest(){
		boolean update;
		long id;
		Optional<Computer> resultat;
		Computer computer1 = new Computer("toto", null, null, null);
		Computer computer2 = new Computer("toto2",
										  new Company(1,"Apple Inc."),
										  LocalDate.parse("1980-05-01").atStartOfDay(),
										  LocalDate.parse("1984-04-01").atStartOfDay());
		
		
		update = computerDAOMySQL.update(null);
		assertTrue(update==false);
		
		id = computerDAOMySQL.add(computer1);
		computer2.setId(id);
		
		update = computerDAOMySQL.update(computer2);
		resultat = computerDAOMySQL.findById(id);
		assertTrue(update==true);
		assertTrue(resultat.get().equals(computer2));
		
		update = computerDAOMySQL.update(computer1);
		resultat = computerDAOMySQL.findById(id);
		assertTrue(update==true);
		assertTrue(resultat.get().equals(computer1));
		
		
		computerDAOMySQL.del(id);
	}

}
