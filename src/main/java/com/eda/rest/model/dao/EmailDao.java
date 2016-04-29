package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Action;
import com.eda.rest.model.GcmReg;

@Repository
public class EmailDao extends AbstractSpringMongoDb implements IEmailDao{

	private Logger logger=Logger.getLogger(EmailDao.class);
	
	
	MongoTemplate mongoTemplate;
	@Override
	public boolean createEmailAction(Action action) {
		
		try {
			
			mongoOperations.insert(action);
			logger.info("Successfully email action inserted");
		} catch (Exception e) {
			logger.error("EmailDao:::::::::::createEmailAction()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return true;
	}

	@Override
	public List<Action> listContent(Long action_id) {
		
		List<Action> listContent=new ArrayList<Action>();
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("action_id").is(action_id)).fields().include("content");
			listContent=mongoOperations.find(query, Action.class);
		} catch (Exception e) {
			logger.error("EmailDao:::::::::::listContent()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return listContent;
	}

	@Override
	public boolean updateActionStatus(Long action_id, String status, String remarks) {
		
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("action_id").is(action_id));
			Action action=mongoOperations.findOne(query, Action.class);
		
			
			if(action != null)
			{
				action.setStatus(status);
				action.setRemarks(remarks);
			}
			logger.info("UPDATE EMAIL ACTION STATUS");
			mongoOperations.save(action);
		} catch (Exception e) {
			logger.error("EmailDao:::::::::::updateActionStatus()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return true;
	}

	@Override
	public List<GcmReg> listEmail(String device_id) {
		
		List<GcmReg> list=new ArrayList<GcmReg>();
		try {
			
			
			Query query=new Query();
			query.addCriteria(Criteria.where("device_id").is(device_id)).fields().include("contact");
			list=mongoOperations.find(query, GcmReg.class);
		} catch (Exception e) {
			logger.error("EmailDao:::::::::::listEmail()"+e);
			e.printStackTrace();
		}
		finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return list;
	}

	@Override
	public List<GcmReg> listPhone(String device_id) {
		
		List<GcmReg> list=new ArrayList<GcmReg>();
		try {
			
			
			Query query=new Query();
			query.addCriteria(Criteria.where("device_id").is(device_id)).fields().include("contact");
			list=mongoOperations.find(query, GcmReg.class);
		} catch (Exception e) {
			logger.error("EmailDao:::::::::::listPhone()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return list;
	}

	@Override
	public boolean deleteEmailAction(Long action_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("action_id").is(action_id));
		mongoOperations.findAndRemove(query, Action.class);
		return true;
	}

}
