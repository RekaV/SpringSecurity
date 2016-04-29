package com.eda.rest.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.eda.rest.model.Agent;
import com.eda.rest.model.MsgThread;
import com.eda.rest.model.dao.AbstractSpringMongoDb;



public class AutoAllocationJob extends AbstractSpringMongoDb implements Job{
	
	private static Logger logger = Logger.getLogger(AutoAllocationJob.class);

	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		System.out.println("SCHEDULER START>>>>>>>>>>>>>>>");
		String name=mongoOperations.getCollectionNames().toString();
		System.out.println("JOB::::::::::"+name);
		
		autoAllocationForAgentToThread();
	}

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
		}finally {
		//System.gc();
			//mongoTemplate.getDb().getMongo().close();
			
		}
		return agent1;
	}
	public List<MsgThread> getThread(String status) {
		
		List<MsgThread> edaThread=new ArrayList<MsgThread>();
		try {
			
			
			Query query=new Query();
			
			query.addCriteria(Criteria.where("status").is(status));//.fields().include("agent_id");
			edaThread = mongoOperations
					.find(query, MsgThread.class);
			
		} catch (Exception e) {
			logger.error("Thread Dao:::::::::getThread()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
		
		return edaThread;
	}
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
			//mongoTemplate.getDb().getMongo().close();
			
		}
		
	}
	public void autoAllocationForAgentToThread() {
		List<Long> agentList = getListOfAgents();
		List<MsgThread> pickedList1 = getThread("P");
		
		List<Long> pickedList = new ArrayList<Long>();
		List<Long> freeAgentList = new ArrayList<Long>();
		
		Random r=new Random();
		try {
			for (int i = 0; i < pickedList1.size(); i++) {
				pickedList.add(pickedList1.get(i).getAgent_id());
			}
			
			logger.info("agentList:::"+agentList);
			logger.info("pickedList:::"+pickedList);
			
			
			
			
			for(int i=0;i<agentList.size();i++){
				logger.info("loop="+i);
			
				logger.info("agentList.get(i)="+agentList.get(i));
	        
				boolean present=false;
				for(int j=0;j<pickedList.size();j++){
					if(agentList.get(i)==pickedList.get(j)){
						logger.info("pickedList.get(j)="+pickedList.get(j));
			
						present=true;
					}
				}
				logger.info("present="+present);
			
				if(!present){
					logger.info("not present, agent id="+agentList.get(i));
					freeAgentList.add(agentList.get(i));
				}
			}
			logger.info("FREE AGENT LIST:::"+freeAgentList);
			
			if(freeAgentList.size()!=0){
				
				int random=r.nextInt(freeAgentList.size());
				logger.info("random="+random);
				logger.info("random="+random);
			
				logger.info("freeAgentList.get(random)="+freeAgentList.get(random));
				updateStatusInThread(freeAgentList.get(random), "NP");
			}

		} finally {
			//mongoTemplate.getDb().getMongo().close();
			agentList.clear();
			pickedList.clear();
			pickedList1.clear();
			freeAgentList.clear();
			
		}
				
	}


	
	
}
