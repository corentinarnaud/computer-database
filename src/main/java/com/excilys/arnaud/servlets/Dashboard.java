package com.excilys.arnaud.servlets;

import com.excilys.arnaud.service.ComputerPage;
import com.excilys.arnaud.service.ComputerService;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

  /** .
   * 
   */
  private static final long serialVersionUID = -8598220867287616063L;
  private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    

      HashMap<DashboardParam,String> parametersMap = getParameters(request);
      traitement(request, parametersMap);
      this.getServletContext().getRequestDispatcher("/views/dashboard.jsp")
      .forward(request, response);

  }
  
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String ids = request.getParameter("selection");
    if (!StringUtils.isEmpty(ids)) {
      String[] tabId = ids.split(",");
      long[] longIds = new long[tabId.length];
      for (int i = 0;i < tabId.length;i++) {
        longIds[i] = Long.parseLong(tabId[i]);
      }      
      ComputerService.COMPUTERSERVICE.delComputers(longIds);    
    }
    doGet(request,response);
  }
  
  
  private HashMap<DashboardParam, String> getParameters(HttpServletRequest request){
    HashMap<DashboardParam, String> parametersMap = new HashMap<>();
    for(DashboardParam param : DashboardParam.values()){
      parametersMap.put(param, request.getParameter(param.toString()) == null ? request.getParameter(param.toString()) : "");
    }
    return parametersMap;
  }
  
  
  
  private void traitement(HttpServletRequest request, HashMap<DashboardParam, String> parametersMap){
    int pageNumber;
    

   
    ComputerPage computerPage = ComputerService.COMPUTERSERVICE.getComputers(parametersMap.get(DashboardParam.SEARCH));
    
    if (!parametersMap.get(DashboardParam.ELEMENTS).equals("")) {
      try {
        computerPage.setElementByPage(Integer.parseInt(parametersMap.get(DashboardParam.ELEMENTS)));
        request.setAttribute(DashboardParam.ELEMENTS.toString(), parametersMap.get(DashboardParam.ELEMENTS));
      } catch (NumberFormatException e) {
        LOGGER.debug("Exception on parsing elements by page " + e.getMessage());
      }
    }
    
    if (!parametersMap.get(DashboardParam.ORDER).equals("")) {
      try {
        computerPage.setOrderBy(Integer.parseInt(parametersMap.get(DashboardParam.ORDER)));
        request.setAttribute(DashboardParam.ORDER.toString(), parametersMap.get(DashboardParam.ORDER));
      } catch (NumberFormatException e) {
        LOGGER.debug("Exceptione on parsing order param " +e.getMessage());
      }
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

    request.setAttribute(DashboardParam.SEARCH.toString(), parametersMap.get(DashboardParam.SEARCH.toString()));
    request.setAttribute("listComputer", computerPage.getPageN(pageNumber - 1));
    request.setAttribute("nbComputer", computerPage.getNbElement());
    request.setAttribute("currentPage", computerPage.getCurrentPage() + 1);
    request.setAttribute("maxPage", computerPage.getNbPage());

    
    
    

  }

}
