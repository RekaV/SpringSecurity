package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Agent;

@Repository
public class LoginDao extends AbstractSpringMongoDb implements ILoginDao{

	private Logger logger=Logger.getLogger(LoginDao.class);
	
	
	@Override
	public List<Agent> login(String agent_name, String password) {
		
		List<Agent> agentList=new ArrayList<Agent>();
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("agent_name").is(agent_name).andOperator(Criteria.where("password").is(password)));
			agentList=mongoOperations.find(query, Agent.class);
			
		} catch (Exception e) {
			logger.error("LoginDao::::::::login()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return agentList;
	}


	@Override
	public boolean changeAgentStatus(Long agent_id, String status) {
		try{
			Query query=new Query();
			query.addCriteria(Criteria.where("agent_id").is(agent_id));
			Agent agent=mongoOperations.findOne(query, Agent.class);
			agent.setStatus(status);
			mongoOperations.save(agent);
		}catch(Exception e)
		{
			
		}finally {
			
		}
		return true;
	}

}
