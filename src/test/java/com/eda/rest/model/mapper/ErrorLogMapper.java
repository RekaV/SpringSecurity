package com.eda.rest.model.mapper;



import org.json.JSONException;
import org.json.JSONObject;

import com.eda.rest.model.ErrorLog;

public class ErrorLogMapper {
public static ErrorLog errorLog()
{
	ErrorLog errorLog=new ErrorLog();
	errorLog.setDevice_id("se98775derr");
	errorLog.setManufacturer("Samsung");
	errorLog.setModel("SM1500");
	errorLog.setError_message("null pointer exception");
	errorLog.setDate_time("13/09/2015");
	
	return errorLog;
	
}
public static JSONObject jsonLog() throws JSONException
{
	JSONObject jsonData=new JSONObject();
	jsonData.put("device_id", "se98775derr");
	jsonData.put("manufacturer", "Samsung");
	jsonData.put("model", "SM1500");
	jsonData.put("error_message", "null pointer exception");
	jsonData.put("date_time", "13/09/2015");
	return jsonData;
	
}
}
