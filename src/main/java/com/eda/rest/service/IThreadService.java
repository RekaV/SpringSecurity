package com.eda.rest.service;

import java.util.List;

import org.json.JSONObject;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;

public interface IThreadService {

	public boolean createNewThread(MsgThread edaThread);

	public boolean updateStatusInThread(Long threadId);

	public String threadStatus(Long thread_id);

	/* public String getThreadStatus(Long thread_ref_id); */
	public String getThreadStatus(JSONObject jsonObject);

	public void updateStatusInThread(Long agentId, String status);

	public String updateAgentStatus(JSONObject jsonObject);

	public boolean agentInsert(JSONObject jsonObject);

	public boolean updateAgent(JSONObject jsonAgent);

	public List<MsgThread> listThread(JSONObject jsonObject);

	public List<Message1> listMessage(Long threadId);

	public List<MsgThread> listAllThread(JSONObject jsonAgentId);

	public String deviceId(Long thread_id);

	public List<Agent> listAgent();

	public int totalNoOfThreads();

	public int totalNoOfUnPickedThreads();

	public int totalNoOfWorkingThreads();

	public int totalNoOfClosedThreads();

	public boolean deleteThread(Long thread_id);

	public boolean deleteAgent(Long agent_id);
}
