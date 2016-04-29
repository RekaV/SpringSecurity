package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Action;

@Repository
public class CalendarDao extends AbstractSpringMongoDb implements ICalendarDao {

	private Logger logger = Logger.getLogger(CalendarDao.class);


	
	@Override
	public boolean createCalenderAction(Action action) {
		
		try {
			
			mongoOperations.insert(action);
		} catch (Exception e) {

			logger.error("calendarDao-createCalenderAction()::::" + e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			

		return true;
	}

	@Override
	public List<Action> listContent(Long action_id) {
		
		List<Action> listContent = new ArrayList<Action>();
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("action_id").is(action_id)).fields().include("content");
			listContent = mongoOperations.find(query, Action.class);
			//return listContent;
			
		} catch (Exception e) {

			logger.error("calendarDao-createCalenderAction()::::" + e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
			//listContent.clear();
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
			logger.info("UPDATE CALENDAR ACTION STATUS");
			mongoOperations.save(action);
		} catch (Exception e) {

			logger.error("calendarDao-updateActionStatus()::::" + e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			

		return true;
	}

}
