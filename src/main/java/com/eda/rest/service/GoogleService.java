package com.eda.rest.service;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.eda.rest.model.Action;
import com.eda.rest.model.Message1;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.service.IMessageService;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GoogleService {

	 @Autowired
	 private SequenceDao sequenceDao;
	
	 @Autowired
	 private IMessageService messageService;
	
	private Logger logger = Logger.getLogger(GoogleService.class);
	
	public String sendGcmPushNotification(Long thread_ref_id, String msg_content) throws IOException
	{
		//old Key
		//AIzaSyA4Jg_Bz9wUD1NXwMTgWk7zROBAvfcAsiE
		
		//new Key
		//AIzaSyA7rJdnqe11JsSKKt_XR790VDbC6B5Qd0o
		
		final String GOOGLE_SERVER_KEY = "AIzaSyA4Jg_Bz9wUD1NXwMTgWk7zROBAvfcAsiE";
		final String MESSAGE_KEY = "message";
		Result result = null;
		logger.info("THREAD REF ID:::::" + thread_ref_id);

			/*logger.error("ERROR:::::"
					+ messageService.getRegKeyForParticularThread(thread_ref_id));
	*/	
			String regId = messageService
					.getRegKeyForParticularThread(thread_ref_id);

			String userMessage = msg_content;// "TEST MESSAGE FROM WEB SERVICE";
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			
			Message message = new Message.Builder().timeToLive(30)
					.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
			logger.info("regId: " + regId);
			logger.info("reg_id:"+regId);
			result = sender.send(message, regId, 1);
			logger.info("result::::"+result);
			logger.info("result=" + result);
			logger.error("ERROR:::::"
					+ result);
		return result.toString();
		
	}
	
	protected Action getAction(JSONObject jsonAction) throws JSONException
	{
		Action action=new Action();
		JSONObject content=jsonAction.getJSONObject("content");
		action.setAction_id(sequenceDao.getNextSequence("eda", "action_id"));
		action.setAction_code(jsonAction.getString("action_code"));
		action.setContent(content.toString());
		action.setThread_id(jsonAction.getLong("thread_id"));
		action.setStatus(jsonAction.getString("status"));
		action.setRemarks(jsonAction.getString("remarks"));
		
		return action;
	}
	
	public boolean getMessage(Long thread_id,String device_id,String status,String action)
	{
		Message1 Message = new Message1();
		Message.setMsg_id(sequenceDao.getNextSequence("eda", "msg_id"));
		Message.setSender_id(1L);
		Message.setDevice_id(device_id);
		Message.setMsg_content(action +" created successfully");
		Message.setOrigin_ip("");
		Message.setTime_stamp(new Date());
		Message.setThread_ref_id(thread_id);
		Message.setParent_id(1L);
		Message.setAgent_id(1L);
		Message.setDirection("S");
		Message.setPath("path");
		
		boolean result=messageService.createMessage(Message);
		
		return result;
	}
}
