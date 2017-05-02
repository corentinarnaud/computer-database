package com.excilys.arnaud.servlets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class Login {
  
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(ModelMap model) {
    return "login";
  }

}
