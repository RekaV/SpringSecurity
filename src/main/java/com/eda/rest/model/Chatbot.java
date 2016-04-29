package com.eda.rest.model;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="eda_chatbot")
public class Chatbot {

	@Id
	String _id;
	
	String event_name;
	String event_key;
	JSONObject predefined_questions;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public String getEvent_key() {
		return event_key;
	}
	public void setEvent_key(String event_key) {
		this.event_key = event_key;
	}
	public JSONObject getPredefined_questions() {
		return predefined_questions;
	}
	public void setPredefined_questions(JSONObject predefined_questions) {
		this.predefined_questions = predefined_questions;
	}
	
	
}
