package com.eda.rest.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.GcmReg;

@Repository
public class ClientDao extends AbstractSpringMongoDb implements IClientDao{

	private Logger logger=Logger.getLogger(ClientDao.class);
	
	//MongoTemplate mongoTemplate;
	@Override
	public boolean updateProfile(String device_id, JSONObject profile) {
		
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("device_id").is(device_id));
			GcmReg gcmReg=mongoOperations.findOne(query, GcmReg.class);
			gcmReg.setProfile(profile);
			mongoOperations.save(gcmReg);
			logger.info("PROFILE UPDATED SUCCESSFULLY............");
			
		} catch (Exception e) {
			logger.error("Client Dao::::::::updateprofile()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return true;
	}

	@Override
	public List<GcmReg> listProfile(String device_id) {
		List<GcmReg> list=new ArrayList<GcmReg>();
		
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("device_id").is(device_id)).fields().include("profile");
			list=mongoOperations.find(query, GcmReg.class);
			logger.info("LIST SIZE PROFILE:::::"+list.size());
			
		} catch (Exception e) {
			logger.error("Client Dao::::::::listProfile()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
		return list;
	
	}

	@Override
	public String GcmRegistrationKeyInsertion(GcmReg gcmReg) {
		
		try {
			
			mongoOperations.insert(gcmReg);
			
		} catch (Exception e) {
			logger.error("Client Dao::::::::GcmRegistrationKeyInsertion()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return "true";
	}

	@Override
	public List<GcmReg> checkGcmAvailability(String device_id, String gcm_reg_key) {
		
		List<GcmReg> listgcm=new ArrayList<GcmReg>();
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("device_id").is(device_id)).fields()
					.include("device_id");
			listgcm = mongoOperations.find(query, GcmReg.class);
			
		} catch (Exception e) {
			logger.error("Client Dao::::::::checkGcmAvailability()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return listgcm;
	}

	@Override
	public void updateGcmRegistrationKey(String device_id, String gcm_reg_key) {
		
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("device_id").is(device_id));
			GcmReg gcmReg = mongoOperations.findOne(query, GcmReg.class);
			gcmReg.setGcm_reg_key(gcm_reg_key);
			gcmReg.setTime_stamp(new Date());
			mongoOperations.save(gcmReg);
			logger.info("MSG GCM REGISTRATION ID  UPDATED");
			
		} catch (Exception e) {
			logger.error("Client Dao::::::::updateGcmRegistrationKey()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		
	}

	@Override
	public boolean updateContact(String deviceId, JSONArray contact) {
		
		try {
			
			Query query=new Query();
			query.addCriteria(Criteria.where("device_id").is(deviceId));
			GcmReg gcmReg=mongoOperations.findOne(query, GcmReg.class);
			gcmReg.setContact(contact);
			logger.info("UPDATE CONTACT::"+contact);
			mongoOperations.save(gcmReg);
		} catch (Exception e) {
			logger.error("Client Dao::::::::updateContact()"+e);
			e.printStackTrace();
		}finally {
			//mongoTemplate.getDb().getMongo().close();
		}
			
		return true;
	}
	public boolean deleteClient(String device_id)
	{
		Query query=new Query();
		query.addCriteria(Criteria.where("device_id").is(device_id));
		mongoOperations.findAndRemove(query, GcmReg.class);
		return true;
		
	}
}
