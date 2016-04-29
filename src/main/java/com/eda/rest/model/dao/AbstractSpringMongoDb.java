package com.eda.rest.model.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import com.eda.rest.config.SpringMongoConfiguration;

public class AbstractSpringMongoDb {
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(
			SpringMongoConfiguration.class);
	//public MongoTemplate mongoTemplate=(MongoTemplate) appContext.getBean("mongoTemplate");
	public static MongoOperations mongoOperations=(MongoOperations) appContext.getBean("mongoTemplate");
}


