package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.Agent;

public interface ILoginDao {
	public List<Agent> login(String agent_name,String password);
	public boolean changeAgentStatus(Long agent_id,String status);
}
