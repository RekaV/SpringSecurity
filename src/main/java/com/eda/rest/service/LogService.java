package com.eda.rest.service;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eda.rest.model.ErrorLog;
import com.eda.rest.model.dao.ILogDao;

@Service
public class LogService implements ILogService{
	private Logger logger = Logger.getLogger(LogService.class);
	@Autowired
	private ILogDao logDao;
	@Override
	public boolean createLog(JSONObject jsonObject) {
		
		
		ErrorLog errorLog=new ErrorLog();
		try
		{
			errorLog.setDevice_id(jsonObject.getString("device_id"));
			errorLog.setManufacturer(jsonObject.getString("manufacturer"));
			errorLog.setModel(jsonObject.getString("model"));
			errorLog.setError_message(jsonObject.getString("error_message"));
			errorLog.setDate_time(jsonObject.getString("date_time"));
		}catch(JSONException e){
			logger.error("LogService:::"+e);
		}
		return logDao.createLog(errorLog);
	}
	@Override
	public boolean deletelog(String model) {
		
		return logDao.deletelog(model);
	}

}
