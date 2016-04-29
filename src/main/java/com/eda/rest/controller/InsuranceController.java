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

import com.eda.rest.service.IInsuranceService;

@RestController
public class InsuranceController extends AbstractController{

	@Autowired
	IInsuranceService insuranceService;
	
	@RequestMapping(value="/travel/insurance/create",method=RequestMethod.POST)
	public boolean createTravelInsurance(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		JSONObject jsonObj=readJSONData(request);
		return insuranceService.createTravelInsurance(jsonObj);
	}
	
	@RequestMapping(value="/location/get/",method=RequestMethod.POST)
	public String getLocation() throws JSONException
	{
		String loc=insuranceService.getLocation();
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("location", loc);
		return loc;
	}
	@RequestMapping(value="/location/create/",method=RequestMethod.POST)
	public void createLocation(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject loc=readJSONData(request);
		insuranceService.createLocation(loc);
	}
}


