package com.eda.rest.service;

import java.util.List;

import com.eda.rest.model.Agent;

public interface ILoginService {
	public List<Agent> login(String agent_name, String password);
	public boolean changeAgentStatus(Long agent_id,String status);
}
