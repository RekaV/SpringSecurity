package com.eda.rest.model.dao;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.Insurance;
import com.eda.rest.model.Location;

@Repository
public class InsuranceDao extends AbstractSpringMongoDb implements IInsuranceDao {

	private Logger logger = Logger.getLogger(Insurance.class);
	
	@Override
	public boolean createTravelInsurance(Insurance insurance) {

	
		try {
			
			mongoOperations.insert(insurance);
			logger.info("ISURANCE DETAILS ARE CREATED...");
		} catch (Exception e) {
			logger.error("InsuranceDao:::::::::+createTravelInsurance()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		

		return true;
	}

	@Override
	public String getLocation() {
		
		String location=null;
		try {
			
			location=mongoOperations.findAll(Location.class).get(0).getLocation();
		} catch (Exception e) {
			logger.error("InsuranceDao:::::::::+getLocation()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return location;
	}

	@Override
	public boolean createLocation(Location location) {
	
		try {
			
			mongoOperations.insert(location);
		} catch (Exception e) {
			logger.error("InsuranceDao:::::::::+createLocation()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return true;
	}

	@Override
	public boolean deleteInsurance(String application_no) {
		Query query=new Query();
		query.addCriteria(Criteria.where("application_no").is(application_no));
		mongoOperations.findAndRemove(query, Insurance.class);
		return true;
	}

	@Override
	public boolean deleteLocation(String location) {
		Query query=new Query();
		query.addCriteria(Criteria.where("location").is(location));
		mongoOperations.findAndRemove(query, Location.class);
		return true;
	}

}
