package com.eda.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.model.GcmReg;
import com.eda.rest.service.IEmailService;
import com.eda.rest.service.IThreadService;


@RestController
public class EmailController extends AbstractController{

	private Logger logger = Logger.getLogger(EmailController.class);
	
	
	
	@Autowired
	IThreadService threadService;
	@Autowired
	IEmailService emailService;
	
	
	@RequestMapping(value = "/email/send/", method = RequestMethod.POST)
	public String emailSend(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		
		JSONObject actionidJson=readJSONData(request);
		return emailService.listContent(actionidJson);
	}
	
	@RequestMapping(value = "/email/create/", method = RequestMethod.POST)
	public boolean receiveMailContent(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		JSONObject receivedJson=readJSONData(request);
		return emailService.createEmailAction(receivedJson);
		
	}
	
	
	@RequestMapping(value="/email/updatestatus/",method=RequestMethod.POST)
	public boolean updateActionStatus(Long action_id,HttpServletRequest request) throws IOException, JSONException
	{
		JSONObject jsonAction=readJSONData(request);
		return emailService.updateActionStatus(jsonAction);
	}
	
	@RequestMapping(value="/send/direct/email",method=RequestMethod.POST)
	public void sendFloralDeliveryAlert(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject jsonObject=readJSONData(request);
		emailService.sendEmailDirectly(jsonObject);
	}
	
	@RequestMapping(value="/autocomplete/phone/{thread_id}/",method=RequestMethod.GET)
	public String autoCompletePhone(@PathVariable Long thread_id) throws JSONException
	{
		String device_id=threadService.deviceId(thread_id);
		JSONArray jsonArray=new JSONArray();
		if(!device_id.equals(null)){
			List<GcmReg> list=emailService.listPhone(device_id);
			logger.info("List Contact phone:::::"+list);
			int length=list.get(0).getContact().length();
			logger.info("LIST SIZE:::::::"+length);
			
			for(int i=0;i<length;i++)
			{
				JSONObject jsonobj=new JSONObject();
				jsonobj.put("text", list.get(0).getContact().getJSONObject(i).getString("name"));
				JSONObject phone=list.get(0).getContact().getJSONObject(i).getJSONObject("phone_numbers");
				logger.info("JSON PHONE::::::::::::"+phone);
				String p=phone.optString("MOBILE");
				
				//.getString("MOBILE");
				
				if(!p.equals(""))
				{
					jsonobj.put("value", p);	
				}
				
				jsonArray.put(jsonobj);
				
			}
			
		}
		return jsonArray.toString();
		
		
	}
	@SuppressWarnings("unused")
	@RequestMapping(value="/autocomplete/email/{thread_id}/",method=RequestMethod.GET)//headers="Accept=application/json"
	public String autoComplete1(@PathVariable Long thread_id) throws JSONException
	{
		logger.info("THREAD ID:::::::::::::::::::::::::::::::"+thread_id);
		String device_id=threadService.deviceId(thread_id);
		logger.info("DEVICE ID:::::::::::::::::::::::::::::::::::"+device_id);
		JSONArray jsonArray=new JSONArray();
		JSONArray j=new JSONArray();
		if(!device_id.equals(null)){
			
		
		List<GcmReg> list=emailService.listEmail(device_id);
		logger.info("SIZE:::::"+list.size());
		
		
		
		
		int len=list.get(0).getContact().length();
		logger.info("LENGTH:::::"+len);
	
		
		for(int i=0;i<len;i++)
		{
			JSONObject jsonObject=new JSONObject();
			String id="id";
			String name="name";
			jsonObject.put(id, list.get(0).getContact().getJSONObject(i).getString("name"));
			jsonObject.put(name,list.get(0).getContact().getJSONObject(i).getString("email_id") );
			
			String email=list.get(0).getContact().getJSONObject(i).getString("email_id").toString();
			
			if(!email.equals(""))
			{
				String e=email.substring(0, email.length()-1);
				jsonArray.put(email);
			}
			
			
			JSONObject json=new JSONObject();
			
			json.put("text",list.get(0).getContact().getJSONObject(i).getString("email_id"));
			json.put("value", i);
			
			j.put(json);
			
			
		}
		}
		logger.info("JSON ARRAY:::::::::"+jsonArray);
		
		return jsonArray.toString();
	}

	
	
}
