package com.eda.rest.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Action;
import com.eda.rest.model.dao.ISmsDao;

@Service
public class SmsService extends GoogleService implements ISmsService
{
	private Logger logger = Logger.getLogger(SmsService.class);

	
	@Autowired
	ISmsDao smsDao;

	@Override
	public boolean createSmsAction(JSONObject jsonObj) {
		// TODO Auto-generated method stub
		boolean sms=false;
		try
		{
		Action action=getAction(jsonObj);
		 sms=smsDao.createSmsAction(action);

		Long action_id=action.getAction_id();
		Long thread_id=action.getThread_id();
		String msg_content=action.getAction_code()+":"+action_id;
		
		
		sendGcmPushNotification(thread_id, msg_content);
		}
		catch(JSONException | IOException e)
		{
			logger.error("SMS SERVICE-CREATE::"+e);
		}
		
		return sms;
	}

	@Override
	public String listContent(JSONObject jsonObj) {
		
		String msgContent = "";
		try {
			Long actionid = jsonObj.getLong("actionid");

			List<Action> content = smsDao.listContent(actionid);

			msgContent = content.listIterator(0).next().getContent();

			logger.info("ACTION DETAILS::::::" + content);
			logger.info("ACTION DETAILS MSG CONTENT::::::" + msgContent);

		} catch (JSONException e) {
			logger.error("SMS SERVICE:::" + e);
		}
		return msgContent;
		
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
		
		update=smsDao.updateActionStatus(action_id, status, remarks);
		logger.info("updateActionStatus::::::"+update);
		String action="SMS";
		boolean msg=getMessage(thread_id, device_id, status,action);
		logger.info("Message::"+msg);
		
		}
		catch(JSONException e)
		{
			logger.error("SMS SERVICE UPDATE::"+e);
		}
		return update;
	}

	@Override
	public boolean deleteSmsAction(Long action_id) {
		
		return smsDao.deleteSmsAction(action_id);
	}

}
