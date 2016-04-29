package com.eda.rest.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.GcmReg;
import com.eda.rest.model.dao.IClientDao;

@Service
public class ClientService implements IClientService {

	@Autowired
	IClientDao clientDao;

	private Logger logger = Logger.getLogger(ClientService.class);

	@Override
	public boolean updateProfile(JSONObject jsonObject) {

		boolean profile = false;
		try {
			String device_id = jsonObject.getString("device_id");
			JSONObject jsonProfile = new JSONObject();
			jsonProfile.put("first_name", jsonObject.getString("first_name"));
			jsonProfile.put("last_name", jsonObject.getString("last_name"));
			jsonProfile.put("address", jsonObject.getString("address"));
			jsonProfile.put("mobile_number",
					jsonObject.getString("mobile_number"));
			jsonProfile.put("email_id", jsonObject.getString("email_id"));
			jsonProfile.put("anniversary", jsonObject.getString("anniversary"));

			profile = clientDao.updateProfile(device_id, jsonProfile);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return profile;
	}

	@Override
	public String listProfile(String device_id) {

		List<GcmReg> list = clientDao.listProfile(device_id);
		JSONObject profile = new JSONObject();

		try {
			if(list.size()!=0)
			{
			if (list.get(0).getProfile().length() != 0) {
				profile.put("first_name",
						list.get(0).getProfile().getString("first_name")
								.toString());
				profile.put("last_name",
						list.get(0).getProfile().getString("last_name")
								.toString());
				profile.put("address",
						list.get(0).getProfile().getString("address")
								.toString());
				profile.put("mobile_number", list.get(0).getProfile()
						.getString("mobile_number").toString());
				profile.put("email_id",
						list.get(0).getProfile().getString("email_id")
								.toString());
				profile.put("anniversary",
						list.get(0).getProfile().getString("anniversary")
								.toString());

			} else {
				profile.put("first_name", "");
				profile.put("last_name", "");
				profile.put("address", "");
				profile.put("mobile_number", "");
				profile.put("email_id", "");
				profile.put("anniversary", "");

			}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return profile.toString();
	}

	@Override
	public String GcmRegistrationKeyInsertion(JSONObject jsonObject) {

		String device_id = null;
		String gcm_reg_key = null;

		GcmReg gcmReg = new GcmReg();

		JSONArray jsonContact = new JSONArray();
		JSONObject jsonProfile = new JSONObject();

		try {
			device_id = jsonObject.getString("device_id");
			gcm_reg_key = jsonObject.getString("gcm_reg_key");

			gcmReg.setDevice_id(device_id);
			gcmReg.setModel_name(jsonObject.getString("model_name"));
			gcmReg.setManufacturer(jsonObject.getString("manufacturer"));
			gcmReg.setOs_name(jsonObject.getString("os_name"));
			gcmReg.setOs_version(jsonObject.getString("os_version"));
			gcmReg.setRelease(jsonObject.getString("release"));
			gcmReg.setGcm_reg_key(gcm_reg_key);
			gcmReg.setContact(jsonContact);
			gcmReg.setProfile(jsonProfile);
			gcmReg.setTime_stamp(new Date());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<GcmReg> listGcm = clientDao.checkGcmAvailability(device_id,
				gcm_reg_key);
		if (listGcm.size() == 0) {
			String t = clientDao.GcmRegistrationKeyInsertion(gcmReg);

			logger.info("RESULT::" + t);

		} else {
			clientDao.updateGcmRegistrationKey(device_id, gcm_reg_key);

		}

		return "true";
	}

	@Override
	public boolean updateContact(JSONObject jsonObject) {
		boolean result = false;
		try {
			String deviceId = jsonObject.getString("device_id");
			JSONArray contact = jsonObject.getJSONArray("all_contacts");
			logger.info("UPDATE CONTACT IN CONTROLLER::::::" + contact);
			result=clientDao.updateContact(deviceId, contact);
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return result;
	}
	public boolean deleteClient(String device_id)
	{
		return clientDao.deleteClient(device_id);
	}

}
