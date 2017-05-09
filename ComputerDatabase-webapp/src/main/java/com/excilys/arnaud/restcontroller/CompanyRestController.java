package com.excilys.arnaud.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.arnaud.dto.CompanyDto;
import com.excilys.arnaud.service.CompanyService;

@RestController
@RequestMapping(path = "/company")
public class CompanyRestController {

  
  @Autowired
  CompanyService companyService;
  
  @RequestMapping(method=RequestMethod.GET, value="{id:[\\d]+}")
  public CompanyDto greeting(@PathVariable(value="id") long id) {
      Optional<CompanyDto> computer = companyService.findById(id);
      return computer.isPresent() ? computer.get() : null;
  }
  
  
  @RequestMapping(method=RequestMethod.GET)
  public List<CompanyDto> getPage(@RequestParam(value="page", defaultValue="0") int page,
                                    @RequestParam(value="elements", defaultValue="10") int nbElementsPage) {
      List<CompanyDto> companies;
      if (page > 0) {
        companies = companyService.getCompanyPage(page, nbElementsPage).getPage();
      } else {
        companies = companyService.getCompanyList();
      }
          
      
      return companies;
  }
  
  @RequestMapping(method=RequestMethod.DELETE)
  public boolean delete(@RequestBody long id) {   
    return companyService.delCompany(id);
    
  }
  
  
}
