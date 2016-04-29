package com.eda.rest.model.mapper;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class InsuranceMapper {
	public static JSONObject jsonInsurance() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		
		jsonData.put("application_id",1L);
		jsonData.put("insurance_plan", "GOLD");
		jsonData.put("name", "Reka");
		jsonData.put("age", "23");
		jsonData.put("gender", "F");
		jsonData.put("mobile_no", "9965142586");
		jsonData.put("email","reka.reka02@gmail.com");
		jsonData.put("tripdetails", "Local");
		jsonData.put("applyfor", "Individual");
		jsonData.put("no_of_people", "1");
		jsonData.put("journy_date", new Date());
		jsonData.put("return_date", new Date());
		jsonData.put("direction", "one way");
		jsonData.put("proof_doc", "passport");
		jsonData.put("thread", 1L);
		return jsonData;
	}
	public static JSONObject jsonLocation() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("location", "saravanampatti");
		return jsonData;
	}
	/*insurance.setApplication_no(application_id);
	insurance.setInsurance_plan(jsonObject.getString("insurance_plan"));
	insurance.setName(jsonObject.getString("name"));
	insurance.setAge(jsonObject.getInt("age"));
	insurance.setGender(jsonObject.getString("gender"));
	insurance.setMobile_no(jsonObject.getInt("mobile_no"));
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
*/}
