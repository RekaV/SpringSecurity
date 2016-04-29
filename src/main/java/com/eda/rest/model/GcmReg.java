package com.eda.rest.model;



import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="eda_gcm_reg")
public class GcmReg {
	private @Id
	String id;
	private String device_id;
	private String model_name;
	private String manufacturer;
	String os_name;
	String os_version;
	String release;
	private String gcm_reg_key;
	private JSONArray contact;
	private JSONObject profile;
	private Date time_stamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getOs_name() {
		return os_name;
	}
	public void setOs_name(String os_name) {
		this.os_name = os_name;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getGcm_reg_key() {
		return gcm_reg_key;
	}
	public void setGcm_reg_key(String gcm_reg_key) {
		this.gcm_reg_key = gcm_reg_key;
	}
	public JSONArray getContact() {
		return contact;
	}
	public void setContact(JSONArray contact) {
		this.contact = contact;
	}
	
	public JSONObject getProfile() {
		return profile;
	}
	public void setProfile(JSONObject profile) {
		this.profile = profile;
	}
	public Date getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	

}
