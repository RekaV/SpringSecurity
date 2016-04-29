package com.eda.rest.service;

import org.json.JSONObject;

public interface ILogService {
public boolean createLog(JSONObject jsonObject);
public boolean deletelog(String model);
}
