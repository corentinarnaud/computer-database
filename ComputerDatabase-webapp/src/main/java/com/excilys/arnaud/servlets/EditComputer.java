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
@RequestMapping("/editComputer")
public class EditComputer{


  @Autowired
  private CompanyService companyService;
  @Autowired
  private ComputerService computerService;
  


  @RequestMapping(method = RequestMethod.GET)
  public String doGet(ModelMap model, @RequestParam Map<String, String> param){

    String idString = param.get("id");
    long id = 0;

    if (idString == null) {
      return "404";
    }
    try {
      id = Long.valueOf(idString);
    } catch (NumberFormatException e) {
      return "404";
    }

    CompanyDtoList companyList = companyService.getCompanyList();
    model.addAttribute("listCompany", companyList);

    Optional<ComputerDto> computerDto = computerService.findById(id);
    if (!computerDto.isPresent()) {
      return "404";
    }
    model.addAttribute("computer", computerDto.get());

    return "editComputer";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String doPost(ModelMap model, @RequestParam Map<String, String> param) {
    String idString = param.get("id");
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
      
        computerService.update(
            new ComputerDto(idString, name, company, introducedString, discontinuedString));
      } catch (ServiceException e) {
        e.printStackTrace();
      } catch (MapperException e) {
        e.printStackTrace();
      }
    }
    return "redirect:/dashboard";
  }

}
