package com.eda.rest.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

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
import com.eda.rest.controller.InsuranceController;
import com.eda.rest.model.mapper.InsuranceMapper;
import com.eda.rest.service.IInsuranceService;
import com.eda.rest.util.EmailAlert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class InsuranceControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Mock
	IInsuranceService insuranceService;
	@Mock
	EmailAlert emailAlert;
	@InjectMocks
	InsuranceController insuranceController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(insuranceController).build();
	}
	@Test
	public void testInsuranceCreate() throws Exception {

		JSONObject jsonData =InsuranceMapper.jsonInsurance();
		when(insuranceService.createTravelInsurance(jsonData)).thenReturn(true);
		MvcResult result = mockMvc
				.perform(
						post("/travel/insurance/create/").content(jsonData.toString())
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
	}
	@Test
	public void testCreateLocation() throws Exception
	{
		JSONObject jsonData=InsuranceMapper.jsonLocation();
		when(insuranceService.createLocation(jsonData)).thenReturn(true);
		MvcResult result = mockMvc
				.perform(
						post("/location/create/").content(jsonData.toString())
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
		
	}
	@Test
	public void testGetLocation() throws Exception
	{
		when(insuranceService.getLocation()).thenReturn("saravanampatti");
		MvcResult result = mockMvc
				.perform(
						post("/location/get/").content("saravanampatti")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println("RESULT::" + result);
		
	}
	
	
	
}
