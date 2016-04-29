package com.eda.rest.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Action;
import com.eda.rest.model.dao.ICalendarDao;


@Service
public class CalendarService extends GoogleService implements ICalendarService{

	private Logger logger = Logger.getLogger(CalendarService.class);
	@Autowired
	private ICalendarDao calanderDao;

	@Override
	public boolean createCalenderAction(JSONObject jsonObj) {
	
		boolean calendar=false;
		try
		{
		Action action=getAction(jsonObj);
		calendar=calanderDao.createCalenderAction(action);

		Long action_id=action.getAction_id();
		Long thread_id=action.getThread_id();
		String msg_content=action.getAction_code()+":"+action_id;
		
		
		sendGcmPushNotification(thread_id, msg_content);
		}
		catch(JSONException | IOException e)
		{
			logger.error("CALENDAR SERVICE-CREATE::"+e);
		}
		
		return calendar;
	}

	@Override
	public String listContent(JSONObject jsonObj) {
	
		String msgContent = "";
		try {
			Long actionid = jsonObj.getLong("actionid");

			List<Action> content = calanderDao.listContent(actionid);

			msgContent = content.listIterator(0).next().getContent();

			logger.info("ACTION DETAILS::::::" + content);
			logger.info("ACTION DETAILS MSG CONTENT::::::" + msgContent);

		} catch (JSONException e) {
			logger.error("CALENDAR SERVICE:::" + e);
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
		update=calanderDao.updateActionStatus(action_id, status, remarks);
		String action="CALENDAR";
		boolean msg=getMessage(thread_id, device_id, status,action);
		logger.info("Message::"+msg);
		}
		catch(JSONException e)
		{
			logger.error("CALENDAR SERVICE UPDATE::"+e);
		}
		return update;
	}
	
		
}
