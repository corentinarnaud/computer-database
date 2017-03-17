package com.excilys.arnaud.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PageTest {
	private static Page<Integer> page;
	private static List<Integer> init;
	
	@Before
	public void UpBefore(){
		init = new ArrayList<Integer>();
		for(int i=0; i<13;i++){
			init.add(new Integer(i));
		}
		page = new Page<Integer>(init);
	}
	

	@Test
	public void testGetPage(){
		List<Integer> res = page.getPage();
		assertTrue(res.size()==page.getElementByPage());
		assertTrue(res.get(0).equals(new Integer(0)));

	}
	
	@Test
	public void testGetNextPage(){
		List<Integer> res = page.getNextPage();
		assertTrue(res.size()==3);
		assertTrue(res.get(0).equals(new Integer(10)));
		
		res = page.getNextPage();
		assertTrue(res.size()==3);
		assertTrue(res.get(0).equals(new Integer(10)));
		
	}
	
	@Test
	public void testGetPrevPage(){
		List<Integer> res = page.getPrevPage();
		assertTrue(res.size()==page.getElementByPage());
		assertTrue(res.get(0).equals(new Integer(0)));
		
		page.getNextPage();
		res = page.getPrevPage();
		assertTrue(res.size()==page.getElementByPage());
		assertTrue(res.get(0).equals(new Integer(0)));
		
	}
	
	@Test
	public void testGetPageN(){
		List<Integer> res = page.getPageN(1);
		assertTrue(res.size()==page.getElementByPage());
		assertTrue(res.get(0).equals(new Integer(0)));
		
		res = page.getPageN(-56);
		assertTrue(res.size()==page.getElementByPage());
		assertTrue(res.get(0).equals(new Integer(0)));
		
		res = page.getPageN(2);
		assertTrue(res.size()==3);
		assertTrue(res.get(0).equals(new Integer(10)));
		
		res = page.getPageN(12);
		assertTrue(res.size()==3);
		assertTrue(res.get(0).equals(new Integer(10)));
	}
	
}
