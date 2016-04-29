package com.eda.rest.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.Insurance;
import com.eda.rest.model.Location;
import com.eda.rest.model.dao.IInsuranceDao;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.util.EmailAlert;

@Service
public class InsuranceService implements IInsuranceService{

	private Logger logger = Logger.getLogger(InsuranceService.class);
	@Autowired
	private SequenceDao sequenceDao;
	@Autowired
	private IInsuranceDao insuranceDao;
	@Autowired
	private EmailAlert emailAlert;
	@Override
	public boolean createTravelInsurance(JSONObject jsonObject)  {
		// TODO Auto-generated method stub
		Insurance insurance=new Insurance();
		boolean result=false;
		
		try
		{
			if(jsonObject.get("name").equals("")||jsonObject.get("age").equals("")||jsonObject.get("gender").equals("")||jsonObject.get("mobile_no").equals("")||jsonObject.get("proof_doc").equals(""))
			{
				logger.error("INSURANCE SERVICE::::::there is an empty values");
				//return result;
				
			}else
			{
				int year=Calendar.getInstance().get(Calendar.YEAR);
				Long app_id=sequenceDao.getNextSequence("eda", "insurance_id");
				String application_id=String.valueOf(year)+String.valueOf(app_id); //app_id.toString();
				
				
				insurance.setApplication_no(application_id);
				insurance.setInsurance_plan(jsonObject.getString("insurance_plan"));
				insurance.setName(jsonObject.getString("name"));
				insurance.setAge(jsonObject.getInt("age"));
				insurance.setGender(jsonObject.getString("gender"));
				insurance.setMobile_no(jsonObject.getString("mobile_no"));
				insurance.setEmail(jsonObject.getString("email"));
				insurance.setTrip_details(jsonObject.getString("tripdetails"));
				insurance.setType_of_person(jsonObject.getString("applyfor"));
				insurance.setNo_of_people(jsonObject.getInt("no_of_people"));
				insurance.setJourny_date(jsonObject.get("journy_date"));
				insurance.setReturn_date(jsonObject.get("return_date"));
				insurance.setDirection(jsonObject.getString("direction"));
				insurance.setProof_doc(jsonObject.getString("proof_doc"));
				insurance.setThread_id(jsonObject.getLong("thread"));
				result=insuranceDao.createTravelInsurance(insurance);
				
				emailAlert.sendEmailAlert(jsonObject.getString("email").toString());
			}
						
			
		}catch(JSONException e)
		{
			logger.error("INSURANCE SERVICE::::::"+e);
		}
		return result;
	}
	@Override
	public String getLocation() {
		
		return insuranceDao.getLocation();
	}
	@Override
	public boolean createLocation(JSONObject jsonObject) {
		
		Location location=new Location();
		boolean loc=false;
		try {
			location.setLocation(jsonObject.getString("location"));
			loc=insuranceDao.createLocation(location);
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		return loc;
	}
	public boolean deleteInsurance(String application_no)
	{
		return insuranceDao.deleteInsurance(application_no);
	}
	@Override
	public boolean deleteLocation(String location) {
		
		return insuranceDao.deleteLocation(location);
	}
}

