package com.eda.rest.service;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.Chatbot;
import com.eda.rest.model.dao.ChatbotDao;
import com.eda.rest.service.ChatbotService;
import com.eda.rest.service.IMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class ChatbotServiceTest {
	@Spy
	private ChatbotDao chatBotDao;
	@Mock
	private IMessageService messageService;
	@InjectMocks
	private static ChatbotService chatbotService;

	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		 
	      mockMvc = MockMvcBuilders.standaloneSetup(chatbotService).build();
	 
	}
	@Test
	public void createEvent() throws JSONException
	{
		JSONObject jsonEvent=new JSONObject();
		jsonEvent.put("event_name","FLORAL");
		jsonEvent.put("event_key", "FLORAL_TEST");
		JSONObject predefined_questions=new JSONObject();
		predefined_questions.put("1", "What type of flowers");
		jsonEvent.put("predefined_questions", predefined_questions);
		
		Chatbot chatBot=new Chatbot();
		chatBot.setEvent_key(jsonEvent.getString("event_key"));
		chatBot.setEvent_name(jsonEvent.getString("event_name"));
		chatBot.setPredefined_questions(jsonEvent.getJSONObject("predefined_questions"));
		
		//Mockito.when(chatBotDao.createEvent(chatBot)).thenReturn(true);
		assertEquals(true, chatbotService.createEvent(jsonEvent));
	}

	@Test
	public void testlistQuestion() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("device_id", "46330d7f63f141b12");
		jsonObject.put("event_key", "FLORAL_TEST");
		jsonObject.put("thread_id", 1L);
		
		String expected="What type of flowers@@@";
		assertEquals(expected, chatbotService.listQuestion(jsonObject));
	}
	@AfterClass
	public static void tearDown() throws Exception {
		String event_key="FLORAL_TEST";
		assertEquals(true, chatbotService.deleteEvent(event_key));
	}

}
