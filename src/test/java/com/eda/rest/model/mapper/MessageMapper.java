package com.eda.rest.model.mapper;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.eda.rest.model.Message1;

public class MessageMapper {
/*	String device_id = jsonMessage.getString("device_id");
	String msg_content = jsonMessage.getString("msg_content");
	String direction = jsonMessage.getString("direction");
	Long thread_ref_id = jsonMessage.getLong("thread_ref_id");
	Message1 Message = new Message1();
	Message.setMsg_id(sequenceDao.getNextSequence("eda", "msg_id"));
	Message.setSender_id(1L);
	Message.setDevice_id(jsonMessage.getString("device_id"));
	Message.setMsg_content(jsonMessage.getString("msg_content"));
	Message.setOrigin_ip(jsonMessage.getString("origin_ip"));
	Message.setTime_stamp(new Date());
	Message.setThread_ref_id(thread_ref_id);
	Message.setParent_id(1L);
	Message.setAgent_id(1L);
	Message.setDirection(jsonMessage.getString("direction"));
	Message.setPath("path");

	MsgThread edaThread = new MsgThread();
	edaThread.setAgent_id(0L);
	edaThread.setStatus("NP");
	edaThread.setDevice_id(device_id);
	edaThread.setStart_time(new Date());
	edaThread.setEnd_time(new Date());
*/
	public static JSONObject jsonMessage() throws JSONException
	{
		JSONObject jsonMessage=new JSONObject();
		jsonMessage.put("msg_id",1L);
		jsonMessage.put("sender_id",1L);
		jsonMessage.put("device_id","s2345ko54");
		jsonMessage.put("msg_content","Hello Test MEssage");
		jsonMessage.put("origin_ip","10.100.4.128");
		jsonMessage.put("time_stamp",new Date());
		jsonMessage.put("thread_ref_id",1L);
		jsonMessage.put("parent_id",1L);
		jsonMessage.put("agent_id",1L);
		jsonMessage.put("direction","I");
		jsonMessage.put("path","path");
		
		return jsonMessage;
		
	}
	public static JSONObject jsonOut() throws JSONException
	{
		JSONObject jsonMessage=new JSONObject();
		jsonMessage.put("msg_id",1L);
		jsonMessage.put("direction","O");
		jsonMessage.put("msg_content","Hello Test MEssage");
		jsonMessage.put("thread_ref_id",1L);
		
				
		return jsonMessage;
		
	}
	
	public static Message1 buildMessage() throws JSONException
	{
		JSONObject jsonMessage=new JSONObject();
		jsonMessage.put("msg_id",1L);
		jsonMessage.put("sender_id",1L);
		jsonMessage.put("device_id","s2345ko54");
		jsonMessage.put("msg_content","Hello Test MEssage");
		jsonMessage.put("origin_ip","10.100.4.128");
		jsonMessage.put("time_stamp",new Date());
		jsonMessage.put("thread_ref_id",1L);
		jsonMessage.put("parent_id",1L);
		jsonMessage.put("agent_id",1L);
		jsonMessage.put("direction","I");
		jsonMessage.put("path","path");
		
		
		Long thread_ref_id = jsonMessage.getLong("thread_ref_id");
		Message1 Message = new Message1();
		Message.setMsg_id(jsonMessage.getLong("msg_id"));
		Message.setSender_id(1L);
		Message.setDevice_id(jsonMessage.getString("device_id"));
		Message.setMsg_content(jsonMessage.getString("msg_content"));
		Message.setOrigin_ip(jsonMessage.getString("origin_ip"));
		Message.setTime_stamp(new Date());
		Message.setThread_ref_id(thread_ref_id);
		Message.setParent_id(1L);
		Message.setAgent_id(1L);
		Message.setDirection(jsonMessage.getString("direction"));
		Message.setPath("path");
		
		return Message;

	}
	
}
