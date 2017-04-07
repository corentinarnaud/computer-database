package com.excilys.arnaud.servlets;

import com.excilys.arnaud.service.ComputerPage;
import com.excilys.arnaud.service.ComputerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

  /** .
   * 
   */
  private static final long serialVersionUID = -8598220867287616063L;
  private static final Logger logger = LoggerFactory.getLogger(Dashboard.class);
  
  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    

    String paramPage = request.getParameter("page");
    String paramSearch = request.getParameter("search");
    String paramElements = request.getParameter("elements");
    String paramOrderBy = request.getParameter("order");
    int pageNumber;
    
    if (paramSearch == null) {
      paramSearch = "";
    } else {
      request.setAttribute("search", paramSearch);
    }

    
    ComputerPage computerPage = ComputerService.COMPUTERSERVICE.getComputers(paramSearch);
    
    if (paramElements != null) {
      try {
        computerPage.setElementByPage(Integer.parseInt(paramElements));
        request.setAttribute("elements", paramElements);
      } catch (NumberFormatException e) {
        logger.debug("Exception on parsing elements by page " + e.getMessage());
      }
    }
    
    if (paramOrderBy != null) {
      try {
        computerPage.setOrderBy(Integer.parseInt(paramOrderBy));
        request.setAttribute("order", paramOrderBy);
      } catch (NumberFormatException e) {
        logger.debug("Exceptione on parsing order param " +e.getMessage());
      }
    }
    
    if (paramPage != null) {
      try {
        pageNumber = Integer.parseInt(paramPage);
      } catch (NumberFormatException e) {
        logger.debug("Exception on parsing number of page " + e.getMessage());
        this.getServletContext().getRequestDispatcher("/views/404.jsp")
        .forward(request, response);
        return;
      }

    } else {
      pageNumber = 1;
    }

    request.setAttribute("listComputer", computerPage.getPageN(pageNumber - 1));
    request.setAttribute("nbComputer", computerPage.getNbElement());
    request.setAttribute("currentPage", computerPage.getCurrentPage() + 1);
    request.setAttribute("maxPage", computerPage.getNbPage());

    
    
    
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp")
                            .forward(request, response);
  }
  
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String ids = request.getParameter("selection");
    if (ids != null) {
      String[] tabId = ids.split(",");
      if (tabId != null && tabId.length != 0 && !tabId[0].isEmpty()) {
        if (tabId.length == 1) {
          ComputerService.COMPUTERSERVICE.del(Long.parseLong(tabId[0]));
        } else {
          long[] longIds = new long[tabId.length];
          for (int i = 0;i < tabId.length;i++) {
            longIds[i] = Long.parseLong(tabId[i]);
          }
          
          ComputerService.COMPUTERSERVICE.dels(longIds);
        }
           
      }
    }
    doGet(request,response);
  }

}
