package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.Action;

public interface ISmsDao {
	public boolean createSmsAction(Action action);

	public List<Action> listContent(Long action_id);

	public boolean updateActionStatus(Long action_id, String status,
			String remarks);
	
	public boolean deleteSmsAction(Long action_id);
}
