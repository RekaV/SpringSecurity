package com.eda.rest.model.mapper;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.eda.rest.model.MsgThread;

public class ThreadMapper {
	
public static MsgThread buildThread()
{
	MsgThread msg_thread=new MsgThread();
	msg_thread.setAgent_id(0L);
	msg_thread.setStatus("NP");
	msg_thread.setDevice_id("st655509o");
	msg_thread.setStart_time(new Date());
	msg_thread.setEnd_time(new Date());
	return msg_thread;
	
}
public static JSONObject jsonAgent() throws JSONException
{
	JSONObject jsonAgent=new JSONObject();
	jsonAgent.put("agent_id", 1L);
	jsonAgent.put("agent_name", "Alex");
	jsonAgent.put("password", "123");
	jsonAgent.put("status", "A");
	return jsonAgent;
}
}
