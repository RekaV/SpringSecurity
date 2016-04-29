package com.eda.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="sequence")
public class Sequence {

	@Id
	private String id;
	private Long msg_seq;
	private Long thread_seq;
	private Long action_seq;
	private Long insurance_seq;
	private Long agent_seq;
	private Long answer_seq;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(Long msg_seq) {
		this.msg_seq = msg_seq;
	}
	public Long getThread_seq() {
		return thread_seq;
	}
	public void setThread_seq(Long thread_seq) {
		this.thread_seq = thread_seq;
	}
	public Long getAction_seq() {
		return action_seq;
	}
	public void setAction_seq(Long action_seq) {
		this.action_seq = action_seq;
	}
	public Long getInsurance_seq() {
		return insurance_seq;
	}
	public void setInsurance_seq(Long insurance_seq) {
		this.insurance_seq = insurance_seq;
	}
	public Long getAgent_seq() {
		return agent_seq;
	}
	public void setAgent_seq(Long agent_seq) {
		this.agent_seq = agent_seq;
	}
	public Long getAnswer_seq() {
		return answer_seq;
	}
	public void setAnswer_seq(Long answer_seq) {
		this.answer_seq = answer_seq;
	}
	
	
	
}
