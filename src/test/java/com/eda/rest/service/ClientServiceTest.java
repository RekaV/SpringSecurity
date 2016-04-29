package com.eda.rest.service;

import static org.junit.Assert.*;

import java.nio.charset.Charset;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.dao.IClientDao;
import com.eda.rest.service.IClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class ClientServiceTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Autowired
	IClientService clientService;
	@Autowired
	IClientDao clientDao;
	
	
	@Before
	public void setUp() throws JSONException
	{
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("device_id", "46330d7f63f141b12");
		jsonObject.put("gcm_reg_key", "APA91bFQOGp_vqOkQPz9KLYLofg15rjKT7yhFjC4NuCnsv4v-0IFA9LHw-OKZ6C88CJktN5URfWWgoqGEdsD1M5UaxFSO4LVQTv8YKdVi9Qm-8OUPZoU5vWqPtvQhb4l5JIurVPDe2vl");
		jsonObject.put("model_name", "SM-E700H");
		jsonObject.put("manufacturer", "SM-E700H");
		
		jsonObject.put("os_name", "Linux");
		jsonObject.put("os_version", "3.10.28-4908225");
		jsonObject.put("release", "4.4.4");
		
		
		clientService.GcmRegistrationKeyInsertion(jsonObject);	
	}
	@Test
	public void testUpdateContact() throws JSONException
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
		jsonObject.put("device_id", "46330d7f63f141b12");
		jsonObject.put("all_contacts", jsonContact);
		assertEquals(true, clientService.updateContact(jsonObject));
	}
	@Test
	public void testListProfile() throws JSONException
	{
		String device_id="46330d7f63f141b12";
		JSONObject profile=new JSONObject();
		
		profile.put("first_name", "");
		profile.put("last_name", "");
		profile.put("address", "");
		profile.put("mobile_number", "");
		profile.put("email_id", "");
		profile.put("anniversary", "");
		
		assertEquals(profile.toString(), clientService.listProfile(device_id));
		
	}
	@Test
	public void testUpdateProfile() throws JSONException
	{
		JSONObject profile=new JSONObject();
		profile.put("device_id", "46330d7f63f141b12");
		profile.put("first_name", "xyz");
		profile.put("last_name", "abc");
		profile.put("address", "cbe");
		profile.put("mobile_number", "9965242827");
		profile.put("email_id", "xyz@gmail.com");
		profile.put("anniversary", "15/03/15");
		
		assertEquals(true, clientService.updateProfile(profile));
	}
	
	@After
	public void tearDown()
	{
		String device_id="46330d7f63f141b12";
		assertEquals(true, clientService.deleteClient(device_id));
	}
}
