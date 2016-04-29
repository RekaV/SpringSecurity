package com.eda.rest.service;

import org.json.JSONObject;

public interface INotesService {
	/*public boolean createNotesAction(Action action);
	public List<Action> listContent(Long action_id);
	public boolean updateActionStatus(Long action_id, String status, String remarks) ;*/
	
	public String createNotesAction(JSONObject jsonObject);
	public String listContent(JSONObject jsonObject);
	public boolean updateActionStatus(JSONObject jsonObject) ;
}
