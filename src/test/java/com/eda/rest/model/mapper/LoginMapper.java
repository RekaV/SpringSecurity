package com.eda.rest.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.eda.rest.model.Agent;

public class LoginMapper {
	private String id;
	private Long agent_id;
	private String agent_name;
	private String password;
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static JSONObject jsonLogin() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("agent_id", 1L);
		jsonData.put("agent_name", "Alex");
		jsonData.put("password", "123");
		jsonData.put("status","A");
		return jsonData;
	}
	public static Agent buildAgent() throws JSONException
	{
		JSONObject jsonContent=jsonLogin();
		
		
		Agent agent=new Agent();
		agent.setAgent_id(jsonContent.getLong("agent_id"));
		agent.setAgent_name(jsonContent.getString("agent_name"));
		agent.setPassword(jsonContent.getString("password"));
		agent.setStatus(jsonContent.getString("status"));
		
    	List<Agent> list=new ArrayList<Agent>();
    	list.add(agent);
    	
		return agent;
		
	}

}
