package com.eda.rest.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.Action;
import com.eda.rest.model.Message1;
import com.eda.rest.model.dao.ICalendarDao;
import com.eda.rest.model.dao.IMessageDao;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.model.mapper.ActionMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class CalendarServiceTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	@Mock
	GoogleService googleService;
	@Mock
	IMessageService messageService;
	@Mock
	IMessageDao messageDao;
	@Mock
	private ICalendarDao calendarDao;
	@Mock 
	SequenceDao sequenceDao;
	
	@InjectMocks
	private CalendarService calendarService;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	
	@Mock
	private MongoOperations mongoOperations;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		new Mockito();
		//emailDao=Mockito.mock(EmailDao.class);
		mockMvc = MockMvcBuilders.standaloneSetup(calendarService).build();
		
	}
	@Test
	public void testCalendarSend() throws Exception {

		JSONObject actionId=new JSONObject();
		actionId.put("actionid", 1);
		
		List<Action> expected = new ArrayList<Action>();
		expected.add(ActionMapper.buildCalendar());
		
		String expec=expected.listIterator(0).next().getContent();
		when(calendarDao.listContent(1L)).thenReturn(expected);
		assertEquals(expec, calendarService.listContent(actionId));
	}
	@Test
	public void testCreateCalendar() throws JSONException, IOException
	{

		
		String reg_key="APA91bHMmV27NFjmv-kD6AH-KecBz2h2joutau_5OVtUFKG3Vf9UYFsEzOjIWnrzDPKm-Jlq8V33yLTbdcih3kABLOZGIoYWpTBKeyJG56cSYzc1FhLKhwLTnfV-lJwYaq68HDzysYxI";
		String result="[ messageId=0:1447312452317530%a216349af9fd7ecd ]";
		
		JSONObject jsonCal=new JSONObject();
		JSONObject jsonContent=new JSONObject();
		
		jsonContent.put("location","KGFSL");
		jsonContent.put("startDate",new Date());
		jsonContent.put("startTime",new Date());
		jsonContent.put("endDate",new Date());
		jsonContent.put("endTime",new Date());
		jsonContent.put("participant","reka.v@kgfsl.com");
		jsonContent.put("description","Test meeting");
		
		jsonCal.put("action_code", "CLNDR");
		jsonCal.put("content", jsonContent);
		jsonCal.put("thread_id", 1L);
		jsonCal.put("status", "IC");
		jsonCal.put("remarks", "In Complete");
		
		Action action=ActionMapper.buildCalendar();
		when(googleService.getAction(jsonCal)).thenReturn(action);
		when(messageService.getRegKeyForParticularThread(1L)).thenReturn(reg_key);
		when(messageDao.getRegKeyForParticularThread(1L)).thenReturn(reg_key);
		when(googleService.sendGcmPushNotification(1L, jsonContent.toString())).thenReturn(result);
		when(sequenceDao.getNextSequence("eda", "msg_id")).thenReturn(1L);
		when(calendarDao.createCalenderAction(action)).thenReturn(true);
		
		
		//assertEquals(true, calendarService.createCalenderAction(jsonCal));
		assertEquals(false, calendarService.createCalenderAction(jsonCal));
		 
	}
	@Test
	public void testUpdateStatusCalendar() throws JSONException
	{
	
		/*JSONObject jsonObj=new JSONObject();
		jsonObj.put("action_id",1L);
		jsonObj.put("status","C");
		jsonObj.put("remarks","success");
		jsonObj.put("thread_ref_id",1L);
		jsonObj.put("device_id","23cef6a88a54aff7");
		
		when(emailDao.updateActionStatus(1L, "C", "success")).thenReturn(true);
		//when(googleService.getMessage(1L, "", "success")).thenReturn(value);
		//Mockito.verify(emailService,Mockito.times(1)).updateActionStatus(1L, "C", "success");
		//Mockito.verifyNoMoreInteractions(emailService);
		Assert.assertEquals(true, emailService.updateActionStatus(jsonObj));*/
		
		String device_id="23cef6a88a54aff7";
		Long thread_id=1L;
		String action="CALENDAR";
				
		JSONObject jsonCal=new JSONObject();
		jsonCal.put("action_id", 1L);
		jsonCal.put("status", "C");
		jsonCal.put("remarks", "success");
		jsonCal.put("thread_ref_id", thread_id);
		jsonCal.put("device_id", device_id);
		
		
		Message1 Message = new Message1();
		Message.setMsg_id(sequenceDao.getNextSequence("eda", "msg_id"));
		Message.setSender_id(1L);
		Message.setDevice_id(device_id);
		Message.setMsg_content("The action is performed successfully");
		Message.setOrigin_ip("");
		Message.setTime_stamp(new Date());
		Message.setThread_ref_id(thread_id);
		Message.setParent_id(1L);
		Message.setAgent_id(1L);
		Message.setDirection("I");
		Message.setPath("path");
		
		
		
		when(calendarDao.updateActionStatus(thread_id, "C", "success")).thenReturn(true);
		when(sequenceDao.getNextSequence("eda", "msg_id")).thenReturn(1L);
		when(messageService.createMessage(Message)).thenReturn(true);
		when(googleService.getMessage(thread_id, device_id, "C",action)).thenReturn(true);
		assertEquals(true, calendarService.updateActionStatus(jsonCal));
		
	}

}
