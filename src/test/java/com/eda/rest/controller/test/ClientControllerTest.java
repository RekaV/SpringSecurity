package com.eda.rest.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
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
import com.eda.rest.controller.ClientController;
import com.eda.rest.model.mapper.ClientMapper;
import com.eda.rest.service.IClientService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class ClientControllerTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Mock
	private IClientService clientService;

	@Mock
	private Logger logger;
	@InjectMocks
	private ClientController clientController;

	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

	}

	@Test
	public void testRegisterGcmKey() throws Exception {
		
		JSONObject jsoGcm=ClientMapper.buildGcmJson();
		//String device_id="23cef6a88a54aff7";
		when(clientService.GcmRegistrationKeyInsertion(jsoGcm)).thenReturn("true");
		mockMvc.perform(post("/gcmkey/create/").content(jsoGcm.toString()))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	@Test
	public final void testUpdateContact() throws Exception {
		
		JSONObject jsonContact=ClientMapper.buildContactJSon();
		when(clientService.updateContact(jsonContact)).thenReturn(true);
		mockMvc.perform(post("/contact/update/").content(jsonContact.toString()))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	public final void testUpdateProfile() throws Exception {
		
		JSONObject jsonProfile=ClientMapper.buildProfileJson();
		when(clientService.updateProfile(jsonProfile)).thenReturn(true);
		mockMvc.perform(post("/profile/create").content(jsonProfile.toString()))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	public final void testListProfile() throws Exception {
		String device_id="23cef6a88a54aff7";
		JSONObject jsonProfile=ClientMapper.buildProfileJson();
		JSONObject json=new JSONObject();
		json.put("device_id",device_id);
		when(clientService.listProfile(device_id)).thenReturn(jsonProfile.toString());
		mockMvc.perform(post("/profile/list").content(json.toString()))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

}
