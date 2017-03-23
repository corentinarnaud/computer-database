package com.excilys.arnaud.model.metier;

import java.util.ArrayList;
import java.util.List;

public class CompanyList extends ArrayList<Company> {


  private static final long serialVersionUID = -8769932831218436965L;

  public CompanyList(List<Company> companyList) {
    super(companyList);
  }

  public CompanyList() {
    super();
  }

  public CompanyList subList(int fromIndex, int toIndex) {
    return new CompanyList(super.subList(fromIndex, toIndex));
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i < size(); i++) {
      string += get(i) + "\n";
    }

    return string;
  }

}
