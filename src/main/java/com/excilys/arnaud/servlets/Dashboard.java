package com.excilys.arnaud.servlets;


import com.excilys.arnaud.model.dto.ComputerPage;
import com.excilys.arnaud.service.ComputerService;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/dashboard")
public class Dashboard{


  private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

  @Autowired
  private ComputerService computerService; 
  
  

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(ModelMap model, @RequestParam Map<String, String> param) {

    HashMap<DashboardParam, String> parametersMap = getParameters(param);
    traitement(model, parametersMap);
    return "dashboard";

  }

  @RequestMapping(method = RequestMethod.POST)
  public void doPost(ModelMap model, @RequestParam Map<String, String> param) {
    String ids = (String) param.getOrDefault("selection", "");
    if (!StringUtils.isEmpty(ids)) {
      String[] tabId = ids.split(",");
      long[] longIds = new long[tabId.length];
      for (int i = 0; i < tabId.length; i++) {
        longIds[i] = Long.parseLong(tabId[i]);
      }
      computerService.delComputers(longIds);
    }
    doGet(model, param);
  }

  private HashMap<DashboardParam, String> getParameters(Map<String, String> mapParam) {
    HashMap<DashboardParam, String> parametersMap = new HashMap<>();
    for (DashboardParam param : DashboardParam.values()) {
      parametersMap.put(param,
          mapParam.getOrDefault(param.toString(), ""));
    }
    return parametersMap;
  }

  private void traitement(ModelMap model, HashMap<DashboardParam, String> parametersMap) {
    int pageNumber, nbElementsByPage, order;



    
    if (!parametersMap.get(DashboardParam.ELEMENTS).equals("")) {
      try {
        nbElementsByPage = Integer.parseInt(parametersMap.get(DashboardParam.ELEMENTS));
        model.addAttribute(DashboardParam.ELEMENTS.toString(), nbElementsByPage);
      } catch (NumberFormatException e) {
        LOGGER.debug("Exception on parsing elements by page " + e.getMessage());
        nbElementsByPage = 10;
      }
    } else {
      nbElementsByPage = 10;
    }

    if (!parametersMap.get(DashboardParam.ORDER).equals("")) {
      try {
        order = Integer.parseInt(parametersMap.get(DashboardParam.ORDER));
        model.addAttribute(DashboardParam.ORDER.toString(), order);
      } catch (NumberFormatException e) {
        LOGGER.debug("Exceptione on parsing order param " + e.getMessage());
        order = 0;
      }
    } else {
      order = 0;
    }

    if (!parametersMap.get(DashboardParam.PAGE).equals("")) {
      try {
        pageNumber = Integer.parseInt(parametersMap.get(DashboardParam.PAGE));
      } catch (NumberFormatException e) {
        pageNumber = 1;
        LOGGER.debug("Exception on parsing number of page " + e.getMessage());
      }

    } else {
      pageNumber = 1;
    }
    
    ComputerPage computerPage = computerService.getComputerPage(pageNumber - 1, parametersMap.get(DashboardParam.SEARCH), order, nbElementsByPage); 
    
    model.addAttribute(DashboardParam.SEARCH.toString(), parametersMap.get(DashboardParam.SEARCH));
    model.addAttribute("listComputer", computerPage.getPage());
    model.addAttribute("nbComputer", computerPage.getNbElement());
    model.addAttribute("currentPage", computerPage.getCurrentPage() + 1);
    model.addAttribute("maxPage", computerPage.getNbPage());

  }

}
