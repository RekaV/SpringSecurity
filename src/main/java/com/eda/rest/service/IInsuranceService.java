package com.eda.rest.service;

import org.json.JSONObject;
public interface IInsuranceService {
	public boolean createTravelInsurance(JSONObject jsonObject); 
	public String getLocation();
	public boolean createLocation(JSONObject jsonObject);
	public boolean deleteInsurance(String application_no);
	public boolean deleteLocation(String location);
	
}
