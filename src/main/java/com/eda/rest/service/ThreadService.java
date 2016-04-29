package com.eda.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;
import com.eda.rest.model.dao.IThreadDao;
import com.eda.rest.model.dao.SequenceDao;


@Service
public class ThreadService implements IThreadService {

	@Autowired
	IThreadDao threadDao;
	
	@Autowired
	SequenceDao sequenceDao;
	private Logger logger=Logger.getLogger(ThreadService.class);
	@Override
	public boolean createNewThread(MsgThread edaThread) {
		return threadDao.createNewThread(edaThread);

	}

	@Override
	public boolean updateStatusInThread(Long threadId) {
		return threadDao.updateStatusInThread(threadId);
	}

	@Override
	public String threadStatus(Long thread_id) {
		return threadDao.threadStatus(thread_id);
	}

	/*
	 * @Override public String getThreadStatus(Long thread_ref_id) { return
	 * threadDao.getThreadStatus(thread_ref_id); }
	 */
	@Override
	public String getThreadStatus(JSONObject jsonObject) {

		JSONObject thread_status = new JSONObject();
		try {
			String thread_id = jsonObject.getString("thread_ref_id");
			Long thread_ref_id = Long.parseLong(thread_id);
			String status = threadDao.getThreadStatus(thread_ref_id);
			if (status.equals("C") || status.equals("")) {
				thread_status.put("status", "C");
			} else {
				thread_status.put("status", "O");
			}
		} catch (JSONException e) {
			
		}

		return thread_status.toString();
	// return threadDao.getThreadStatus(thread_ref_id);
	}

	@Override
	public void updateStatusInThread(Long agentId, String status) {
		threadDao.updateStatusInThread(agentId, status);
	}

	@Override
	public String updateAgentStatus(JSONObject jsonObject) {
		try
		{
			Long agentId=jsonObject.getLong("agent_id");
			threadDao.updateAgentStatus(agentId);
		} catch (JSONException e) {

		}
		return "update";
	}

	
	@Override
	public boolean agentInsert(JSONObject jsonObject) {
		boolean result=false;
		try {
			Agent agent = new Agent();
			agent.setAgent_name(jsonObject.getString("agent_name"));
			agent.setAgent_id(sequenceDao.getNextSequence("eda", "agent_id"));//jsonObject.getLong("agent_id"));//sequenceDao.getNextSequence("eda", "agent_id"));
			agent.setPassword(jsonObject.getString("password"));
			agent.setStatus(jsonObject.getString("status"));
			result=threadDao.agentInsert(agent);
		} catch (JSONException e) {

		}
		return result;
	}

	@Override
	public boolean updateAgent(JSONObject jsonAgent) {
		
		boolean result=false;
		try {
			Long agent_id = jsonAgent.getLong("agent_id");
			String agent_name=jsonAgent.getString("agent_name");
			String password=jsonAgent.getString("password");
			String status=jsonAgent.getString("status");
			logger.info("JSON AGENT"+jsonAgent);
			result=threadDao.updateAgent(agent_id, agent_name, password, status);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	@Override
	public List<MsgThread> listThread(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		Long agent_id = null;
		try
		{
			agent_id = jsonObject.getLong("agent_id");
			
		}catch(JSONException e)
		{
			
		}
		return threadDao.listThread(agent_id);
		
		
	}

	@Override
	public List<Message1> listMessage(Long threadId) {
		// TODO Auto-generated method stub
		return threadDao.listMessage(threadId);
	}

	@Override
	public List<MsgThread> listAllThread(JSONObject jsonAgentId) {
		// TODO Auto-generated method stub
		List<MsgThread> list=new ArrayList<MsgThread>();
		try
		{
			Long agent_id = jsonAgentId.getLong("agent_id");
			list=threadDao.listAllThread(agent_id);
		}catch(JSONException e)
		{
			
		}
		
		return list;
	}

	@Override
	public String deviceId(Long thread_id) {
		
		return threadDao.deviceId(thread_id);
	}

	@Override
	public List<Agent> listAgent() {

		return threadDao.listAgent();
	}

	@Override
	public int totalNoOfThreads() {
		int size=threadDao.totalNoOfThreads().size();
		return size;
	}

	@Override
	public int totalNoOfUnPickedThreads() {
		int size=threadDao.totalNoOfUnPickedThreads().size();
		return size;
	}

	@Override
	public int totalNoOfWorkingThreads() {
		int size=threadDao.totalNoOfWorkingThreads().size();
		return size;
	}

	@Override
	public int totalNoOfClosedThreads() {
		int size=threadDao.totalNoOfClosedThreads().size();
		return size;
	}

	@Override
	public boolean deleteThread(Long thread_id) {
		
		return threadDao.deleteThread(thread_id);
	}

	@Override
	public boolean deleteAgent(Long agent_id) {
	
		return threadDao.deleteAgent(agent_id);
	}

}
