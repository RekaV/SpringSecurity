package com.eda.rest.service;

import org.json.JSONObject;

public interface IContactService {
	public boolean createContactAction(JSONObject jsonObject);
	public String listContent(JSONObject jsonObject);
	public boolean updateActionStatus(JSONObject jsonObject);
	public boolean deleteContactAction(Long action_id);
}
