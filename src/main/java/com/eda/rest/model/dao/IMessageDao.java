package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.WatsonNlc;

public interface IMessageDao {

	public String getCollectionName();

	public boolean createMessage(Message1 edaMessage);

	public String getRegKeyForParticularThread(Long thread_ref_id);

	public String getThreadStatus(Long thread_ref_id);

	public boolean updatePathForVoiceFile(Long msg_id, String path);

	public List<Long> getListOfAgents();

	public List<Agent> getFreeAgentList(Long[] picked);

	public List<Message1> listMessage(Long threadId);
	
	public List<Agent> listAgent();

	public boolean createWatsonAnswer(WatsonNlc watson);
	
	public boolean deleteMessage(Long msg_id);
	
	public Long messageCount(Long thread_id);
}
