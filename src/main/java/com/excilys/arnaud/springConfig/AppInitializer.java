package com.excilys.arnaud.springConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@javax.servlet.annotation.HandlesTypes(WebApplicationInitializer.class)
public class AppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // Create the 'root' Spring application context
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(MainConfig.class);

    // Manage the lifecycle of the root application context
    servletContext.addListener(new ContextLoaderListener(rootContext));

    
    ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
    
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/");
   
  }



}
