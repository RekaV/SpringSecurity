package com.eda.rest.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eda_thread")
public class MsgThread {
	 @Id
	private String id;
	private Long thread_id;
	private Long agent_id;
	private String status;
	private String device_id;

	private Date start_time;
	private Date end_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getThread_id() {
		return thread_id;
	}
	public void setThread_id(Long thread_id) {
		this.thread_id = thread_id;
	}
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
}
