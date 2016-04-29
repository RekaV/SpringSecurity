package com.eda.rest.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import com.eda.rest.job.AutoAllocationJob;
import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.model.dao.MessageDao;
import com.eda.rest.model.dao.SequenceDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageServiceTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Spy
	MessageDao messageDao;
	@Mock
	SequenceDao sequenceDao;
	@Mock
	IThreadService threadService;
	@Mock
	AutoAllocationJob autoAllocationJob;

	@InjectMocks
	static
	MessageService messageService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(messageService).build();
		
	}
	@Test
	public void T1getCollectionNameTest()
	{
		String collectionName="[eda_action, eda_agent, eda_chatbot, eda_gcm_reg, eda_insurance, eda_location, eda_message, eda_thread, errorBundle,"
								+ "error_log, objectlabs-system, objectlabs-system.admin.collections, sequence, songs, system.indexes]";
		Mockito.when(messageDao.getCollectionName()).thenReturn(collectionName);
		Assert.assertEquals(collectionName, messageService.getCollectionName());
		
	}
	
	@Test
	public void T2getRegKeyForParticularThreadTest()
	{
		Long thread_ref_id=1000L;
		String reg_key="APA91bGH6FVBTa0sWMRUZB4NXppJlWXH7RswULB18d2Ko9AswXAf2u2yoNWNuQPqye6qFag"
				+ "zoVs3te_z0Z13UBRNNjcxJZZThbfJEBpZ2aw6d98oy-FMqiKyeLoXdfqklvLdWCJvZY0q";
		Mockito.when(messageDao.getRegKeyForParticularThread(thread_ref_id)).thenReturn(reg_key);
		Assert.assertEquals(reg_key, messageService.getRegKeyForParticularThread(thread_ref_id));
	}
	@Test
	public void T3getListOfAgentTest()
	{
		List<Long> agentList=new ArrayList<>();
		agentList.add(1L);
		agentList.add(2L);
		agentList.add(3L);
		agentList.add(4L);
		
		Mockito.when(messageDao.getListOfAgents()).thenReturn(agentList);
		Assert.assertEquals(agentList, messageService.getListOfAgent());
	}
	@Test
	public void T4listAgentTest()
	{
		/*String listAgent = "[{\"password\":\"123\",\"agent_id\":\"561df41a40722f5755d67c51\",\"agent_name\":\"Alex\",\"status\":\"NA\"},"
							+ "{\"password\":\"123\",\"agent_id\":\"561df46940722f5755d67c52\",\"agent_name\":\"Bob\",\"status\":\"NA\"},"
							+ "{\"password\":\"123\",\"agent_id\":\"561df47640722f5755d67c53\",\"agent_name\":\"Smith\",\"status\":\"NA\"},"
							+ "{\"password\":\"123\",\"agent_id\":\"561f53100920ea802bb648f4\",\"agent_name\":\"John\",\"status\":\"A\"}]";*/
		
		Agent agent=new Agent();
		agent.setAgent_id(1000L);
		agent.setAgent_name("Alex");
		agent.setPassword("123");
		agent.setStatus("A");
		
		List<Agent> listAgent=new ArrayList<Agent>();
		listAgent.add(agent);
		Mockito.when(messageDao.listAgent()).thenReturn(listAgent);
		Assert.assertEquals(listAgent, messageService.listAgent());
	}
	@Test
	public void  T5createMessage1Test()
	{
		String device_id="";
		Long thread_id=1000L;
		Message1 Message = new Message1();
		Mockito.when(sequenceDao.getNextSequence("eda", "msg_id")).thenReturn(1000L);
		
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
		
		
		Mockito.when(messageDao.createMessage(Message)).thenReturn(true);
		Assert.assertEquals(true, messageService.createMessage(Message));
	}
	
	@Test
	public void T6updatePathForVoiceFileTest()
	{
		Long msg_id=1000L;
		String path="path";
		Mockito.when(messageDao.updatePathForVoiceFile(msg_id, path)).thenReturn(true);
		Assert.assertEquals(true, messageService.updatePathForVoiceFile(msg_id, path));
	}
	@Test
	public void T7createMessageTest()
	{
		JSONObject jsonMessage=new JSONObject();
		jsonMessage.put("device_id", "3d6a67a81b40712a");
		jsonMessage.put("msg_content", "TEST MESSAGE");
		jsonMessage.put("origin_ip", "10.100.4.128");
		jsonMessage.put("thread_ref_id", 1000L);
		jsonMessage.put("direction", "O");
		
		@SuppressWarnings("unused")
		String device_id = jsonMessage.getString("device_id");
		String msg_content = jsonMessage.getString("msg_content");
		String direction = jsonMessage.getString("direction");
		Long thread_ref_id = jsonMessage.getLong("thread_ref_id");
		
		Mockito.when(sequenceDao.getNextSequence("eda", "msg_id")).thenReturn(1000L);
		
		Message1 Message = new Message1();
		Message.setMsg_id(sequenceDao.getNextSequence("eda", "msg_id"));
		Message.setSender_id(1L);
		Message.setDevice_id(jsonMessage.getString("device_id"));
		Message.setMsg_content(jsonMessage.getString("msg_content"));
		Message.setOrigin_ip(jsonMessage.getString("origin_ip"));
		Message.setTime_stamp(new Date());
		Message.setThread_ref_id(thread_ref_id);
		Message.setParent_id(1L);
		Message.setAgent_id(1L);
		Message.setDirection(jsonMessage.getString("direction"));
		Message.setPath("path");

		JSONObject returnToApp=new JSONObject();
		
		returnToApp.put("msg_id", 1000L);
		returnToApp.put("thread_ref_id", Message.getThread_ref_id());
		returnToApp.put("msg_content", msg_content);
		returnToApp.put("direction",direction);
		
		
		Mockito.when( threadService.threadStatus(1000L)).thenReturn("P");
		//Mockito.when(messageDao.createMessage(Message)).thenReturn(true);
		JSONObject actual=messageService.createMessage(jsonMessage);
		Assert.assertEquals(returnToApp.toString(), actual.toString());
		
	}
	@Test
	public void T8listMessageTest()
	{
		Long thread_id=1000L;
		Message1 msg=new Message1();
		msg.setMsg_content("TEST MSG");
		msg.setDirection("O");
		msg.setMsg_id(1000L);
		msg.setTime_stamp(new Date());
		msg.setPath("path");
		
		List<Message1> list=new ArrayList<Message1>();
		list.add(msg);

		Mockito.when(messageDao.listMessage(thread_id)).thenReturn(list);
		Assert.assertEquals(list, messageService.listMessage(thread_id));
	}
	
	
	@AfterClass
	public static void tearDown()
	{
		Long msg_id=1000L;
		Assert.assertEquals(true, messageService.deleteMessage(msg_id));
	}
}
