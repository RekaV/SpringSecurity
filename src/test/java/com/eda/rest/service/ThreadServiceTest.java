package com.eda.rest.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.MsgThread;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.model.dao.ThreadDao;
import com.eda.rest.service.ThreadService;

import org.junit.runners.MethodSorters;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThreadServiceTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	@Spy
	ThreadDao threadDao;
	@Mock
	SequenceDao sequenceDao;
	
	@InjectMocks
	static
	ThreadService threadService;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(threadService).build();
		
	}
	@Test
	public void test1CreateNewThread()
	{
		MsgThread edaThread = new MsgThread();
		edaThread.setAgent_id(0L);
		edaThread.setStatus("NP");
		edaThread.setDevice_id("3d6a67a81b40712a");
		edaThread.setStart_time(new Date());
		edaThread.setEnd_time(new Date());
		Mockito.when(sequenceDao.getNextSequence("eda", "thread_id")).thenReturn(1000L);
		edaThread.setThread_id(sequenceDao.getNextSequence("eda", "thread_id"));
		
		Mockito.when(threadDao.createNewThread(edaThread)).thenReturn(true);
		Assert.assertEquals(true, threadService.createNewThread(edaThread));
	
	}
	@Test
	public void test2updateStatusInThread()
	{
		Long thread_id=1000L;
		Assert.assertEquals(true, threadService.updateStatusInThread(thread_id));
	}
	@Test
	public void test3threadStatus()
	{
		Long thread_id=1000L;
		Mockito.when(threadDao.threadStatus(thread_id)).thenReturn("C");
		Assert.assertEquals("C", threadService.threadStatus(thread_id));
	}
	@Test
	public void test4getThreadStatus() throws JSONException
	{
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("thread_ref_id", "1000");
		@SuppressWarnings("unused")
		Long thread_ref_id=1000L;
		JSONObject json=new JSONObject();
		json.put("status", "C");
		//Mockito.when(threadDao.getThreadStatus(thread_ref_id)).thenReturn(json.toString());
		Assert.assertEquals(json.toString(), threadService.getThreadStatus(jsonObj));
	}
	@Test
	public void test5updateStatusInThread1()
	{
		Long agent_id=100L;
		String status = "NP";
		threadService.updateStatusInThread(agent_id, status);
	}

	@Test
	public void test6agentInsert() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("agent_name", "ABC");
		jsonObject.put("agent_id", 1000L);
		jsonObject.put("password", "123");
		jsonObject.put("status", "A");
		
		
		Agent agent = new Agent();
		agent.setAgent_name(jsonObject.getString("agent_name"));
		agent.setAgent_id(jsonObject.getLong("agent_id"));
		agent.setPassword(jsonObject.getString("password"));
		agent.setStatus(jsonObject.getString("status"));
		Mockito.when(sequenceDao.getNextSequence("eda", "agent_id")).thenReturn(1000L);
		
		Assert.assertEquals(true, threadService.agentInsert(jsonObject));
	}
	@Test
	public void test7updateAgentStatus() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("agent_id", 1000L);
		@SuppressWarnings("unused")
		Long agentId=1000L;
		Assert.assertEquals("update",threadService.updateAgentStatus(jsonObject));
	}
	@Test
	public void test8listThread() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("agent_id", 1000L);
		Long agent_id=jsonObject.getLong("agent_id");
		
		MsgThread msgThread=new MsgThread();
		msgThread.setThread_id(1000L);
		
		List<MsgThread> msgList=new ArrayList<MsgThread>();
		msgList.add(msgThread);
		Mockito.when(threadDao.listThread(agent_id)).thenReturn(msgList);
		Assert.assertEquals(msgList, threadService.listThread(jsonObject));
	}
	@Test
	public void test90listMessage()
	{
		Long thread_id=1000L;
		Message1 msg=new Message1();
		msg.setMsg_content("TEST MSG");
		msg.setDirection("O");
		msg.setMsg_id(1L);
		msg.setTime_stamp(new Date());
		msg.setPath("path");
		
		List<Message1> list=new ArrayList<Message1>();
		list.add(msg);
		
		Mockito.when(threadDao.listMessage(thread_id)).thenReturn(list);
		Assert.assertEquals(list, threadService.listMessage(thread_id));
	}
	@Test
	public void test91listAllThread() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("agent_id", 1000L);
		
		List<MsgThread> list_all_thread_id=new ArrayList<MsgThread>();
		MsgThread edaThread = new MsgThread();
		edaThread.setAgent_id(0L);
		edaThread.setStatus("NP");
		edaThread.setDevice_id("3d6a67a81b40712a");
		edaThread.setStart_time(new Date());
		edaThread.setEnd_time(new Date());
		edaThread.setThread_id(100L);
		list_all_thread_id.add(edaThread);
		Mockito.when(threadDao.listAllThread(1000L)).thenReturn(list_all_thread_id);
		Assert.assertEquals(list_all_thread_id, threadService.listAllThread(jsonObject));
	}
	@Test
	public void test92deviceId()
	{
		Long thread_id=1000L;
		MsgThread edaThread = new MsgThread();
		edaThread.setDevice_id("3d6a67a81b40712a");
		Mockito.when(threadDao.deviceId(thread_id)).thenReturn(edaThread.getDevice_id());
		Assert.assertEquals("3d6a67a81b40712a",threadService.deviceId(thread_id));
	}
	@AfterClass
	public  static void tearDown()
	{
		Assert.assertEquals(true, threadService.deleteThread(1000L));
		Assert.assertEquals(true, threadService.deleteAgent(1000L));
	}
}
