package com.eda.rest.service;

import java.util.List;

import com.eda.rest.model.Action;

public interface IActionService {
	public void createAction(Action action);
	public List<Action> listContent(Long action_id);
	public void updateAction(Long action_id,String status,String remarks);
}
