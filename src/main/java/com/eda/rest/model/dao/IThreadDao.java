package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;

public interface IThreadDao {
	public boolean createNewThread(MsgThread edaThread);

	public boolean updateStatusInThread(Long threadId);

	public String threadStatus(Long thread_id);

	public String getThreadStatus(Long thread_ref_id);

	public void updateStatusInThread(Long agentId, String status);

	public void updateAgentStatus(Long agentId);

	public boolean agentInsert(Agent agent);
	public boolean updateAgent(Long agent_id,String agent_name,String password,String status);

	public List<MsgThread> listThread(Long agentId);

	public List<Message1> listMessage(Long threadId);

	public List<MsgThread> listAllThread(Long agentId);

	public List<MsgThread> getThread(String status);

	public String deviceId(Long thread_id);
	
	public List<Agent> listAgent();
	
	public List<MsgThread> totalNoOfThreads();
	
	public List<MsgThread> totalNoOfUnPickedThreads();
	
	public List<MsgThread> totalNoOfWorkingThreads();
	
	public List<MsgThread> totalNoOfClosedThreads();
	
	public boolean deleteThread(Long thread_id);
	
	public boolean deleteAgent(Long agent_id);
	
	
	
}
