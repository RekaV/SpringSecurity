package com.eda.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Agent;
import com.eda.rest.model.dao.ILoginDao;

@Service
public class LoginService implements ILoginService{

	@Autowired
	ILoginDao loginDao;
	@Override
	public List<Agent> login(String agent_name, String password) {
		return loginDao.login(agent_name, password);
	}
	@Override
	public boolean changeAgentStatus(Long agent_id, String status) {
	
		return loginDao.changeAgentStatus(agent_id, status);
	}
	

}
