package com.eda.rest.service;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Chatbot;
import com.eda.rest.model.dao.IChatbotDao;

@Service
public class ChatbotService extends GoogleService implements IChatbotService{

	private Logger logger = Logger.getLogger(ChatbotService.class);

	
	@Autowired
	IChatbotDao chatBotDao;
	@Autowired
	IMessageService messageService;
	@Override
	public boolean createEvent(JSONObject jsonEvent) {
		Chatbot chatBot=new Chatbot();
		boolean result=false;
		try {
			chatBot.setEvent_name(jsonEvent.getString("event_name"));
			chatBot.setEvent_key(jsonEvent.getString("event_key"));
			chatBot.setPredefined_questions(jsonEvent.getJSONObject("predefined_questions"));
			result=chatBotDao.createEvent(chatBot);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public String listQuestion(JSONObject jsonList) {
		JSONObject jsonList1=new JSONObject();
		String result="";
		//String msg="";
		try {
			String event_key=jsonList.getString("event_key");
			String device_id=jsonList.getString("device_id");
			Long thread_id=jsonList.getLong("thread_id");
			
			List<Chatbot> listQuestions=chatBotDao.listQuestions(event_key);
			logger.info("QUESTION SIZE:::::::::"+listQuestions.size());
			jsonList1=listQuestions.get(0).getPredefined_questions();
			for(int i=1;i<=jsonList1.length();i++)
			{
				//msg +=jsonList1.getString(String.valueOf(i));
				JSONObject jsonMsg=new JSONObject();
				jsonMsg.put("device_id", device_id);
				jsonMsg.put("msg_content", jsonList1.getString(String.valueOf(i)));
				jsonMsg.put("direction", "O");
				jsonMsg.put("thread_ref_id", thread_id);
				jsonMsg.put("sender_id", 1L);
				jsonMsg.put("origin_ip", "ip");
				jsonMsg.put("time_stamp", new Date());
				jsonMsg.put("parent_id", 1L);
				jsonMsg.put("agent_id", 1L);
				jsonMsg.put("path", "path");
				messageService.createMessage(jsonMsg);
				
				result +=jsonList1.getString(String.valueOf(i));
				result +="@@@";
			}
			logger.info("RESULT:::::::::::::::::"+result);
			//sendGcmPushNotification(thread_id, jsonList1.toString());
			
			/*String device_id = jsonMessage.getString("device_id");
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
			Message.setPath("path");*/
			
			
		} catch (JSONException  e) {
			
			e.printStackTrace();
		}

		
		return result;
	}
	@Override
	public boolean deleteEvent(String event_key) {
		// TODO Auto-generated method stub
		return chatBotDao.deleteEvent(event_key);
	}

}
