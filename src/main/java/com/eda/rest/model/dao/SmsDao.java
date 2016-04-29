package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Action;

@Repository
public class SmsDao extends AbstractSpringMongoDb implements ISmsDao{

	private Logger logger=Logger.getLogger(SmsDao.class);
	
	MongoTemplate mongoTemplate;
	@Override
	public boolean createSmsAction(Action action) {
	
		try {
			
			mongoOperations.insert(action);
			logger.info("Successfully sms action inserted");
			
		} catch (Exception e) {
			logger.error("Sms Dao:::::::::::::::::::createSmsAction()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
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
			return listContent;
		} catch (Exception e) {
			logger.error("Sms Dao:::::::::::::::::::listContent()"+e);
			e.printStackTrace();
		}finally {
			//System.gc();
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
			logger.info("UPDATE SMS ACTION STATUS");
			mongoOperations.save(action);
			
		} catch (Exception e) {
			logger.error("Sms Dao:::::::::::::::::::updateActionStatus()"+e);
			e.printStackTrace();
		}
		finally {
			//System.gc();
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return true;
	}

	@Override
	public boolean deleteSmsAction(Long action_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("action_id").is(action_id));
		mongoOperations.findAndRemove(query, Action.class);
		return true;
	}

}
