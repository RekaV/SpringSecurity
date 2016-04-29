package com.eda.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.eda.rest.service.IMessageService;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public abstract class AbstractController {

	
	 @Autowired
	 private IMessageService messageService;
	
	private static Logger logger = Logger.getLogger(AbstractController.class);
	
	static JSONObject readJSONData(HttpServletRequest request)
	{
		
		String output = "";
		JSONObject jsonMessage=new JSONObject();
		try{
			logger.info("EXECUTED");
			// String clientOrigin = request.getHeader("origin");

			InputStream in = request.getInputStream(); // Get the data in the entity
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = br.readLine()) != null)
				output = output + line;

			logger.info("MOBILE DATA:::" + output);
			jsonMessage = new JSONObject(output);
			
		}catch(JSONException | IOException e)
		{
			logger.error("READ FROM JSON OBJECT::::::::::"+e);
		}
		return jsonMessage;
	}
	
	public Result sendGcmPushNotification(Long thread_ref_id, String msg_content) throws IOException
			 {
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

			result = sender.send(message, regId, 1);
			logger.info("result=" + result);
			logger.error("ERROR:::::"
					+ result);
		return result;
		
	}
	
/*	Message1 getMessage(Long thread_id,String device_id,String status)
	{
		Message1 Message = new Message1();
		Message.setMsg_id(sequenceDao.getNextSequence("eda", "msg_id"));
		Message.setSender_id(1L);
		Message.setDevice_id(device_id);
		Message.setMsg_content(status);
		Message.setOrigin_ip("");
		Message.setTime_stamp(new Date());
		Message.setThread_ref_id(thread_id);
		Message.setParent_id(1L);
		Message.setAgent_id(1L);
		Message.setDirection("I");
		Message.setPath("path");
		
		return Message;
	}
*/
	}
