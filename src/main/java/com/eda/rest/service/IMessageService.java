package com.eda.rest.service;

import java.util.List;

import org.json.JSONObject;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;

public interface IMessageService {

	public String getCollectionName();
	public List<Agent> listAgent();

	public boolean createMessage(Message1 message);

	public String getRegKeyForParticularThread(Long thread_ref_id);

	public boolean updatePathForVoiceFile(Long msg_id, String path);

	public List<Message1> listMessage(Long threadId);
	
	public JSONObject createMessage(JSONObject jsonMessage);
	
	public List<Long> getListOfAgent();
	
	public boolean deleteMessage(Long msg_id);
	
	public Long messageCount(Long thread_id);

}
