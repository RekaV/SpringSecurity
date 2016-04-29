package com.eda.rest.model.dao;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Sequence;

@Service
public class SequenceDao {

	private Logger logger=Logger.getLogger(SequenceDao.class);
	@Autowired
	public MongoOperations mongoOperations;
	


	public Long getNextSequence(String collectionName, String fieldName) {
		
		//MongoTemplate mongoTemplate;
		try {
		/*	mongoTemplate = abstractSpringMongoDb.mongoTemplate();
			MongoOperations mongoOperations=(MongoOperations) mongoTemplate;*/
			
			if (fieldName.equals("msg_id")) {
				Sequence counter = mongoOperations.findAndModify(
						query(where("_id").is(collectionName)),
						new Update().inc("msg_seq", 1), options().returnNew(true),
						Sequence.class);
				return counter.getMsg_seq();
			} else if (fieldName.equals("thread_id")) {
				Sequence counter = mongoOperations.findAndModify(
						query(where("_id").is(collectionName)),
						new Update().inc("thread_seq", 1), options().returnNew(true),
						Sequence.class);
				return counter.getThread_seq();
			}else if (fieldName.equals("action_id")) {
				Sequence counter = mongoOperations.findAndModify(
						query(where("_id").is(collectionName)),
						new Update().inc("action_seq", 1), options().returnNew(true),
						Sequence.class);
				return counter.getAction_seq();
			}else if(fieldName.equals("insurance_id")){
				Sequence counter = mongoOperations.findAndModify(
						query(where("_id").is(collectionName)),
						new Update().inc("insurance_seq", 1), options().returnNew(true),
						Sequence.class);
				return counter.getInsurance_seq();
			}else if(fieldName.equals("agent_id")){
				Sequence counter = mongoOperations.findAndModify(
						query(where("_id").is(collectionName)),
						new Update().inc("agent_seq", 1), options().returnNew(true),
						Sequence.class);
				return counter.getAgent_seq();
			}else if(fieldName.equals("answer_id")){
				Sequence counter = mongoOperations.findAndModify(
						query(where("_id").is(collectionName)),
						new Update().inc("answer_seq", 1), options().returnNew(true),
						Sequence.class);
				return counter.getAnswer_seq();
			}
			
		} catch (Exception e) {
			logger.error("Sequnece Dao:::getNextsequence"+e);
		}finally {
			//System.gc();
		}
			
	
		return (long) 0;
	}}
