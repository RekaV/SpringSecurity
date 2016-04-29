package com.eda.rest.service;

import java.nio.charset.Charset;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eda.rest.config.AppConfig;
import com.eda.rest.config.AppInitializer;
import com.eda.rest.model.ErrorLog;
import com.eda.rest.model.dao.LogDao;
import com.eda.rest.service.LogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class LogServiceTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Spy
	private LogDao logDao;
	@Mock
	private MongoOperations mongoOperations;
	@InjectMocks
	private static LogService logService;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(logService).build();
		
	}
	
	@Test
	public void createLog() throws JSONException
	{
		ErrorLog errorLog=new ErrorLog();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("device_id", "3d6a67a81b40712a");
		jsonObject.put("manufacturer", "Micromax");
		jsonObject.put("model", "test_model");
		jsonObject.put("error_message", "TEST LOG MESSAGE");
		jsonObject.put("date_time",new Date().toString());
		
		
		errorLog.setDevice_id(jsonObject.getString("device_id"));
		errorLog.setManufacturer(jsonObject.getString("manufacturer"));
		errorLog.setModel(jsonObject.getString("model"));
		errorLog.setError_message(jsonObject.getString("error_message"));
		errorLog.setDate_time(jsonObject.getString("date_time"));
		
		//Mockito.when(logDao.createLog(errorLog)).thenReturn(true);
		Assert.assertEquals(true, logService.createLog(jsonObject));
	}
	@AfterClass
	public static void tearDown()
	{
		Assert.assertEquals(true, logService.deletelog("test_model"));
	}
	
}
