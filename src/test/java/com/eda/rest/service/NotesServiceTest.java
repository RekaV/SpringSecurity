package com.eda.rest.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.Action;
import com.eda.rest.model.dao.INotesDao;
import com.eda.rest.model.dao.SequenceDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class NotesServiceTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	GoogleService googleService;
	@Mock
	IMessageService messageService;
	@Mock
	INotesDao notesDao;
	@Mock
	SequenceDao sequenceDao;
	@InjectMocks
	NotesService notesService;
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(notesService).build();
		
	}
	@Test
	public void createNotesTest()
	{
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("title","NOTE1");
		jsonContent.put("subject","TEST NOTE");
		
		Long thread_id=1000L;
		String reg_key="APA91bHMmV27NFjmv-kD6AH-KecBz2h2joutau_5OVtUFKG3Vf9UYFsEzOjIWnrzDPKm-Jlq8V33yLTbdcih3kABLOZGIoYWp"
				+ "TBKeyJG56cSYzc1FhLKhwLTnfV-lJwYaq68HDzysYxI";
		JSONObject jsonAction=new JSONObject();
		jsonAction.put("action_code", "NOTES");
		jsonAction.put("content", jsonContent);
		jsonAction.put("thread_id", thread_id);
		jsonAction.put("status", "IC");
		jsonAction.put("remarks", "InComplete");
		
		Action action=new Action();
		action.setAction_id(sequenceDao.getNextSequence("eda", "action_id"));
		action.setAction_code(jsonAction.getString("action_code"));
		action.setContent(String.valueOf(jsonAction.getJSONObject("content").toString()));
		action.setThread_id(jsonAction.getLong("thread_id"));
		action.setStatus(jsonAction.getString("status"));
		action.setRemarks(jsonAction.getString("remarks"));
		
		Mockito.when(sequenceDao.getNextSequence("eda", "action_id")).thenReturn(1000L);
		Mockito.when(googleService.getAction(jsonAction)).thenReturn(action);
		Mockito.when(notesDao.createNotesAction(action)).thenReturn(true);
		Mockito.when(messageService.getRegKeyForParticularThread(thread_id)).thenReturn(reg_key);
		Assert.assertEquals(jsonContent.toString(), notesService.createNotesAction(jsonAction));
	}
	@Test
	public void listContentTest()
	{
		JSONObject actionidJson=new JSONObject();
		actionidJson.put("actionid", 1000L);
		Long actionid=actionidJson.getLong("actionid");
		Long thread_id=1000L;
		JSONObject jsonContent=new JSONObject();
		jsonContent.put("title","NOTE1");
		jsonContent.put("subject","TEST NOTE");
		
		JSONObject jsonAction=new JSONObject();
		jsonAction.put("action_code", "NOTES");
		jsonAction.put("content", jsonContent.toString());
		jsonAction.put("thread_id", thread_id);
		jsonAction.put("status", "IC");
		jsonAction.put("remarks", "InComplete");
		
		Action action=new Action();
		action.setAction_id(sequenceDao.getNextSequence("eda", "action_id"));
		action.setAction_code(jsonAction.getString("action_code"));
		action.setContent(jsonAction.getString("content"));
		action.setThread_id(jsonAction.getLong("thread_id"));
		action.setStatus(jsonAction.getString("status"));
		action.setRemarks(jsonAction.getString("remarks"));
		
		List<Action> actionList=new ArrayList<>();
		actionList.add(action);
		
		Mockito.when(notesDao.listContent(actionid)).thenReturn(actionList);
		Assert.assertEquals(jsonContent.toString(), notesService.listContent(actionidJson));
	}
	@Test
	public void updateActionStatusTest()
	{
		String deviceid="23cef6a88a54aff7";
		Long actionid=1000L;
		Long threadid=1000L;
		String action="NOTES";
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("action_id", actionid);
		jsonObject.put("status", "C");
		jsonObject.put("remarks", "Completed");
		jsonObject.put("thread_ref_id", threadid);
		jsonObject.put("device_id", deviceid);
		
		Long action_id=jsonObject.getLong("action_id");
		String status=jsonObject.getString("status");
		String remarks=jsonObject.getString("remarks");
		Long thread_id=jsonObject.getLong("thread_ref_id");
		String device_id=jsonObject.getString("device_id");
		
		Mockito.when(notesDao.updateActionStatus(action_id, status, remarks)).thenReturn(true);
		Mockito.when(googleService.getMessage(thread_id, device_id, status,action)).thenReturn(true);
		
		Assert.assertEquals(true, notesService.updateActionStatus(jsonObject));
		
	}
}
