package com.excilys.arnaud.servlets;

import com.excilys.arnaud.mapper.MapperException;
import com.excilys.arnaud.model.dto.CompanyDto;
import com.excilys.arnaud.model.dto.CompanyDtoList;
import com.excilys.arnaud.model.dto.ComputerDto;
import com.excilys.arnaud.service.CompanyService;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.service.ServiceException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;





@WebServlet("/addComputer")
public class AddComputer extends HttpServlet  {

  /** serialVersionUID.
   * 
   */
  private static final long serialVersionUID = 8749145544902853202L;
  
  @Autowired
  private CompanyService companyService;
  @Autowired
  private ComputerService computerService;
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
      config.getServletContext());
  }
  
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    CompanyDtoList companyList = companyService.getCompanyList();
    String name = request.getParameter("computerName");
    String introducedString = request.getParameter("introduced");
    String discontinuedString = request.getParameter("discontinued");
    String companyIDString = request.getParameter("companyId");
    CompanyDto company = null;
    
    
    if (name != null && !name.equals("")) {
      

      try {
       
        if (companyIDString != null && !companyIDString.isEmpty() && !companyIDString.equals("0")) {
          Optional<CompanyDto> opt = 
              companyService.findById(Long.parseLong(companyIDString));
          company = opt.isPresent() ? opt.get() : null;
        }
      
        computerService.add(
            new ComputerDto(name, company, introducedString, discontinuedString));
      } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch( MapperException e ){
        e.printStackTrace();
      }
    }

    
    request.setAttribute("listCompany", companyList);
    this.getServletContext().getRequestDispatcher("/views/addComputer.jsp")
    .forward(request, response);
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    CompanyDtoList companyList = companyService.getCompanyList();
    request.setAttribute("listCompany", companyList);
    
    this.getServletContext().getRequestDispatcher("/views/addComputer.jsp")
    .forward(request, response);
  }
  
  
}

