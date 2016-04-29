package com.eda.rest.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eda_message")

public class Message1 {
	@Id
	private String id;
	private Long msg_id;
	private Long sender_id;
	private String device_id;
	private String msg_content;
	private String origin_ip;
	private Date time_stamp;
	private Long thread_ref_id;
	private Long parent_id;
	private Long agent_id;
	private String direction;
	private String path;
	private String nlc_classification;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Long msg_id) {
		this.msg_id = msg_id;
	}
	public Long getSender_id() {
		return sender_id;
	}
	public void setSender_id(Long sender_id) {
		this.sender_id = sender_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public String getOrigin_ip() {
		return origin_ip;
	}
	public void setOrigin_ip(String origin_ip) {
		this.origin_ip = origin_ip;
	}
	public Date getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
	public Long getThread_ref_id() {
		return thread_ref_id;
	}
	public void setThread_ref_id(Long thread_ref_id) {
		this.thread_ref_id = thread_ref_id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getNlc_classification() {
		return nlc_classification;
	}
	public void setNlc_classification(String nlc_classification) {
		this.nlc_classification = nlc_classification;
	}
	
	

}