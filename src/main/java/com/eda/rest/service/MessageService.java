package com.eda.rest.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;
import com.eda.rest.model.dao.IMessageDao;
import com.eda.rest.model.dao.SequenceDao;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;


@Service
public class MessageService  implements IMessageService {
	private Logger logger = Logger.getLogger(MessageService.class);
	@Autowired
	IMessageDao messageDao;
	@Autowired
	SequenceDao sequenceDao;
	@Autowired
	IThreadService threadService;


	
	@Override
	public String getCollectionName() {
		
		return messageDao.getCollectionName();
	}
	@Override
	public List<Agent> listAgent() {
		
		return messageDao.listAgent();
	}
	
	@Override
	public boolean createMessage(Message1 edaMessage) {
		return messageDao.createMessage(edaMessage);
	}
	@Override
	public String getRegKeyForParticularThread(Long thread_ref_id) {
		return messageDao.getRegKeyForParticularThread(thread_ref_id);
	}

	@Override
	public boolean updatePathForVoiceFile(Long msg_id, String path) {
		return messageDao.updatePathForVoiceFile(msg_id, path);
	}

	@Override
	public List<Message1> listMessage(Long threadId) {
		return messageDao.listMessage(threadId);
	}
	
	@Override
	public JSONObject createMessage(JSONObject jsonMessage) {
		JSONObject returnToApp = new JSONObject();
		NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		service.setUsernameAndPassword("c4a4fcc1-2d95-47b4-9c7b-840d60d6b3ae", "lyyo1g1NDJP8");
		
		//List<ClassifiedClass> list=new ArrayList<>();
		
		Message1 Message = new Message1();
		try
		{
			String device_id = jsonMessage.getString("device_id");
			String msg_content = jsonMessage.getString("msg_content");
			String direction = jsonMessage.getString("direction");
			Long thread_ref_id = jsonMessage.getLong("thread_ref_id");
			
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
			Message.setNlc_classification("");
			
			MsgThread edaThread = new MsgThread();
			edaThread.setAgent_id(0L);
			edaThread.setStatus("NP");
			edaThread.setDevice_id(device_id);
			edaThread.setStart_time(new Date());
			edaThread.setEnd_time(new Date());

			String status = threadService.threadStatus(thread_ref_id);

			if (((jsonMessage.getLong("thread_ref_id") == 0L || status.equals("C")
					|| status.equals("")) && (jsonMessage.getString("direction").equals("I")))) {

				thread_ref_id = sequenceDao.getNextSequence("eda", "thread_id");

				Message.setThread_ref_id(thread_ref_id);

				edaThread.setThread_id(thread_ref_id);
				threadService.createNewThread(edaThread);

				//AutoAllocationJob.autoAllocationForAgentToThread();
			}
			if(direction.equals("I"))
			{
			/*Classification classification = service.classify("5E00F7x2-nlc-749",
					msg_content);*/
			//0235B6x12-nlc-407
			Classification classification = service.classify("0235B6x12-nlc-407",
						msg_content);
			logger.info("WATSON ANSWER:::::"+classification);
			Message.setNlc_classification(classification.getClasses().toString());
		
			logger.info("WATSON ANSWER:::::"+classification.getClasses().toString());
			System.out.println("CLASSIFICATION::::::"+classification);
			}
			messageDao.createMessage(Message);

			returnToApp.put("msg_id", Message.getMsg_id());
			returnToApp.put("thread_ref_id", Message.getThread_ref_id());
			returnToApp.put("msg_content", msg_content);
			returnToApp.put("direction",direction);
			logger.info("RETURN TO APP::::" + returnToApp);

			/*if(direction.equals("I"))
			{
			WatsonNlc watson=new WatsonNlc();

			watson.setAnswer_id(sequenceDao.getNextSequence("eda", "answer_id"));
			watson.setMsg_id(Message.getMsg_id());
			watson.setThread_id(thread_ref_id);
			//watson.setAgent_id(agent_id);
			
			Classification classification = service.classify("5E00F7x2-nlc-749",
					msg_content);
			logger.info("WATSON ANSWER:::::"+classification);
			watson.setAnswer(classification.getTopClass());
			
			boolean insertwatson=messageDao.createWatsonAnswer(watson);
			logger.info("WATSON ANSWER:::"+insertwatson);
			//System.out.println("CLASSIFICATION::::::"+classification);
			}*/
		}catch(JSONException | UnsupportedEncodingException  e)
		{
			logger.error("MESSAGE SERVICE:::::::::createMessage()"+e);
			
		}
		return returnToApp;

		
	}
	@Override
	public List<Long> getListOfAgent() {
		
		return messageDao.getListOfAgents();
	}
	@Override
	public boolean deleteMessage(Long msg_id) {
		
		return messageDao.deleteMessage(msg_id);
	}
	@Override
	public Long messageCount(Long thread_id) {

		return messageDao.messageCount(thread_id);
	}

	
}
