package com.eda.rest.service;

import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.eda.rest.model.Insurance;
import com.eda.rest.model.Location;
import com.eda.rest.model.dao.InsuranceDao;
import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.model.mapper.InsuranceMapper;
import com.eda.rest.util.EmailAlert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, AppInitializer.class })
@WebAppConfiguration
public class InsuranceServiceTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	
	@Mock
	private SequenceDao sequenceDao;
	@Mock
	private MongoOperations mongo;
	@Spy
	private InsuranceDao insuranceDao;
	@Mock
	private EmailAlert emailAlert;
	@InjectMocks
	static
	InsuranceService insuranceService;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(insuranceService).build();
		
	}
	@Test
	public void testCreateInsurance() throws JSONException
	{
		JSONObject jsonObject=InsuranceMapper.jsonInsurance();
		
		
		
		Insurance insurance=new Insurance();
		
		
		
		insurance.setApplication_no("20151");
		insurance.setInsurance_plan(jsonObject.getString("insurance_plan"));
		insurance.setName(jsonObject.getString("name"));
		insurance.setAge(jsonObject.getInt("age"));
		insurance.setGender(jsonObject.getString("gender"));
		insurance.setMobile_no(jsonObject.getString("mobile_no"));
		insurance.setEmail(jsonObject.getString("email"));
		insurance.setTrip_details(jsonObject.getString("tripdetails"));
		insurance.setType_of_person(jsonObject.getString("applyfor"));
		insurance.setNo_of_people(jsonObject.getInt("no_of_people"));
		insurance.setJourny_date(jsonObject.get("journy_date"));
		insurance.setReturn_date(jsonObject.get("return_date"));
		insurance.setDirection(jsonObject.getString("direction"));
		insurance.setProof_doc(jsonObject.getString("proof_doc"));
		insurance.setThread_id(jsonObject.getLong("thread"));
		//result=insuranceDao.createTravelInsurance(insurance);
		
		//emailAlert.sendEmailAlert(jsonObject.getString("email").toString());
		Mockito.when(sequenceDao.getNextSequence("eda", "insurance_id")).thenReturn(1L);
		//Mockito.when(insuranceDao.createTravelInsurance(insurance)).thenReturn(true);
		Assert.assertEquals(true, insuranceService.createTravelInsurance(jsonObject));
	}
	@Test
	public void testcreateLocation() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("location","SARAVANAMPATTI" );
		Location location=new Location();
		location.setLocation("SARAVANAMPATTI");
		//Mockito.when(insuranceDao.createLocation(location)).thenReturn(true);
		Assert.assertEquals(true, insuranceService.createLocation(jsonObject));
		
	}
	@Test
	public void testGetLocation()
	{
		Assert.assertEquals("SARAVANAMPATTY", insuranceService.getLocation());
	}
	@AfterClass
	public static void tearDown()
	{
		Assert.assertEquals(true, insuranceService.deleteInsurance("20151"));
		Assert.assertEquals(true,insuranceService.deleteLocation("SARAVANAMPATTI"));
	}
}

