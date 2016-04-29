package com.eda.rest.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Action;
import com.eda.rest.model.dao.INotesDao;

@Service
public class NotesService extends GoogleService implements INotesService{
	private Logger logger = Logger.getLogger(NotesService.class);
	@Autowired
	INotesDao notesDao;
	/*@Override
	public boolean createNotesAction(Action action) {
		return notesDao.createNotesAction(action);
	}
	@Override
	public List<Action> listContent(Long action_id) {
		return notesDao.listContent(action_id);
	}

	@Override
	public boolean updateActionStatus(Long action_id, String status, String remarks) {
		return notesDao.updateActionStatus(action_id, status, remarks);
	}
*/

	@Override
	public String createNotesAction(JSONObject jsonObject)  {
		// TODO Auto-generated method stub
		String content="";
		try
		{
		Action action=getAction(jsonObject);
			
		notesDao.createNotesAction(action);

		Long action_id=action.getAction_id();
		Long thread_id=action.getThread_id();
		String msg_content=action.getAction_code()+":"+action_id;
		JSONObject jsonContent=jsonObject.getJSONObject("content");
		
		content=jsonContent.toString();
		sendGcmPushNotification(thread_id, msg_content);
		}
		catch(JSONException | IOException e)
		{
			logger.error("NOTES SERVICE-CREATE:::"+e);
		}
		
			return content;
		
		
	}

	@Override
	public String listContent(JSONObject jsonObject) {
		
		String msgContent="";
		try
		{
		Long actionid=jsonObject.getLong("actionid");
		List<Action> content=notesDao.listContent(actionid);
		 msgContent=content.listIterator(0).next().getContent();
		logger.info("ACTION DETAILS::::::"+content);
		logger.info("ACTION DETAILS MSG CONTENT::::::"+msgContent);
		}catch(JSONException e)
		{
			logger.error("NOTES SERVICE:::"+e);
		}
		return msgContent;
	
	}

	@Override
	public boolean updateActionStatus(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		try
		{
		Long action_id=jsonObject.getLong("action_id");
		String status=jsonObject.getString("status");
		String remarks=jsonObject.getString("remarks");
		Long thread_id=jsonObject.getLong("thread_ref_id");
		String device_id=jsonObject.getString("device_id");
		/*Long thread_id=jsonAction.getLong("thread_id");
		String device_id=jsonAction.getString("device_id");
		
		Message1 msg=getMessage(thread_id, device_id, status);
		messageService.createMessage(msg);*/

		notesDao.updateActionStatus(action_id, status, remarks);
		String action="NOTES";
		boolean msg=getMessage(thread_id, device_id, status,action);
		logger.info("Message::"+msg);
		}
		catch(JSONException e)
		{
			logger.error("NOTES SERVICE:::"+e);
		}
		return true;
	}
	
}
