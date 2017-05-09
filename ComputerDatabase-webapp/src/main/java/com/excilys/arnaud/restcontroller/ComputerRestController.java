package com.excilys.arnaud.restcontroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.arnaud.dto.ComputerDto;
import com.excilys.arnaud.service.ComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerRestController {
  
  @Autowired
  ComputerService computerService;
  
  @RequestMapping(method=RequestMethod.GET, value="/{id:[\\d]+}")
  public ComputerDto getComputer(@PathVariable(value="id") long id) {
      Optional<ComputerDto> computer = computerService.findById(id);
      
      return computer.isPresent() ? computer.get() : null;
  }
  
  
  @RequestMapping(method=RequestMethod.GET)
  public List<ComputerDto> getPage(@RequestParam(value="page", defaultValue="1") int page,
                                    @RequestParam(value="search", defaultValue="") String pattern,
                                    @RequestParam(value="order", defaultValue="1") int orderBy,
                                    @RequestParam(value="elements", defaultValue="10") int nbElementsPage) {
      List<ComputerDto> computers = computerService.getComputerPage(page, pattern, orderBy, nbElementsPage)
          .getPage();
      
      return computers;
  }
  
  @RequestMapping(method=RequestMethod.POST)
  public long add(@RequestBody @Valid ComputerDto computer, 
      BindingResult result) {
    if(!result.hasErrors()){
      return computerService.add(computer);
    }
    
    return -1;
    
  }
  
  @RequestMapping(method=RequestMethod.DELETE)
  public boolean delete(@RequestBody long id) {   
    return computerService.delComputer(id);
    
  }
  
  @RequestMapping(method=RequestMethod.PUT)
  public boolean update(@RequestBody @Valid ComputerDto computer, 
      BindingResult result) {
    if(!result.hasErrors()){
      return computerService.update(computer);
    }
    
    return false;
    
  }

}
