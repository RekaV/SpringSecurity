package com.eda.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.model.Agent;
import com.eda.rest.service.ILoginService;

@RestController
public class LoginController extends AbstractController{

	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	ILoginService loginService;
	
	@RequestMapping(value="/agent/login/",method=RequestMethod.POST)
	public Long login(HttpServletRequest request,String agent_name,String password,Long agent_id) throws IOException, JSONException
	{
		JSONObject jsonLogin=readJSONData(request);
		String status=jsonLogin.getString("status");
		List<Agent> agentList=loginService.login(jsonLogin.getString("agent_name"),jsonLogin.getString("password"));
		if(agentList.size() !=0 )
		{
			agent_id=agentList.get(0).getAgent_id();	
			boolean changeStatus=loginService.changeAgentStatus(agent_id, status);
			logger.info("LOGIN CONTROLLER::Change status::"+changeStatus);
		}
		logger.info("LOGIN CONTROLLER::::"+agent_id);
		
		return agent_id;
	}
	
	@RequestMapping(value="/agent/logout/",method=RequestMethod.POST)
	public boolean logout(HttpServletRequest request){
		JSONObject jsonLogout=readJSONData(request);
		Long agent_id=jsonLogout.getLong("agent_id");
		String status=jsonLogout.getString("status");
		return loginService.changeAgentStatus(agent_id, status);
		
	}
	
	
}
