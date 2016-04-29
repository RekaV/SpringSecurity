package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Agent;
import com.eda.rest.model.GcmReg;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;
import com.eda.rest.model.WatsonNlc;

@Repository
public class MessageDao extends AbstractSpringMongoDb implements IMessageDao {

	private Logger logger = Logger.getLogger(MessageDao.class);
	

	
	@Override
	public String getCollectionName() {
		
		String name = null;
		try {
			
			name = mongoOperations.getCollectionNames().toString();
		} catch (Exception e) {
			logger.error("MessageDao:::::::getcollectionName()" + e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			

		return name;
	}

	@Override
	public boolean createMessage(Message1 edaMessage) {
		boolean result=false;
		try {
			
			mongoOperations.insert(edaMessage);
			logger.info("SUCCESSFULLY INSERTED");
			result=true;
		} catch (Exception e) {
			logger.error("MessageDao:::::::createMessage()" + e);
			e.printStackTrace();
		}finally
		{
			//mongoTemplate.getDb().getMongo().close();
		}
		return result;
	}

	@Override
	public String getRegKeyForParticularThread(Long thread_ref_id) {
		
		String regkey = "";
		try {
			
			Query query = new Query();
			String device = "";
			query.addCriteria(Criteria.where("thread_id").is(thread_ref_id))
					.fields().include("device_id");
			List<MsgThread> device_id = mongoOperations
					.find(query, MsgThread.class);

			for (int i = 0; i < device_id.size(); i++) {
				device = device_id.get(i).getDevice_id().toString();
			}
			query = new Query(Criteria.where("device_id").is(device));

			List<GcmReg> reg_key = mongoOperations.find(query, GcmReg.class);
			logger.info("DEVICE ID:::" + device);
			
			for (int i = 0; i < reg_key.size(); i++) {
				regkey = reg_key.get(i).getGcm_reg_key().toString();
			}
		} catch (Exception e) {
			logger.error("MessageDao:::::::getRegKeyForParticularThread()" + e);
			e.printStackTrace();
		}finally{
			//mongoTemplate.getDb().getMongo().close();
		}
		return regkey;
	}

	@Override
	public String getThreadStatus(Long thread_ref_id) {
		
		String status = "";
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("thread_id").is(thread_ref_id))
					.fields().include("status");
			List<MsgThread> thread_status = mongoOperations.find(query,
					MsgThread.class);
			
			if (thread_status.size() != 0) {

				status = thread_status.get(0).getStatus().toString();
			}

			
		} catch (Exception e) {
			logger.error("MessageDao:::::::getThreadStatus()" + e);
			e.printStackTrace();
		}finally
		{
			//mongoTemplate.getDb().getMongo().close();
		}
		return status;
	}

	@Override
	public boolean updatePathForVoiceFile(Long msg_id, String path) {
		
		boolean result=false;
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("msg_id").is(msg_id));
			Message1 msg = mongoOperations.findOne(query, Message1.class);
			msg.setPath(path);
			mongoOperations.save(msg);
			result=true;
		} catch (Exception e) {
			logger.error("MessageDao:::::::updatePathForVoiceFile()" + e);
			e.printStackTrace();
		}finally{
			//mongoTemplate.getDb().getMongo().close();
		}
		return result;
	}

	@Override
	public List<Long> getListOfAgents() {
		
		List<Long> agent1 = new ArrayList<Long>();
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("status").is("A"));//.fields().include("agent_id");
			List<Agent> agent = mongoOperations.find(query, Agent.class);
			
			for (int i = 0; i < agent.size(); i++) {
				agent1.add(agent.get(i).getAgent_id());
			}
		} catch (Exception e) {
			logger.error("MessageDao:::::::getListOfAgents()" + e);
			e.printStackTrace();
		}finally
		{
			//mongoTemplate.getDb().getMongo().close();
		}
		return agent1;
	}

	@Override
	public List<Agent> getFreeAgentList(Long[] picked) {
		
		List<Agent> thread = new ArrayList<Agent>();
		try {
			
			for (int i = 0; i < picked.length; i++) {
				Query query = new Query();
				query = new Query(Criteria.where("agent_id").in(picked[i]));// ne(picked[i]));
				thread.addAll(mongoOperations.find(query, Agent.class));
			}
			logger.error("ERROR::" + thread);
		} catch (Exception e) {
			logger.error("MessageDao:::::::getFreeAgentList()" + e);
			e.printStackTrace();
		}finally
		{
			//mongoTemplate.getDb().getMongo().close();
		}
		return thread;
	}

	@Override
	public List<Message1> listMessage(Long threadId) {
		
		List<Message1> msg=new ArrayList<Message1>();
		try {
			
			Query query = new Query();
			query.with(new Sort(Sort.Direction.ASC, "msg_id"));
			query.addCriteria(Criteria.where("thread_ref_id").is(threadId))
					.fields().include("msg_content").include("direction")
					.include("msg_id").include("time_stamp").include("path").include("nlc_classification");
			msg = mongoOperations.find(query, Message1.class);
			//System.out.println("WATSON MSG:::::::"+msg.get(0).getWatson_answer());
		} catch (Exception e) {
			logger.error("MessageDao:::::::listMessage()" + e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
		return msg;
	}

	@Override
	public List<Agent> listAgent() {
		List<Agent> listAgent=new ArrayList<Agent>();
		
		try {
			
			listAgent=mongoOperations.findAll(Agent.class);
		} catch (Exception e) {
			
			logger.error("MessageDao::::::::listAgent()"+e);
		}finally
		{
			//mongoTemplate.getDb().getMongo().close();
		}
		
		
		return listAgent;
	}

	@Override
	public boolean createWatsonAnswer(WatsonNlc watson) {
		boolean val=false;
		try
		{
			mongoOperations.insert(watson);
			val=true;
			logger.info("MessageDao:::::::createWatsonAnswer successfully");
			
		}catch(Exception e)
		{
			logger.error("MessageDao:::::::createWatsonAnswer"+e);
		}
		return val;
	}

	@Override
	public boolean deleteMessage(Long msg_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("msg_id").is(msg_id));
		mongoOperations.findAllAndRemove(query, Message1.class);
		return true;
	}

	@Override
	public Long messageCount(Long thread_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("thread_ref_id").is(thread_id));
		Long count=mongoOperations.count(query, Message1.class);
		logger.info("MESSAGE DAO--COUNT():::"+count);
		return count;
	}

}
