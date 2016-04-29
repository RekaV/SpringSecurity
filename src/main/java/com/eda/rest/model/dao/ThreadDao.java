package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;

@Repository
public class ThreadDao extends AbstractSpringMongoDb implements IThreadDao{

	private Logger logger=Logger.getLogger(ThreadDao.class);
	
	
	@Override
	public boolean createNewThread(MsgThread edaThread) {
		boolean result=false;
		try {
			mongoOperations.insert(edaThread);
			result=true;
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::createNewThread()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return result;
		
		
	}

	@Override
	public boolean updateStatusInThread(Long threadId) {
		
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("thread_id").is(threadId));
			MsgThread msgThread = mongoOperations.findOne(query, MsgThread.class);
			msgThread.setStatus("C");
			msgThread.setEnd_time(new Date());
			mongoOperations.save(msgThread);
			logger.info("MSG THREAD ID STATUS UPDATED");
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::updateStatusInThread()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return true;
	}

	@Override
	public String threadStatus(Long thread_id) {
		
		String sta=null;
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("thread_id").is(thread_id)).fields()
					.include("status");
			List<MsgThread> status = mongoOperations.find(query, MsgThread.class);

			if (status.size() == 0)
			{
				sta="";
			}
				
			else
			{
				sta=status.get(0).getStatus().toString();
			}
				
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::threadStatus()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
		//	mongoTemplate.getDb().getMongo().close();
		}
			
		
		return sta;
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
			logger.error("Thread Dao:::::::::getThreadStatus()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return status;
	}

	@Override
	public void updateStatusInThread(Long agentId, String status) {
		
		try {
			
			Query query = new Query();
			query = new Query(Criteria.where("status").is(status));
			MsgThread eda_Thread = mongoOperations.findOne(query, MsgThread.class);
			if (eda_Thread != null) {
				eda_Thread.setAgent_id(agentId);
				eda_Thread.setStatus("P");
				mongoOperations.save(eda_Thread);
				logger.info("UPDATED");
			} else {
				logger.info("There is no free threads");
			
			}
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::updateStatusInThread()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
	}

	@Override
	public void updateAgentStatus(Long agentId) {
		
		try {
			
			Query query=new Query();
			String status="";
			
			query.addCriteria(Criteria.where("agent_id").is(agentId));
			Agent agent=mongoOperations.findOne(query, Agent.class);
			
			if(agent !=null)
			{
			 status=agent.getStatus();
			}
			if(status.equals("A"))
			{
				agent.setStatus("P");
			}else
			{
				agent.setStatus("A");
			}
			mongoOperations.save(agent);
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::updateAgentStatus()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
		//	mongoTemplate.getDb().getMongo().close();
		}
			
		
	}

	@Override
	public boolean agentInsert(Agent agent) {
		
		try {
			mongoOperations.insert(agent);
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::agentInsert()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return true;
	}
	
	@Override
	public boolean updateAgent(Long agent_id, String agent_name, String password, String status) {
		
		Query query=new Query();
		query.addCriteria(Criteria.where("agent_id").is(agent_id));
		Agent agent=mongoOperations.findOne(query, Agent.class);
		//logger.info(message);
		if(!agent.equals(null))
		{
			agent.setAgent_id(agent_id);
			agent.setAgent_name(agent_name);
			agent.setPassword(password);
			agent.setStatus(status);
			mongoOperations.save(agent);
			logger.info("Successfully agent  updated......");
			return true;
		}
		else{
		return false;
		}
	}


	@Override
	public List<MsgThread> listThread(Long agentId) {
		
		List<MsgThread> list=new ArrayList<MsgThread>();
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("agent_id").is(agentId).andOperator(Criteria.where("status").is("P"))).fields().include("thread_id");
			list=mongoOperations.find(query, MsgThread.class);
		
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::listThread()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return list;
	}

	@Override
	public List<Message1> listMessage(Long threadId) {
		
		List<Message1> msg=new ArrayList<Message1>();
		try {
			
			Query query=new Query();
			query.with(new Sort(Sort.Direction.ASC, "msg_id"));
			query.addCriteria(Criteria.where("thread_ref_id").is(threadId)).fields().include("msg_content").include("direction").include("msg_id").include("time_stamp").include("path");
			msg=mongoOperations.find(query, Message1.class);
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::listMessage()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return msg;
	}

	@Override
	public List<MsgThread> listAllThread(Long agentId) {
		
		List<MsgThread> list_all_thread_id=new ArrayList<MsgThread>();
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("agent_id").ne(agentId).andOperator(Criteria.where("status").ne("C"))).fields().include("thread_id");
			list_all_thread_id=mongoOperations.find(query, MsgThread.class);
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::listAllThread()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return list_all_thread_id;
	}

	@Override
	public List<MsgThread> getThread(String status) {
		
		List<MsgThread> edaThread=new ArrayList<MsgThread>();
		try {
			
			Query query=new Query();
			
			query.addCriteria(Criteria.where("status").is(status)).fields().include("agent_id");
			edaThread = mongoOperations
					.find(query, MsgThread.class);
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::getThread()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return edaThread;
	}

	@Override
	public String deviceId(Long thread_id) {
		
		try {
			
			Query query=new Query();
			if(thread_id !=0)
			{
			query.addCriteria(Criteria.where("thread_id").is(thread_id)).fields().include("device_id");
			String device_id=mongoOperations.findOne(query, MsgThread.class).getDevice_id();
			return device_id;
			}
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::deviceId()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return null;
	}

	@Override
	public List<Agent> listAgent() {
		
		List<Agent> listAgent=new ArrayList<Agent>();
		try {
			
			listAgent=mongoOperations.findAll(Agent.class);
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::listAgent()"+e);
			e.printStackTrace();
		}
		finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return listAgent;
	}

	@Override
	public List<MsgThread> totalNoOfThreads() {
		List<MsgThread> listThread=mongoOperations.findAll(MsgThread.class);
		return listThread;
	}

	@Override
	public List<MsgThread> totalNoOfUnPickedThreads() {
		Query query=new Query();
		query.addCriteria(Criteria.where("status").is("NP"));
		List<MsgThread> listThread=mongoOperations.find(query, MsgThread.class);
		return listThread;
	}

	@Override
	public List<MsgThread> totalNoOfWorkingThreads() {
		
		Query query=new Query();
		query.addCriteria(Criteria.where("status").is("P"));
		List<MsgThread> listThread=mongoOperations.find(query, MsgThread.class);
		return listThread;
	}

	@Override
	public List<MsgThread> totalNoOfClosedThreads() {
		Query query=new Query();
		query.addCriteria(Criteria.where("status").is("C"));
		List<MsgThread> listThread=mongoOperations.find(query, MsgThread.class);
		return listThread;
	}

	@Override
	public boolean deleteThread(Long thread_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("thread_id").is(thread_id));
		mongoOperations.findAndRemove(query, MsgThread.class);
		return true;
	}

	@Override
	public boolean deleteAgent(Long agent_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("agent_id").is(agent_id));
		mongoOperations.findAndRemove(query, Agent.class);
		return true;
	}

}
