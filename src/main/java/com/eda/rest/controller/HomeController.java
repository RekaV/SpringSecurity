package com.eda.rest.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eda.rest.service.IHomeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	private Logger logger=Logger.getLogger(HomeController.class);
	@Autowired
	IHomeService homeService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/s", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		logger.info("Welcome home! The client locale is {}."+locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		String name=homeService.getName();
		System.out.println("DATE::::::::::"+formattedDate+"NAME:::::::::::::::"+name);
		return "home";
	}
	
}
