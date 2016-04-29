package com.eda.rest.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Action;
import com.eda.rest.model.GcmReg;
import com.eda.rest.model.dao.IEmailDao;
import com.eda.rest.util.EmailAlert;

@Service
public class EmailService extends GoogleService implements IEmailService {

	private Logger logger = Logger.getLogger(EmailService.class);

	@Autowired
	IEmailDao emailDao;
	
	@Autowired
	EmailAlert emailAlert;

	@Override
	public String listContent(JSONObject jsonObj) {
		
		String msgContent = "";
		try {
			Long actionid = jsonObj.getLong("actionid");

			List<Action> content = emailDao.listContent(actionid);

			msgContent = content.listIterator(0).next().getContent();

			logger.info("ACTION DETAILS::::::" + content);
			logger.info("ACTION DETAILS MSG CONTENT::::::" + msgContent);

		} catch (JSONException e) {
			logger.error("EMAIL SERVICE:::" + e);
		}
		return msgContent;
	}

	
	@Override
	public boolean createEmailAction(JSONObject jsonObj){
		boolean email=false;
		try
		{
		Action action=getAction(jsonObj);
		 email=emailDao.createEmailAction(action);

		Long action_id=action.getAction_id();
		Long thread_id=action.getThread_id();
		String msg_content=action.getAction_code()+":"+action_id;
		
		
		sendGcmPushNotification(thread_id, msg_content);
		}
		catch(JSONException | IOException e)
		{
			logger.error("EMAIL SERVICE-CREATE::"+e);
		}
		
		return email;
	}

	@Override
	public boolean updateActionStatus(JSONObject jsonObj) {
		
		boolean update=false;
		try
		{
		Long action_id=jsonObj.getLong("action_id");
		String status=jsonObj.getString("status");
		String remarks=jsonObj.getString("remarks");
		Long thread_id=jsonObj.getLong("thread_ref_id");
		String device_id=jsonObj.getString("device_id");
		
		update=emailDao.updateActionStatus(action_id, status, remarks);
		logger.info("updateActionStatus::::::"+update);
		String action="EMAIL";
		boolean msg=getMessage(thread_id, device_id, status,action);
		logger.info("Message::"+msg);
		}
		catch(JSONException e)
		{
			logger.error("EMAIL SERVICE UPDATE::"+e);
		}
		return update;
	}


	@Override
	public List<GcmReg> listEmail(String device_id) {
		// TODO Auto-generated method stub
		return emailDao.listEmail(device_id);
	}


	@Override
	public List<GcmReg> listPhone(String device_id) {
		// TODO Auto-generated method stub
		return emailDao.listPhone(device_id);
	}


	@Override
	public void sendEmailDirectly(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		try {
			JSONObject json=(JSONObject) jsonObject.get("content");
			String to=json.getString("to");
			
			String subject=json.getString("subject");
			String msg=json.getString("message");
			
			emailAlert.sendFloralAlert(to, subject, msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public boolean deleteEmailAction(Long action_id) {
		
		return emailDao.deleteEmailAction(action_id);
	}

}

