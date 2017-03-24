package com.excilys.arnaud.servlets;

import com.excilys.arnaud.model.dto.CompanyDtoList;
import com.excilys.arnaud.service.CompanyService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
  
  /** serialVersionUID.
   * 
   */
  private static final long serialVersionUID = -6942829491462214116L;
  
  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    CompanyDtoList companyList = CompanyService.COMPANYSERVICE.getCompanyList();
    request.setAttribute("listCompany", companyList);
    
    this.getServletContext().getRequestDispatcher("/views/editComputer.jsp")
    .forward(request, response);
  }



}
