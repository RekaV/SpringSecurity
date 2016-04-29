package com.eda.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="eda_action")
public class Action {

	 @Id
	 private String id;
	 private Long action_id;
	 private String action_code;
	 private String content;
	 private Long thread_id;
	 private String status;
	 private String remarks;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getAction_id() {
		return action_id;
	}
	public void setAction_id(Long action_id) {
		this.action_id = action_id;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getThread_id() {
		return thread_id;
	}
	public void setThread_id(Long thread_id) {
		this.thread_id = thread_id;
	}
	

}
