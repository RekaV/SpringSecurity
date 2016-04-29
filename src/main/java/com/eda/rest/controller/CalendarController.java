package com.eda.rest.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.service.ICalendarService;


@RestController
public class CalendarController extends AbstractController {
	
	//private Logger logger = Logger.getLogger(CalendarController.class);


	@Autowired
	private ICalendarService calendarService;
	
	
	
	
	@RequestMapping(value = "/cal/send/", method = RequestMethod.POST)
	public String eventSend(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		JSONObject actionidJson=readJSONData(request);
		return calendarService.listContent(actionidJson);
	}
	
	@RequestMapping(value = "/cal/create/", method = RequestMethod.POST)
	public boolean receiveCalendarContent(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		JSONObject receivedJson=readJSONData(request);
		return calendarService.createCalenderAction(receivedJson);
		
	}
	@RequestMapping(value="/cal/updatestatus/",method=RequestMethod.POST)
	public boolean updateActionStatus(Long action_id,HttpServletRequest request) throws IOException, JSONException
	{
		JSONObject jsonAction=readJSONData(request);
		return calendarService.updateActionStatus(jsonAction);
	}
	
}
