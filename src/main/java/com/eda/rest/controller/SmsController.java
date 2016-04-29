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

import com.eda.rest.service.ISmsService;

@RestController
public class SmsController extends AbstractController{
	
	@Autowired
	ISmsService smsService;
	@RequestMapping(value = "/sms/send/", method = RequestMethod.POST)
	public String emailSend(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		
		JSONObject actionidJson=readJSONData(request);
		return smsService.listContent(actionidJson);
	}
	
	@RequestMapping(value = "/sms/create/", method = RequestMethod.POST)
	public boolean receiveMailContent(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		JSONObject receivedJson=readJSONData(request);
		return smsService.createSmsAction(receivedJson);
		
	}
	
	
	@RequestMapping(value="/sms/updatestatus/",method=RequestMethod.POST)
	public boolean updateActionStatus(Long action_id,HttpServletRequest request) throws IOException, JSONException
	{
		JSONObject jsonAction=readJSONData(request);
		return smsService.updateActionStatus(jsonAction);
	}
}
