package com.eda.rest.model.mapper;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClientMapper {

	public static JSONObject buildGcmJson() throws JSONException
	{
		JSONObject jsonGcm=new JSONObject();
		JSONArray jsonContact=new JSONArray();
		JSONObject jsonProfile=new JSONObject();
		jsonGcm.put("device_id","23cef6a88a54aff7");
		jsonGcm.put("model_name","SM-G7102");
		jsonGcm.put("manufacturer","samsung");
		jsonGcm.put("os_name","Linux");
		jsonGcm.put("os_version","3.4.0-2903967");
		jsonGcm.put("release","4.4.2");
		jsonGcm.put("gcm_reg_key","APA91bFgw4CNdx-SnoC4-899lSbjW5aDS47EFWa8r7NJHwXejlr2R8GxRL6GDkoAapR4uzmPdDLxAxtx8JJza2-RoVklhOQYi4I84Gc61D9G5fSI2J6eCL8H-YQoxbMQc3k0lgydpo2a");
		jsonGcm.put("jsonContact",jsonContact);
		jsonGcm.put("jsonProfile",jsonProfile);
		jsonGcm.put("time_stamp",new Date());
		
		return jsonGcm;
	}
	
	public static JSONObject buildContactJSon() throws JSONException
	{
		JSONObject json=new JSONObject();
		JSONObject phone_numbers=new JSONObject();
		JSONArray jsonContact=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
		phone_numbers.put("MOBILE", "9965242827");
		json.put("email_id", "reka.v@kgfsl.com");
		json.put("phone_numbers", phone_numbers);
		json.put("name","reka");
		jsonContact.put(json);
		jsonObject.put("device_id", "23cef6a88a54aff7");
		jsonObject.put("all_contacts", jsonContact);
		
		return jsonObject;
		
	}
	public static JSONObject buildProfileJson() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		        	 
        jsonObject.put("email_id", "aravind.m@kgfsl.com");
		jsonObject.put("address", "cbe");
		jsonObject.put("last_name", "manokaran");
		jsonObject.put("mobile_number", "23456799433567");
		jsonObject.put("first_name", "aravind");
		jsonObject.put("anniversary", "15/09/2015");
		return jsonObject;
	}
	
	
}
