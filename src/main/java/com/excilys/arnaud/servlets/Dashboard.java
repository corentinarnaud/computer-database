package com.excilys.arnaud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.service.Page;



@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

  /** .
   * 
   */
  private static final long serialVersionUID = -8598220867287616063L;
  
  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    HttpSession session = request.getSession();
    String paramPage = request.getParameter("page");
    String paramSearch = request.getParameter("search");
    
    if(paramSearch != null){
      session.setAttribute("search", paramSearch);
    } else if(session.getAttribute("search")!=null){
      paramSearch = (String) session.getAttribute("search");
    }
    
    Page<Computer> computerPage = ComputerService.COMPUTERSERVICE.getComputers(paramSearch);
    
    if(paramPage!=null){
      switch(paramPage){
      case "n" :
        request.setAttribute("listComputer", computerPage.getNextPage());
        break;
      case "p" :
        request.setAttribute("listComputer", computerPage.getPrevPage());
        break;
      default :
        try {
          int pageNumber = Integer.parseInt(paramPage);
          request.setAttribute("listComputer", computerPage.getPageN(pageNumber-1));
        } catch (NumberFormatException e) {
          this.getServletContext().getRequestDispatcher("/views/404.jsp")
          .forward(request, response);
          return;
        }
      }

    } else {
      paramPage = request.getParameter("elements");
      if(paramPage!=null){
        try {
          int nbElements = Integer.parseInt(paramPage);
          computerPage.setElementByPage(nbElements);
        } catch (NumberFormatException e) {
          
        }
      }
      request.setAttribute("listComputer", computerPage.getPage());
    }
    
    request.setAttribute("nbComputer", computerPage.getNbElement());
    request.setAttribute("currentPage", computerPage.getCurrentPage()+1);
    request.setAttribute("maxPage", computerPage.getNbPage());

    
    
    
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp")
                            .forward(request, response);
  }
  
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String[] tabId = request.getParameter("selection").split(",");
    if(tabId!=null && tabId.length!=0 && !tabId[0].isEmpty()){
      if(tabId.length==1){
        ComputerService.COMPUTERSERVICE.del(Long.parseLong(tabId[0]));
      } else {
        long[] longIds = new long[tabId.length];
        for(int i = 0;i < tabId.length;i++){
          longIds[i]=Long.parseLong(tabId[i]);
        }
        
        ComputerService.COMPUTERSERVICE.dels(longIds);
      }
         
    }
    
    doGet(request,response);
  }

}
