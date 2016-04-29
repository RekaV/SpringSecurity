package com.eda.rest.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.controller.AbstractController;
import com.eda.rest.controller.NotesController;
import com.eda.rest.model.Action;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.model.mapper.ActionMapper;
import com.eda.rest.service.IMessageService;
import com.eda.rest.service.INotesService;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.google.android.gcm.server.Sender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class NotesControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private INotesService noteService;

	@Mock
	private SequenceDao sequenceDaoImpl;

	@Mock
	private AbstractController abstractController;

	@Mock
	private IMessageService messageService;
	
	@Mock
	private Sender sender;
	
	@InjectMocks
	private NotesController notesController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(notesController).build();
	}

	@Test
	public void testNotesSend() throws Exception {

		JSONObject actionId=new JSONObject();
		actionId.put("actionid", 1);
		List<Action> list = new ArrayList<Action>();
		list.add(ActionMapper.buildNote());
		String msgContent=list.get(0).getContent();
		when(noteService.listContent(actionId)).thenReturn(msgContent);
		mockMvc.perform(post("/notes/send/").content("{'actionid':1}"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	public void testNotesCreate() throws Exception {

		JSONObject jsonData = ActionMapper.jsonNote();
		Action action = ActionMapper.buildNote();
		List<Action> list = new ArrayList<Action>();
		list.add(action);
		String content=jsonData.get("content").toString();
		when(noteService.createNotesAction(jsonData)).thenReturn(content);
		MvcResult result = mockMvc
				.perform(
						post("/notes/create/").content(jsonData.toString())
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
	}

	@Test
	public void testUpdateActionStatus() throws Exception {
		JSONObject jsonData=new JSONObject();
		jsonData.put("action_id",1L);
		jsonData.put("remarks", "IC");
		jsonData.put("status", "IC");
		
		when(noteService.updateActionStatus(jsonData)).thenReturn(
				true);
		MvcResult result = mockMvc
				.perform(
						post("/notes/updatestatus/").content(
								jsonData.toString()).contentType(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
	}

}
