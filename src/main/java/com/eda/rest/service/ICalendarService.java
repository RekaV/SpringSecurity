package com.eda.rest.service;

import org.json.JSONObject;

public interface ICalendarService {

	public boolean createCalenderAction(JSONObject jsonObj);
	public String listContent(JSONObject jsonObj);
	public boolean updateActionStatus(JSONObject jsonObj) ;
}
