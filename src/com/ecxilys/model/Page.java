package com.ecxilys.model;

import java.util.List;

public class Page<E>{
	private static int ELMEMENT_BY_PAGE = 10;
	private List<E> list;
	private int currentPage=0;
	private int nbPage;
	
	
	public Page(List<E> list){
		this.list=list;
		nbPage=list.size();
	}
	
	public int getElementByPage(){
		return ELMEMENT_BY_PAGE;
	}

	public void setElementByPage(int nb){
		currentPage=0;
		ELMEMENT_BY_PAGE=nb;
	}
	
	public int getNbPage(){
		return nbPage;
	}
	
	public int getCurrentPage(){
		return currentPage+1;
	}
	
	
	public List<E> getPage(){
		return list.subList(currentPage*ELMEMENT_BY_PAGE, currentPage*ELMEMENT_BY_PAGE+ELMEMENT_BY_PAGE);
	}
	
	public List<E> getNextPage(){
		if(currentPage<nbPage)
			currentPage++;
		return getPage();
	}
	
	public List<E> getPrevPage(){
		if(currentPage>0)
			currentPage--;
		return getPage();
	}
	
}
