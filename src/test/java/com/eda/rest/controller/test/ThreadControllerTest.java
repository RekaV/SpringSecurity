package com.eda.rest.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.controller.ThreadController;
import com.eda.rest.model.MsgThread;
import com.eda.rest.model.mapper.ThreadMapper;
import com.eda.rest.service.IThreadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class ThreadControllerTest {
	@Mock
	private IThreadService threadService;
	@InjectMocks
	private ThreadController threadController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(threadController).build();
	}
	@Test
	public void testUpdateAgentStatus() throws Exception {
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("agent_id", 1L);
		when(threadService.updateAgentStatus(jsonObj)).thenReturn("update");
		mockMvc.perform(post("/agent/updatestatus/").content("{'agent_id':1}"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	@Test
	public void testUpdateThreadStatus() throws Exception
	{
		Long thread_id=1L;
		when(threadService.updateStatusInThread(thread_id)).thenReturn(true);
		mockMvc.perform(get("/thread/updatestatus/"+thread_id).contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	@Test
	public void testGetThreadStatus() throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("thread_ref_id", 1L);
		JSONObject out=new JSONObject();
		out.put("status", "C");
		when(threadService.getThreadStatus(jsonObject)).thenReturn(out.toString());
		mockMvc.perform(post("/thread/getstatus/").content(jsonObject.toString()).contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk()).andDo(print()).andReturn();
		
	}
	@Test
	public void testListThread1() throws Exception
	{
		JSONObject agent_id=new JSONObject();
		agent_id.put("agent_id", 1L);
		List<MsgThread> threadList=new ArrayList<MsgThread>();
		MsgThread msg_thread=ThreadMapper.buildThread();
		threadList.add(msg_thread);
		when(threadService.listThread(agent_id)).thenReturn(threadList);
		mockMvc.perform(post("/thread/listthread1/").content(agent_id.toString()).contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk()).andDo(print()).andReturn();
		
		
	}
	@Test
	public void testListAllThread() throws Exception
	{
		JSONObject agent_id=new JSONObject();
		agent_id.put("agent_id", 1L);
		List<MsgThread> threadList=new ArrayList<MsgThread>();
		MsgThread msg_thread=ThreadMapper.buildThread();
		threadList.add(msg_thread);
		when(threadService.listAllThread(agent_id)).thenReturn(threadList);
		mockMvc.perform(post("/thread/listallthread/").content(agent_id.toString()).contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk()).andDo(print()).andReturn();
		
	}
	@Test
	public void testAgentInsert() throws Exception
	{
		JSONObject jsonAgent=ThreadMapper.jsonAgent();
		when(threadService.agentInsert(jsonAgent)).thenReturn(true);
		mockMvc.perform(post("/agent/insert/").content(jsonAgent.toString()).contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk()).andDo(print()).andReturn();

	}
}
