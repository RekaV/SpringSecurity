package com.eda.rest.model.dao;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.ErrorLog;

@Repository
public class LogDao extends AbstractSpringMongoDb implements ILogDao {
	private Logger logger = Logger.getLogger(LogDao.class);
	
	MongoTemplate mongoTemplate;

	@Override
	public boolean createLog(ErrorLog errorLog) {
		boolean result=false;
		try {
			
			mongoOperations.insert(errorLog);
			logger.info("ERROR LOG FROM MOBILE CREATED");
			result=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//mongoTemplate.getDb().getMongo().close();
		}

		return result;
	}
	@Override
	public boolean deletelog(String model) {
		Query query=new Query();
		query.addCriteria(Criteria.where("model").is(model));
		mongoOperations.findAndRemove(query, ErrorLog.class);
		return true;
	}

}
