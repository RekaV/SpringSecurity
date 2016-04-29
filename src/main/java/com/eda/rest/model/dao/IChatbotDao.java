package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.Chatbot;

public interface IChatbotDao {
	public boolean createEvent(Chatbot chatBot);
	public List<Chatbot> listQuestions(String event_key);
	public boolean deleteEvent(String event_key);
}
