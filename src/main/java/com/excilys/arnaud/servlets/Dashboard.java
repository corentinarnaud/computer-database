package com.excilys.arnaud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.Page;
import com.excilys.arnaud.service.CompanyService;
import com.excilys.arnaud.service.ComputerService;



@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

  /** .
   * 
   */
  private static final long serialVersionUID = -8598220867287616063L;
  
  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    Page<Computer> computerPage = ComputerService.COMPUTERSERVICE.getComputers();
    
    
    request.setAttribute("page", computerPage);
    
    
    
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp")
                            .forward(request, response);
  }

}
