package com.eda.rest.config;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import com.eda.rest.security.AuthenticationFilter;
import com.eda.rest.security.GsonMessageBodyHandler;

public class CustomApplication extends ResourceConfig 
{
	public CustomApplication() 
	{
		packages("com.eda.rest");
		register(LoggingFilter.class);
		register(GsonMessageBodyHandler.class);
		register(AuthenticationFilter.class);
	}
}
