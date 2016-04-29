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

import com.eda.rest.service.ILogService;

@RestController
public class LogController extends AbstractController{

	@Autowired
	private ILogService logService;
	
	@RequestMapping(value="/errorlog/store/",method=RequestMethod.POST)
	public String errorLog(HttpServletRequest request,HttpServletResponse response) throws  JSONException, IOException
	{
		JSONObject jsonError=readJSONData(request);
		logService.createLog(jsonError);
		return "true";
	}
}
