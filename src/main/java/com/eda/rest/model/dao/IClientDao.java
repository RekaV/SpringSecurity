package com.eda.rest.model.dao;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eda.rest.model.GcmReg;

public interface IClientDao {
	public boolean updateProfile(String device_id, JSONObject profile);

	public List<GcmReg> listProfile(String device_id);

	public String GcmRegistrationKeyInsertion(GcmReg gcmReg);

	public List<GcmReg> checkGcmAvailability(String device_id, String gcm_reg_key);

	public void updateGcmRegistrationKey(String device_id, String gcm_reg_key);

	public boolean updateContact(String deviceId, JSONArray contact);

	public boolean deleteClient(String device_id);
}
