package com.eda.rest.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.service.IClientService;


@RestController
public class ClientController extends AbstractController{

	@Autowired
	IClientService clientService;
	private Logger logger = Logger.getLogger(ClientController.class);
	
	@RequestMapping(value = "/gcmkey/create/", produces = { "application/json" }, method = RequestMethod.POST)
	public String registerGcmKey(HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException {
		JSONObject jsonGcmDate = readJSONData(request);
		return clientService.GcmRegistrationKeyInsertion(jsonGcmDate);
	}

	@RequestMapping(value = "/contact/update/", method = RequestMethod.POST)
	public boolean updateContact(HttpServletRequest request, String deviceId,
			JSONObject jsoncontact) throws IOException, JSONException {
		jsoncontact = readJSONData(request);

		deviceId = jsoncontact.getString("device_id");
		JSONArray contact = jsoncontact.getJSONArray("all_contacts");

		logger.info("UPDATE CONTACT IN CONTROLLER::::::" + contact);
		clientService.updateContact(jsoncontact);
		return true;
	}
	@RequestMapping(value="/profile/create",method=RequestMethod.POST)
	public String updateProfile(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject jsonProfile=readJSONData(request);
		clientService.updateProfile(jsonProfile);
		return "true";
		
	}
	
	@RequestMapping(value="/profile/list",method=RequestMethod.POST)
	public String listProfile(HttpServletRequest request,HttpServletResponse response) throws JSONException
	{
		JSONObject jsonObject=readJSONData(request);
		String device_id=jsonObject.getString("device_id");
		String profile=clientService.listProfile(device_id);
		return profile;
		
	}
}
