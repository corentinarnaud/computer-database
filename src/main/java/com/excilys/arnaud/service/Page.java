package com.excilys.arnaud.service;

import java.util.List;

public abstract class Page<E>{
	protected int ELMEMENT_BY_PAGE = 10;
	protected List<E> list;
	protected int currentPage=0;
	protected int nbPage;
	protected int nbElement;
	
	
	public Page(){
	  
	}
	
	protected Page(List<E> list, int nbElement){
		this.list=list;
		this.nbElement=nbElement;
		nbPage=(int)Math.ceil((float)nbElement/ELMEMENT_BY_PAGE);
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
	  return nbElement;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	
	
	public List<E> getPage(){
		return list;
	}
	
	public abstract List<E> getNextPage();
	
	public abstract List<E> getPrevPage();
	
	
	public abstract List<E> getPageN(int n);
	
	
	public String toString(){
		return "Page "+currentPage+"/"+nbPage;
	}
	
}
