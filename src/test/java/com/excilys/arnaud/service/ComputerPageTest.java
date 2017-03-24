package com.excilys.arnaud.service;

import static org.junit.Assert.assertTrue;

import com.excilys.arnaud.model.dto.ComputerDtoList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;





@RunWith(MockitoJUnitRunner.class)
public class ComputerPageTest {
  @InjectMocks private static ComputerPage page;
  @Mock ComputerDtoList init;

  @Before
  public void UpBefore() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetPage() {
    ComputerDtoList res = page.getPage();
    assertTrue(res.size() == page.getElementByPage());

  }

  @Test
  public void testGetNextPage() {
    ComputerDtoList res = page.getNextPage();
    assertTrue(res.size() == 3);
    assertTrue(res.get(0).equals(new Integer(10)));

    res = page.getNextPage();
    assertTrue(res.size() == 3);
    assertTrue(res.get(0).equals(new Integer(10)));

  }

  @Test
  public void testGetPrevPage() {
    ComputerDtoList res = page.getPrevPage();
    assertTrue(res.size() == page.getElementByPage());
    assertTrue(res.get(0).equals(new Integer(0)));

    page.getNextPage();
    res = page.getPrevPage();
    assertTrue(res.size() == page.getElementByPage());
    assertTrue(res.get(0).equals(new Integer(0)));

  }

  @Test
  public void testGetPageN() {
    ComputerDtoList res = page.getPageN(1);
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
