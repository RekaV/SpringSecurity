package com.eda.rest.service;

import org.json.JSONObject;

public interface ISmsService {
	public boolean createSmsAction(JSONObject jsonObj);
	public String listContent(JSONObject jsonObj);
	public boolean updateActionStatus(JSONObject jsonObj) ;
	public boolean deleteSmsAction(Long action_id);
}
