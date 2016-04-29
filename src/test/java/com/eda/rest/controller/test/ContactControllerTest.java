package com.eda.rest.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.controller.ContactController;
import com.eda.rest.model.Action;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.model.mapper.ActionMapper;
import com.eda.rest.service.IContactService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class ContactControllerTest {
	@Mock
	private IContactService contactService;

	@Mock
	private SequenceDao sequenceDaoImpl;
	@InjectMocks
	private ContactController contactController;
	private MockMvc mockMvc;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
	}
	@Test
	public void testContactSend() throws Exception
	{
		
		JSONObject action_id=new JSONObject();
		action_id.put("actionid", 1);
		List<Action> list = new ArrayList<Action>();
		list.add(ActionMapper.buildContact());
		String msgContent=list.get(0).getContent();
		when(contactService.listContent(action_id)).thenReturn(msgContent);
		mockMvc.perform(post("/new/contact/send/").content("{'actionid':1}"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	@Test
	public void testReceiveContactContent() throws Exception
	{
		JSONObject jsonData = ActionMapper.jsonContact();
		Action action = ActionMapper.buildContact();
		List<Action> list = new ArrayList<Action>();
		list.add(action);
		when(contactService.createContactAction(jsonData)).thenReturn(true);
		MvcResult result = mockMvc
				.perform(
						post("/new/contact/create/").content(jsonData.toString())
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
	}
	@Test
	public void testupdateActionStatus() throws Exception
	{
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		
		when(contactService.updateActionStatus(jsonData)).thenReturn(
				true);
		MvcResult result = mockMvc
				.perform(
						post("/new/contact/updatestatus/").content(
								jsonData.toString()).contentType(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
	}
}
