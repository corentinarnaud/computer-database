package com.excilys.arnaud.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.arnaud.model.metier.Company;
import com.excilys.arnaud.model.metier.CompanyList;
import com.excilys.arnaud.model.metier.Computer;
import com.excilys.arnaud.service.CompanyService;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.service.ServiceException;



@WebServlet("/addComputer")
public class AddComputer extends HttpServlet  {

  /**
   * 
   */
  private static final long serialVersionUID = 8749145544902853202L;
  
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    CompanyList companyList = CompanyService.COMPANYSERVICE.getCompanyList();
    String name = request.getParameter("computerName");
    String introducedString = request.getParameter("introduced");
    String discontinuedString = request.getParameter("discontinued");
    String companyIDString = request.getParameter("companyId");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime introduced = null;
    LocalDateTime discontinued = null;
    Company company = null;
    
    
    
    if(name!=null && !name.equals("")){
      
      

      try{
      if(introducedString!=null && !introducedString.isEmpty()){
        introduced = LocalDateTime
            .from(LocalDate.parse(introducedString, formatter).atStartOfDay());
        if(discontinuedString!=null && !discontinuedString.isEmpty()){
          discontinued = LocalDateTime
              .from(LocalDate.parse(discontinuedString, formatter).atStartOfDay());
        }
      }
      
      if(companyIDString!=null && !companyIDString.isEmpty() && !companyIDString.equals("0")){
        Optional<Company> opt = CompanyService.COMPANYSERVICE.findById(Long.parseLong(companyIDString));
        company = opt.isPresent() ? opt.get() : null;
      }
      
        ComputerService.COMPUTERSERVICE.add(new Computer(name, company, introduced, discontinued));
      } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch( DateTimeParseException e ){
        
      }
    }

    
    request.setAttribute("listCompany", companyList);
    this.getServletContext().getRequestDispatcher("/views/addComputer.jsp")
    .forward(request, response);
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    CompanyList companyList = CompanyService.COMPANYSERVICE.getCompanyList();
    request.setAttribute("listCompany", companyList);
    
    this.getServletContext().getRequestDispatcher("/views/addComputer.jsp")
    .forward(request, response);
  }
  
  
 }

