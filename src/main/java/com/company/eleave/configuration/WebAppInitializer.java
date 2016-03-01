package com.company.eleave.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.company.eleave.configuration.security.SecurityConfiguration;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	  
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { HibernateConfig.class, WebConfiguration.class, SecurityConfiguration.class };
    }
   
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {};
    }
   
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
  
}
