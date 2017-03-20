package com.excilys.arnaud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

  /** .
   * 
   */
  private static final long serialVersionUID = -8598220867287616063L;
  
  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.html")
                            .forward(request, response);
  }

}
