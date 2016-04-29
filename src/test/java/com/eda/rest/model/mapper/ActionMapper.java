package com.eda.rest.model.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.eda.rest.model.Action;


public class ActionMapper {

	 
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
	
	public static JSONObject jsonEmail() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("action_code", "EMAIL");
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		jsonData.put("thread_id", 1L);
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("message","TEST1");
		jsonContent.put("to","reka.v1@kgfsl.com");
		jsonContent.put("subject","TEST MAIL1111");
		
		jsonData.put("content",jsonContent);
		
		return jsonData;
	}
	public static JSONObject jsonNote() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("action_code", "NOTE");
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		jsonData.put("thread_id", 1L);
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("title","NOTE1");
		jsonContent.put("subject","TEST NOTE");
		
		jsonData.put("content",jsonContent);
		
		return jsonData;
	}
	public static JSONObject jsonCalendar() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("action_code", "CLNDR");
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		jsonData.put("thread_id", 1L);
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("location","KGFSL");
		jsonContent.put("startDate",new Date());
		jsonContent.put("startTime",new Date());
		jsonContent.put("endDate",new Date());
		jsonContent.put("endTime",new Date());
		jsonContent.put("participant","reka.v@kgfsl.com");
		jsonContent.put("description","Test meeting");
		
		jsonData.put("content",jsonContent);
		
		return jsonData;
	}
	public static JSONObject jsonSms() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("action_code", "CLNDR");
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		jsonData.put("thread_id", 1L);
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("receipient","7708190947");
		jsonContent.put("message","Hello test message");
		
		
		jsonData.put("content",jsonContent);
		
		return jsonData;
	}
	
	public static JSONObject jsonContact() throws JSONException
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("action_code", "CLNDR");
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		jsonData.put("thread_id", 1L);
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("contact_name","reka");
		jsonContent.put("mobile_number","9965242827");
		
		
		jsonData.put("content",jsonContent);
		
		return jsonData;
	}
	
	public static Action buildNote() throws JSONException
	{
		JSONObject jsonContent=jsonNote();
		
		
		Action action=new Action();
		action.setAction_id(1L);
		action.setAction_code("EMAIL");
		action.setRemarks("C");
		action.setStatus("C");
		action.setThread_id(1L);
		action.setContent(jsonContent.toString());
    	List<Action> list=new ArrayList<Action>();
    	list.add(action);
    	
		return action;
		
	}

	public static Action buildEmail() throws JSONException
	{
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("message","TEST1");
		jsonContent.put("to","reka.v1@kgfsl.com");
		jsonContent.put("subject","TEST MAIL1111");
		
		Action action=new Action();
		action.setAction_id(1L);
		action.setAction_code("EMAIL");
		action.setRemarks("C");
		action.setStatus("C");
		action.setThread_id(1L);
		action.setContent(jsonContent.toString());
    	List<Action> list=new ArrayList<Action>();
    	list.add(action);
    	
		return action;
		
	}

	public static Action buildCalendar() throws JSONException
	{
		/*		"location":$("#txtCallocation").val(),
		"startDate":$("#startDate").val(),
		"startTime":$("#startTime").val(),
		"endDate":$("#endDate").val(),
		"endTime":$("#endTime").val(),
		"participant":$("#txtCalParticipant").val(),
		"description":$("#txtCalDesc").val(),*/

		JSONObject jsonContent=new JSONObject();
		jsonContent.put("location","KGFSL");
		jsonContent.put("startDate",new Date());
		jsonContent.put("startTime",new Date());
		jsonContent.put("endDate",new Date());
		jsonContent.put("endTime",new Date());
		jsonContent.put("participant","reka.v@kgfsl.com");
		jsonContent.put("description","Test meeting");
		
		
		
		
		Action action=new Action();
		action.setAction_id(1L);
		action.setAction_code("CLNDR");
		action.setRemarks("C");
		action.setStatus("C");
		action.setThread_id(1L);
		action.setContent(jsonContent.toString());
    	List<Action> list=new ArrayList<Action>();
    	list.add(action);
    	
		return action;
	}
	public static Action buildSms() throws JSONException
	{
		/*		"location":$("#txtCallocation").val(),
		"startDate":$("#startDate").val(),
		"startTime":$("#startTime").val(),
		"endDate":$("#endDate").val(),
		"endTime":$("#endTime").val(),
		"participant":$("#txtCalParticipant").val(),
		"description":$("#txtCalDesc").val(),*/

		JSONObject jsonContent=new JSONObject();
		jsonContent.put("receipient","7708190947");
		jsonContent.put("message","Hello test message");
		
		
		
		
		Action action=new Action();
		action.setAction_id(1L);
		action.setAction_code("SMS");
		action.setRemarks("C");
		action.setStatus("C");
		action.setThread_id(1L);
		action.setContent(jsonContent.toString());
    	List<Action> list=new ArrayList<Action>();
    	list.add(action);
    	
		return action;
	}
	public static Action buildContact() throws JSONException
	{
		/*		"location":$("#txtCallocation").val(),
		"startDate":$("#startDate").val(),
		"startTime":$("#startTime").val(),
		"endDate":$("#endDate").val(),
		"endTime":$("#endTime").val(),
		"participant":$("#txtCalParticipant").val(),
		"description":$("#txtCalDesc").val(),*/

		JSONObject jsonContent=new JSONObject();
		jsonContent.put("contact_name","reka");
		jsonContent.put("mobile_number","9965242827");
		
		
		
		
		Action action=new Action();
		action.setAction_id(1L);
		action.setAction_code("CONTACT");
		action.setRemarks("C");
		action.setStatus("C");
		action.setThread_id(1L);
		action.setContent(jsonContent.toString());
    	List<Action> list=new ArrayList<Action>();
    	list.add(action);
    	
		return action;
	}

	
}
