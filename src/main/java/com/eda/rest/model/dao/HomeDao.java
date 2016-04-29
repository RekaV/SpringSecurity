package com.eda.rest.model.dao;

import org.springframework.stereotype.Repository;

@Repository
public class HomeDao extends AbstractSpringMongoDb implements IHomeDao{

	//MongoOperations mongoOperations=(MongoOperations) mongoTemplate;
	@Override
	public String getName() {

		String name=mongoOperations.getCollectionNames().toString();
		System.out.println("DAO:::::::"+name);
		return name;

	}
	
}
