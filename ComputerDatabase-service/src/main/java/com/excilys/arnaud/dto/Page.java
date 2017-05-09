package com.excilys.arnaud.dto;

import java.util.List;

public class Page<E> {
  protected int elementsByPage = 10;
  protected List<E> list;
  protected int currentPage = 0;
  protected int nbPage;
  protected int nbElement;

  /**
   * @param list : List of computer
   * @param nbElement : Total number of computer
   * @param currentPage : Current page
   */
  public Page(List<E> list, int nbElement, int currentPage) {
    this.list = list;
    this.nbElement = nbElement;
    this.elementsByPage = list.size();
    nbPage = (int) Math.ceil((float) nbElement / elementsByPage);
    this.currentPage = currentPage;
  }


  public int getElementByPage() {
    return elementsByPage;
  }

  public void setElementByPage(int nb) {
    currentPage = 0;
    elementsByPage = nb;
  }

  public int getNbPage() {
    return nbPage;
  }

  public int getNbElement() {
    return nbElement;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public List<E> getPage() {
    return list;
  }

  public String toString() {
    return "Page " + currentPage + "/" + nbPage;
  }
}
