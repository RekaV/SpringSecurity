package com.eda.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.service.IWatsonService;

@RestController
public class WatsonController extends AbstractController{
	private Logger logger=Logger.getLogger(WatsonController.class);
	
	@Autowired
	IWatsonService watsonService;
	
	@RequestMapping(value="/watson/question/",method=RequestMethod.POST)
	public String getClassification(HttpServletRequest request)
	{
		JSONObject questions=readJSONData(request);
		String answer=watsonService.getClassification(questions).toString();
		logger.info("WATSON ANSWER::::::::::::::::::"+answer);
		return answer;
	}
	@RequestMapping(value="/watson/answer/",method=RequestMethod.POST)
	public String getAnswer(HttpServletRequest request) throws JSONException
	{
		JSONObject jsonObj=readJSONData(request);
		Long msg_id=jsonObj.getLong("msg_id");
		return watsonService.getAnswer(msg_id);
	}
	
}
