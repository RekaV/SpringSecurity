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

import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.service.IContactService;

@RestController
public class ContactController extends AbstractController{
	@Autowired
	SequenceDao sequenceDaoImpl;
	@Autowired
	IContactService contactService;
	
	@RequestMapping(value = "/new/contact/send/", method = RequestMethod.POST)
	public String contactSend(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		
		JSONObject actionidJson=readJSONData(request);
		return contactService.listContent(actionidJson);
	}
	
	@RequestMapping(value = "/new/contact/create/", method = RequestMethod.POST)
	public boolean receiveContactContent(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		JSONObject receivedJson=readJSONData(request);
		return contactService.createContactAction(receivedJson);
	}
	@RequestMapping(value="/new/contact/updatestatus/",method=RequestMethod.POST)
	public boolean updateActionStatus(Long action_id,HttpServletRequest request) throws IOException, JSONException
	{
		JSONObject jsonAction=readJSONData(request);
		return contactService.updateActionStatus(jsonAction);
	}
	
}
