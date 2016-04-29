package com.eda.rest.service;

import org.json.JSONObject;

public interface IClientService {

	public boolean updateProfile(JSONObject jsonObject);

	public String listProfile(String device_id);

	public String GcmRegistrationKeyInsertion(JSONObject jsonObject);

	public boolean updateContact(JSONObject jsonObject);

	public boolean deleteClient(String device_id);
}
