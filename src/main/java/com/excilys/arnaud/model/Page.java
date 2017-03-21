package com.excilys.arnaud.model;

import java.util.List;

public class Page<E>{
	private static int ELMEMENT_BY_PAGE = 10;
	private List<E> list;
	private int currentPage=0;
	private int nbPage;
	
	
	public Page(List<E> list){
		this.list=list;
		nbPage=(int)Math.ceil((float)list.size()/ELMEMENT_BY_PAGE);
	}
	
	public Page(int nbPage){
    this.nbPage=nbPage;
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
	
	public int getNbElement(){
	  return list.size();
	}
	
	public int getCurrentPage(){
		return currentPage+1;
	}
	
	
	public List<E> getPage(){
		int toIndex = currentPage*ELMEMENT_BY_PAGE+ELMEMENT_BY_PAGE;
		return list.subList(currentPage*ELMEMENT_BY_PAGE, toIndex>list.size() ? list.size() : toIndex);
	}
	
	public List<E> getNextPage(){
		if(currentPage<nbPage-1)
			currentPage++;
		return getPage();
	}
	
	public List<E> getPrevPage(){
		if(currentPage>0)
			currentPage--;
		return getPage();
	}
	
	
	public List<E> getPageN(int n){
		if(n-1>=0&&n-1<nbPage)
			currentPage=n-1;
		return getPage();
	}
	
	
	public String toString(){
		return "Page "+currentPage+"/"+nbPage;
	}
	
}
