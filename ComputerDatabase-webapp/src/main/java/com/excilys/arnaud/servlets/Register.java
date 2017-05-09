package com.excilys.arnaud.servlets;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.arnaud.dto.UserDto;
import com.excilys.arnaud.service.UserService;

@Controller
@RequestMapping("/registration")
public class Register {
  private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);
  
  @Autowired
  private UserService userService;
  
  @RequestMapping(method = RequestMethod.GET)
  public String showRegistrationForm(WebRequest request, Model model) {
      UserDto userDto = new UserDto();
      model.addAttribute("user", userDto);
      return "registration";
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView registerUserAccount
        (@ModelAttribute("user") @Valid UserDto accountDto, 
        BindingResult result, WebRequest request, Errors errors) {    

      LOGGER.debug("Creation of user : " + accountDto);
      if (!result.hasErrors()) {
        if (createUserAccount(accountDto, result) == false) {
          LOGGER.debug("Username " + accountDto.getName() + " already exist.");
          result.rejectValue("name", "exist");
        }
      }
      if (result.hasErrors()) {
        for(ObjectError e : result.getAllErrors()) {
          LOGGER.debug(e.getObjectName());
        }
        return new ModelAndView("registration", "user", accountDto);
    } 
    else {
        return new ModelAndView("dashboard", "user", accountDto);
    }
  }
  
  private boolean createUserAccount(UserDto accountDto, BindingResult result) {
    return userService.registerNewUserAccount(accountDto);
}

}
