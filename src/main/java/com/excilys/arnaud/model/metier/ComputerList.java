package com.excilys.arnaud.model.metier;

import java.util.ArrayList;
import java.util.Collection;

public class ComputerList extends ArrayList<Computer> {

  private static final long serialVersionUID = 7470912263550875109L;

  public ComputerList() {
    super();
  }

  public ComputerList(Collection<Computer> list) {
    super(list);
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i < size(); i++) {
      string += get(i) + "\n";
    }

    return string;
  }

  public ComputerList subList(int fromIndex, int toIndex) {
    return new ComputerList(super.subList(fromIndex, toIndex));
  }
}
