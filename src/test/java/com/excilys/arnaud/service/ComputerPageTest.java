package com.excilys.arnaud.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.arnaud.model.metier.Computer;



@RunWith(MockitoJUnitRunner.class)
public class ComputerPageTest {
  @InjectMocks private static ComputerPage page;
  @Mock ArrayList<Computer> init;

  @Before
  public void UpBefore() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetPage() {
    List<Computer> res = page.getPage();
    assertTrue(res.size() == page.getElementByPage());

  }

  @Test
  public void testGetNextPage() {
    List<Computer> res = page.getNextPage();
    assertTrue(res.size() == 3);
    assertTrue(res.get(0).equals(new Integer(10)));

    res = page.getNextPage();
    assertTrue(res.size() == 3);
    assertTrue(res.get(0).equals(new Integer(10)));

  }

  @Test
  public void testGetPrevPage() {
    List<Computer> res = page.getPrevPage();
    assertTrue(res.size() == page.getElementByPage());
    assertTrue(res.get(0).equals(new Integer(0)));

    page.getNextPage();
    res = page.getPrevPage();
    assertTrue(res.size() == page.getElementByPage());
    assertTrue(res.get(0).equals(new Integer(0)));

  }

  @Test
  public void testGetPageN() {
    List<Computer> res = page.getPageN(1);
    assertTrue(res.size() == page.getElementByPage());
    assertTrue(res.get(0).equals(new Integer(0)));

    res = page.getPageN(-56);
    assertTrue(res.size() == page.getElementByPage());
    assertTrue(res.get(0).equals(new Integer(0)));

    res = page.getPageN(2);
    assertTrue(res.size() == 3);
    assertTrue(res.get(0).equals(new Integer(10)));

    res = page.getPageN(12);
    assertTrue(res.size() == 3);
    assertTrue(res.get(0).equals(new Integer(10)));
  }

}
