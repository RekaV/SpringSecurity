package com.eda.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="eda_watson_nlc")
public class WatsonNlc {

	@Id
	String id;
	Long answer_id;
	Long msg_id;
	Long thread_id;
	//Long agent_id;
	String answer;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Long answer_id) {
		this.answer_id = answer_id;
	}
	public Long getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Long msg_id) {
		this.msg_id = msg_id;
	}
	public Long getThread_id() {
		return thread_id;
	}
	public void setThread_id(Long thread_id) {
		this.thread_id = thread_id;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
}
