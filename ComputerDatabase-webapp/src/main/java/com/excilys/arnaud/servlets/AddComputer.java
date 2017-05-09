package com.excilys.arnaud.servlets;

import com.excilys.arnaud.mapper.MapperException;
import com.excilys.arnaud.dto.CompanyDto;
import com.excilys.arnaud.dto.CompanyDtoList;
import com.excilys.arnaud.dto.ComputerDto;
import com.excilys.arnaud.service.CompanyService;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.service.ServiceException;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
@RequestMapping("/addComputer")
public class AddComputer{

  
  @Autowired
  private CompanyService companyService;
  @Autowired
  private ComputerService computerService;
  
  
  @RequestMapping(method = RequestMethod.POST)
  public String doPost(ModelMap model, @RequestParam Map<String, String> param) {
    CompanyDtoList companyList = companyService.getCompanyList();
    String name = param.get("computerName");
    String introducedString = param.get("introduced");
    String discontinuedString = param.get("discontinued");
    String companyIDString = param.get("companyId");
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
        e.printStackTrace();
      } catch( MapperException e ){
        e.printStackTrace();
      }
    }

    
    model.addAttribute("listCompany", companyList);
    return "addComputer";
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(ModelMap model) {
    
    CompanyDtoList companyList = companyService.getCompanyList();
    model.addAttribute("listCompany", companyList);
    
    return "addComputer";
  }
  
  
}

