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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.controller.LoginController;
import com.eda.rest.model.Agent;
import com.eda.rest.model.mapper.LoginMapper;
import com.eda.rest.service.ILoginService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class LoginControllerTest {

	@Mock
	private ILoginService loginService;
	
	@InjectMocks
	private LoginController loginController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}
	
	@Test
	public void testLogin() throws Exception{
		JSONObject jsonData=LoginMapper.jsonLogin();
		Agent agent=LoginMapper.buildAgent();
		List<Agent> list=new ArrayList<Agent>();
		list.add(agent);
		
		when(loginService.login(jsonData.getString("agent_name"), jsonData.getString("password"))).thenReturn(list);
		mockMvc.perform(post("/agent/login/").content("{'agent_name':'Alex','password':'123','status':'A'}"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}
}
