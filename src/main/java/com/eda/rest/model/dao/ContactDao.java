package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Action;

@Repository
public class ContactDao extends AbstractSpringMongoDb implements IContactDao {

	private Logger logger = Logger.getLogger(ContactDao.class);

	// MongoTemplate mongoTemplate;

	@Override
	public boolean createContactAction(Action action) {

		try {

			mongoOperations.insert(action);
			logger.info("Successfully contact action inserted");
			return true;
		} catch (Exception e) {
			logger.error("ContactDao:::::::::createContactAction()" + e);
			e.printStackTrace();
		} finally {
			// mongoTemplate.getDb().getMongo().close();
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
			return listContent;

		} catch (Exception e) {
			logger.error("ContactDao:::::::::listContent()" + e);
			e.printStackTrace();
		} finally {
			// mongoTemplate.getDb().getMongo().close();
		}

		return listContent;
	}

	@Override
	public boolean updateActionStatus(Long action_id, String status, String remarks) {

		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("action_id").is(action_id));
			Action action = mongoOperations.findOne(query, Action.class);

			if (action != null) {
				action.setStatus(status);
				action.setRemarks(remarks);
			}
			logger.info("UPDATE CONTACT ACTION STATUS");
			mongoOperations.save(action);
		} catch (Exception e) {
			logger.error("ContactDao:::::::::updateActionStatus()" + e);
			e.printStackTrace();
		} finally {
			// mongoTemplate.getDb().getMongo().close();
		}

		return true;
	}

	@Override
	public boolean deleteContactAction(Long action_id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("action_id").is(action_id));
		mongoOperations.findAndRemove(query, Action.class);
		return true;
	}
}
