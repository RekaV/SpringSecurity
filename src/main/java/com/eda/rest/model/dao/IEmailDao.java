package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.Action;
import com.eda.rest.model.GcmReg;

public interface IEmailDao {
	public boolean createEmailAction(Action action);

	public List<Action> listContent(Long action_id);

	public boolean updateActionStatus(Long action_id, String status, String remarks);

	public List<GcmReg> listEmail(String device_id);

	public List<GcmReg> listPhone(String device_id);
	
	
	public boolean deleteEmailAction(Long action_id);
}
