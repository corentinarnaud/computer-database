package com.excilys.arnaud.servlets;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.arnaud.dto.UserDto;
import com.excilys.arnaud.model.User;
import com.excilys.arnaud.service.ServiceException;
import com.excilys.arnaud.service.UserService;

@Controller
@RequestMapping("/registration")
public class Register {
  
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
      boolean registered = false;
      if (!result.hasErrors()) {
          registered = createUserAccount(accountDto, result);
      }
      if (registered == false) {
        //TODO; Somethings like that :
        result.rejectValue("name", "exist");
      }
      if (result.hasErrors()) {
        return new ModelAndView("registration", "user", accountDto);
    } 
    else {
        return new ModelAndView("login", "user", accountDto);
    }
  }
  
  private boolean createUserAccount(UserDto accountDto, BindingResult result) {
    return userService.registerNewUserAccount(accountDto);
}

}
