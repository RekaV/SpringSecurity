package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Chatbot;

@Repository
public class ChatbotDao extends AbstractSpringMongoDb implements IChatbotDao {

	private Logger logger=Logger.getLogger(Chatbot.class);
	
	
	@Override
	public boolean createEvent(Chatbot chatBot) {
		
		try {
			mongoOperations.insert(chatBot);
		} catch (Exception e) {
			logger.error("ChatBotDao::::createEvent()"+e);
			e.printStackTrace();
		}finally {
		//	mongoTemplate.getDb().getMongo().close();
		}
			
		
		return true;
	}

	@Override
	public List<Chatbot> listQuestions(String event_key) {
		
		List<Chatbot> listQuestions=new ArrayList<Chatbot>();
		try {
			

			Query query=new Query();
			query.addCriteria(Criteria.where("event_key").is(event_key)).fields().include("predefined_questions");
			listQuestions=mongoOperations.find(query, Chatbot.class);
		} catch (Exception e) {
			logger.error("ChatBotDao::::listQuestions()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return listQuestions;
	}

	@Override
	public boolean deleteEvent(String event_key) {
		Query query=new Query();
		query.addCriteria(Criteria.where("event_key").is(event_key));
		mongoOperations.findAndRemove(query, Chatbot.class);
		return true;
	}

}
