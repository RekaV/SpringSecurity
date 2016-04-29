package com.eda.rest.service;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.Agent;
import com.eda.rest.model.dao.ILoginDao;
import com.eda.rest.model.mapper.LoginMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class LoginServiceTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private ILoginDao loginDao;

	
	@InjectMocks
	private LoginService loginService;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginService).build();
		
	}

	@Test
	public void testLogin() throws Exception {
		List<Agent> expected = new ArrayList<Agent>();
		expected.add(LoginMapper.buildAgent());
		/*List<Action> actual=emailDao.listContent(1L);
		System.out.println("RESULT::::::::"+ expected+"ACTUAL::::::"+actual);
		//Assert.assertEquals(expected, actual);
		Mockito.verify(emailDao,Mockito.times(1)).listContent(1L);
    	Mockito.verifyNoMoreInteractions(emailDao);*/
		Mockito.when(loginDao.login("Alex", "123")).thenReturn(expected);
		assertEquals(expected, loginService.login("Alex", "123"));
	}

}
