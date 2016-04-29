package com.eda.rest.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Action;
import com.eda.rest.model.dao.IContactDao;

@Service
public class ContactService extends GoogleService implements IContactService{

	private Logger logger = Logger.getLogger(ContactService.class);
	
	@Autowired
	IContactDao contactDao;
	
	@Override
	public boolean createContactAction(JSONObject jsonObject) {
		boolean contact=false;
		try
		{
		Action action=getAction(jsonObject);
		contact=contactDao.createContactAction(action);

		Long action_id=action.getAction_id();
		Long thread_id=action.getThread_id();
		String msg_content=action.getAction_code()+":"+action_id;
		
		
		sendGcmPushNotification(thread_id, msg_content);
		}
		catch(JSONException | IOException e)
		{
			logger.error("CONTACT SERVICE-CREATE::"+e);
		}
		
		return contact;
	}

	@Override
	public String listContent(JSONObject jsonObject) {
		String msgContent = "";
		try {
			Long actionid = jsonObject.getLong("actionid");

			List<Action> content = contactDao.listContent(actionid);

			msgContent = content.listIterator(0).next().getContent();

			logger.info("ACTION DETAILS::::::" + content);
			logger.info("ACTION DETAILS MSG CONTENT::::::" + msgContent);

		} catch (JSONException e) {
			logger.error("CONTACT SERVICE:::" + e);
		}
		return msgContent;
	}

	@Override
	public boolean updateActionStatus(JSONObject jsonObject) {
		boolean update=false;
		try
		{
		Long action_id=jsonObject.getLong("action_id");
		String status=jsonObject.getString("status");
		String remarks=jsonObject.getString("remarks");
		Long thread_id=jsonObject.getLong("thread_ref_id");
		String device_id=jsonObject.getString("device_id");
		
		update=contactDao.updateActionStatus(action_id, status, remarks);
		logger.info("updateActionStatus::::::"+update);
		String action="NEW CONTACT";
		boolean msg=getMessage(thread_id, device_id, status,action);
		logger.info("Message::"+msg);
		}
		catch(JSONException e)
		{
			logger.error("CONTACT SERVICE UPDATE::"+e);
		}
		return update;
	}

	@Override
	public boolean deleteContactAction(Long action_id) {
		
		return contactDao.deleteContactAction(action_id);
	}

}
