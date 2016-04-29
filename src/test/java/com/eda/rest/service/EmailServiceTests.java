package com.eda.rest.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.Action;
import com.eda.rest.model.dao.EmailDao;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.util.EmailAlert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class EmailServiceTests {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Mock
	SequenceDao sequenceDao;
	@Mock
	IMessageService messageService;
	@Mock
	GoogleService googleService;
	@Spy
	EmailDao emailDao;
	@InjectMocks
	static
	EmailService emailService;
	@Mock
	EmailAlert emailAlert;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		new Mockito();
		MockMvcBuilders.standaloneSetup(emailService).build();
	}
	@Test
	public void createEmailActionTest()
	{
		Long action_id=1000L;
		Long thread_id=1000L;
		
		String reg_key="APA91bFCXDADBKzpne4_zih1BPxyXzDX"
				+ "-7Sf9HTVMglCLJyRV7ZtK3EWGxlPcvKAHxVvehQsWgU7sozAh75My4KwLTsYgbJPkfhS7bysnGYm9ZP8LdL8_Sgydwe0tDJyi44zYeqimBlW";
		JSONObject jsonEmail=new JSONObject();
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("message","TEST");
		jsonContent.put("to","reka.v@kgfsl.com");
		jsonContent.put("subject", "TESTING");
		jsonEmail.put("action_code", "EMAIL");
		jsonEmail.put("content", jsonContent);
		jsonEmail.put("thread_id", thread_id);
		jsonEmail.put("status", "IC");
		jsonEmail.put("remarks", "In Complete");
		
		Action action=new Action();
		action.setAction_id(sequenceDao.getNextSequence("eda", "action_id"));
		action.setAction_code("EMAIL");
		action.setRemarks("C");
		action.setStatus("C");
		action.setThread_id(1L);
		action.setContent(jsonContent.toString());
    	List<Action> list=new ArrayList<Action>();
    	list.add(action);
    	
    	Mockito.when(googleService.getAction(jsonEmail)).thenReturn(action);
    	Mockito.when(sequenceDao.getNextSequence("eda", "action_id")).thenReturn(action_id);
    	Mockito.when(messageService.getRegKeyForParticularThread(thread_id)).thenReturn(reg_key);
    	Assert.assertEquals(true,emailService.createEmailAction(jsonEmail));
    	
	}
	@SuppressWarnings("unused")
	@Test
	public void updateActionStatusTest()
	{
		Long actionid=1000L;
		Long threadid=1000L;
		
		String deviceid="23cef6a88a54aff7";
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("action_id",actionid);
		jsonObj.put("status","C");
		jsonObj.put("remarks","Completed");
		jsonObj.put("thread_ref_id",threadid);
		jsonObj.put("device_id",deviceid);
		
		
		Long action_id=jsonObj.getLong("action_id");
		String status=jsonObj.getString("status");
		String remarks=jsonObj.getString("remarks");
		Long thread_id=jsonObj.getLong("thread_ref_id");
		String device_id=jsonObj.getString("device_id");
		
		Mockito.when(emailDao.updateActionStatus(action_id, status, remarks)).thenReturn(true);
		Assert.assertEquals(true,emailService.updateActionStatus(jsonObj));
	}
	@AfterClass
	public static void tearDown()
	{
		Long action_id=1000L;
		Assert.assertEquals(true,emailService.deleteEmailAction(action_id));
	}
}
