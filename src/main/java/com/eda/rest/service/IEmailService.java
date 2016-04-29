package com.eda.rest.service;

import java.util.List;

import org.json.JSONObject;
import com.eda.rest.model.GcmReg;

public interface IEmailService {
	public boolean createEmailAction(JSONObject jsonObj);

	public String listContent(JSONObject jsonObj);

	public boolean updateActionStatus(JSONObject jsonObj);

	public List<GcmReg> listEmail(String device_id);

	public List<GcmReg> listPhone(String device_id);

	public void sendEmailDirectly(JSONObject jsonObject);

	public boolean deleteEmailAction(Long action_id);
}
