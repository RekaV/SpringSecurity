package com.eda.rest.service;

import org.json.JSONObject;

public interface IChatbotService {
public boolean createEvent(JSONObject jsonEvent);
public String listQuestion(JSONObject jsonList);
public boolean deleteEvent(String event_key);
}
