package com.excilys.arnaud.servlets;


import com.excilys.arnaud.model.dto.ComputerPage;
import com.excilys.arnaud.service.ComputerService;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;



@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

  /**
   * .
   * 
   */
  private static final long serialVersionUID = -8598220867287616063L;
  private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

  @Autowired
  private ComputerService computerService; 
  
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
      config.getServletContext());
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HashMap<DashboardParam, String> parametersMap = getParameters(request);
    traitement(request, parametersMap);
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

  }

  @RequestMapping(method = RequestMethod.POST)
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String ids = request.getParameter("selection");
    if (!StringUtils.isEmpty(ids)) {
      String[] tabId = ids.split(",");
      long[] longIds = new long[tabId.length];
      for (int i = 0; i < tabId.length; i++) {
        longIds[i] = Long.parseLong(tabId[i]);
      }
      computerService.delComputers(longIds);
    }
    doGet(request, response);
  }

  private HashMap<DashboardParam, String> getParameters(HttpServletRequest request) {
    HashMap<DashboardParam, String> parametersMap = new HashMap<>();
    for (DashboardParam param : DashboardParam.values()) {
      parametersMap.put(param,
          request.getParameter(param.toString()) != null ? request.getParameter(param.toString()) : "");
    }
    return parametersMap;
  }

  private void traitement(HttpServletRequest request, HashMap<DashboardParam, String> parametersMap) {
    int pageNumber, nbElementsByPage, order;



    
    if (!parametersMap.get(DashboardParam.ELEMENTS).equals("")) {
      try {
        nbElementsByPage = Integer.parseInt(parametersMap.get(DashboardParam.ELEMENTS));
        request.setAttribute(DashboardParam.ELEMENTS.toString(), nbElementsByPage);
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
        request.setAttribute(DashboardParam.ORDER.toString(), order);
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
    
    request.setAttribute(DashboardParam.SEARCH.toString(), parametersMap.get(DashboardParam.SEARCH));
    request.setAttribute("listComputer", computerPage.getPage());
    request.setAttribute("nbComputer", computerPage.getNbElement());
    request.setAttribute("currentPage", computerPage.getCurrentPage() + 1);
    request.setAttribute("maxPage", computerPage.getNbPage());

  }

}
