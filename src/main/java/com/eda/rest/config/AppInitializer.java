package com.eda.rest.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
	private Logger logger = Logger.getLogger(AppInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		try {
			AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
			appContext.register(WebConfig.class, AppConfig.class);

			ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
					new DispatcherServlet(appContext));
			dispatcher.setLoadOnStartup(1);
			dispatcher.addMapping("/");
			FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", CorsFilter.class);
			corsFilter.addMappingForUrlPatterns(null, false, "/*");

		} catch (Exception e) {
			logger.error("AppInitializer:::" + e);
		}

	}
	
	

}
