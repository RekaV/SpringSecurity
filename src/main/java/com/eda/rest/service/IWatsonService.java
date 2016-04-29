package com.eda.rest.service;

import org.json.JSONObject;

public interface IWatsonService {
	public String getClassification(JSONObject questions);
	public String getAnswer(Long msg_id) ;
}
