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
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.model.dao.SmsDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class SmsServiceTests {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Spy
	SmsDao smsDao;
	@Mock
	GoogleService googleService;
	@Mock
	IMessageService messageService;
	@Mock
	SequenceDao sequenceDao;
	@InjectMocks
	static
	SmsService smsService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(smsService).build();
		
	}
	@Test
	public void createSmsActionTest()
	{
		Long threadid=1000L;
		
		String reg_key="APA91bGH6FVBTa0sWMRUZB4NXppJlWXH7RswULB18d2Ko9AswXAf2u2yoNWNuQPqye6qFag"
				+ "zoVs3te_z0Z13UBRNNjcxJZZThbfJEBpZ2aw6d98oy-FMqiKyeLoXdfqklvLdWCJvZY0q";
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("receipient","7708190947");
		jsonContent.put("message","Hello");
		JSONObject jsonSms=new JSONObject();
		jsonSms.put("action_code", "SMS");
		jsonSms.put("content", jsonContent);
		jsonSms.put("thread_id", threadid);
		jsonSms.put("status", "IC");
		jsonSms.put("remarks", "In Complete");
		
		Action action=new Action();
		action.setAction_id(sequenceDao.getNextSequence("eda", "action_id"));
		action.setAction_code(jsonSms.getString("action_code"));
		action.setContent(String.valueOf(jsonSms.getJSONObject("content").toString()));
		action.setThread_id(jsonSms.getLong("thread_id"));
		action.setStatus(jsonSms.getString("status"));
		action.setRemarks(jsonSms.getString("remarks"));
		
		Mockito.when(sequenceDao.getNextSequence("eda", "action_id")).thenReturn(1000L);
		Mockito.when(googleService.getAction(jsonSms)).thenReturn(action);
		
		Mockito.when(messageService.getRegKeyForParticularThread(threadid)).thenReturn(reg_key);
		//Assert.assertEquals(jsonContent.toString(), smsService.createSmsAction(jsonSms));
		Assert.assertEquals(true, smsService.createSmsAction(jsonSms));

	}
	
	@Test
	public void listContentTest()
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("actionid", 1000L);
		Long threadid=1000L;
		
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("receipient","7708190947");
		jsonContent.put("message","Hello");
		JSONObject jsonSms=new JSONObject();
		jsonSms.put("action_code", "SMS");
		jsonSms.put("content", jsonContent);
		jsonSms.put("thread_id", threadid);
		jsonSms.put("status", "IC");
		jsonSms.put("remarks", "In Complete");
		
		Action action=new Action();
		action.setAction_id(sequenceDao.getNextSequence("eda", "action_id"));
		action.setAction_code(jsonSms.getString("action_code"));
		action.setContent(String.valueOf(jsonSms.getJSONObject("content").toString()));
		action.setThread_id(jsonSms.getLong("thread_id"));
		action.setStatus(jsonSms.getString("status"));
		action.setRemarks(jsonSms.getString("remarks"));
		
		List<Action> listSms=new ArrayList<>();
		listSms.add(action);
		Mockito.when(sequenceDao.getNextSequence("eda", "action_id")).thenReturn(1000L);
		Mockito.when(smsDao.listContent(1000L)).thenReturn(listSms);
		Assert.assertEquals(jsonContent.toString(), smsService.listContent(jsonObject));
		
	}
	@SuppressWarnings("unused")
	@Test
	public void updateActionStatusTest(){
		
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
		
		Mockito.when(smsDao.updateActionStatus(action_id, status, remarks)).thenReturn(true);
		Assert.assertEquals(true,smsService.updateActionStatus(jsonObj));
	}
	@AfterClass
	public static void tearDown()
	{
		Long action_id=1000L;
		Assert.assertEquals(true,smsService.deleteSmsAction(action_id));
	}
}
