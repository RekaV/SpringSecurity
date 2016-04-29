package com.eda.rest.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.model.Agent;
import com.eda.rest.model.Message1;
import com.eda.rest.service.IMessageService;
import com.google.android.gcm.server.Result;

import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;

@RestController
@PropertySource("classpath:db.properties")
public class MessageController extends AbstractController{

	private static final String SAVE_DIR = System.getProperty("catalina.home")
			+ "/webapps/test/";
	private static final int BUFFER_SIZE = 4096;

	@Value("${eda.service.version}")
	String appVersion;
	
	private Logger logger = Logger.getLogger(MessageController.class);
	@Autowired
	IMessageService messageService;
	@RequestMapping(value="/get/",method=RequestMethod.GET)
	
	public String getCollectionNames()
	{
		String name=messageService.getCollectionName();
		System.out.println("NAMES:::::::::::::"+name);
		String list=messageService.getListOfAgent().toString();
		System.out.println("NAMES:::::::::::::"+name+"LIST::::::::::::::::"+list);
		return name+"HELLO";
		
	}
	@RequestMapping(value="/list/agent/",method=RequestMethod.GET)
	public String listAgent()
	{
		List<Agent> list=messageService.listAgent();
		
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<list.size();i++)
		{
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("agent_id",list.get(i).getId());
				jsonObject.put("agent_name",list.get(i).getAgent_name());
				jsonObject.put("password",list.get(i).getPassword());
				jsonObject.put("status",list.get(i).getStatus());
				
				jsonArray.put(jsonObject);
				
			} catch (JSONException e) {
			
				e.printStackTrace();
			}
			
		}
		System.out.println("List agent::::"+jsonArray.toString());
		
		return jsonArray.toString();
		
	}
	

	@RequestMapping
	(value = "/", method = RequestMethod.GET)
	public String home() {
		// eda.autoAlloc();
	
		String dbClass = "succ";
		String dbStatus = "Available";
		String connectionCheck = messageService.getCollectionName();
		if (connectionCheck.equals(null)) {
			dbClass = "fail";
			dbStatus = "Unable to connect";
		}
		
		//to display the health check status on browser
		String result = "<!DOCTYPE html><html><head><title>EDA Web Service</title>" +
		"<style>body {font-family: arial;}.succ {color: green;}.fail{color: red;}</style>" +
		"</head>" +
		"<body>" +
		"<div style='margin:15% 25%; width:50%;'>" +
			"<div style='border:1px solid #dfdfdf; padding: 10px;'>" +
			"<h2>Eda Web Service - Health Check </h2>" +
			"<p><b>Version: " + appVersion + " <br/><br/>" +
			"Website status: <span class='succ'>Up and running successfully :-)</span><br/><br/>" +
			"Database service: <span class='" + dbClass + "'>" + dbStatus + "</span><br/>" +
			"</b></p></div></div>" +
		"</body></html>";

		
		//String result="<html><body><h1>Eda Web Service version 2.3.2 running successfully...!!!!!!!!!!!!!!!!</h1></body></html>";
		return result;
	}

		
	@RequestMapping(value = "/message/create/", produces = { "application/json" }, method = RequestMethod.POST)
	public String createMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {

		JSONObject jsonMessage = readJSONData(request);
		JSONObject out=messageService.createMessage(jsonMessage);
		
		String direction=out.getString("direction");
		String msg_content=out.getString("msg_content");
		Long thread_ref_id=out.getLong("thread_ref_id");
		if (direction.equals("O")) {

			Result result = null;
			result = sendGcmPushNotification(thread_ref_id, msg_content);
			request.setAttribute("pushStatus", result.toString());
		}
		JSONObject returnToApp = new JSONObject();
		returnToApp.put("msg_id", out.getLong("msg_id"));
		returnToApp.put("thread_ref_id", out.getLong("thread_ref_id"));
			
		return returnToApp.toString();
	}
	
		
	@RequestMapping(value = "/servlet/audio/", method = RequestMethod.POST)
	public void receiveVoiceFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			IllegalArgumentException, InputFormatException, EncoderException {

		String fileName = request.getHeader("fileName");
		File saveFile = new File(SAVE_DIR + fileName);

		logger.info("===== Begin headers =====");
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String headerName = names.nextElement();
			logger.info(headerName + " = "
					+ request.getHeader(headerName));
		}
		logger.info("===== End headers =====\n");

		InputStream inputStream = request.getInputStream();

		FileOutputStream outputStream = new FileOutputStream(saveFile);

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		//logger.info("Receiving data...");
		logger.info("Receiving data...");
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		logger.info("Data received.");
		outputStream.close();
		inputStream.close();

		logger.info("File written to: " + saveFile.getAbsolutePath());

		File targetFile = AudioCoversion.convert(saveFile, fileName);
		logger.info("CONVERTED FILE::::" + targetFile.toString());
		saveVoiceFile(fileName, targetFile, response);

	}

	private void saveVoiceFile(String fileName, File saveFile,
			HttpServletResponse response) throws IOException {
		int dot = fileName.indexOf(".");
		String msgid = fileName.substring(0, dot);
		logger.info("MSG ID:::" + msgid);

		Long msg_id = Long.parseLong(fileName.substring(0, dot));

		messageService.updatePathForVoiceFile(msg_id,
				saveFile.getAbsolutePath());

		response.getWriter().print("UPLOAD DONE");

		response.getWriter().print(System.getProperty("catalina.home"));
	}
	/**
	 * ********* METHODS FOR WEB APPLICATION **************
	 **/
	
	@RequestMapping(value = "/message/listmessage/", method = RequestMethod.POST)
	public List<Message1> listMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonThreadId = readJSONData(request);
		Long thread_id = jsonThreadId.getLong("thread_id");
		List<Message1> listAllMsg = messageService.listMessage(thread_id);
		return listAllMsg;

	}
	@RequestMapping(value="/message/count/",method=RequestMethod.POST)
	public Long messageCount(HttpServletRequest request)
	{
		JSONObject jsonThreadId = readJSONData(request);
		Long thread_id = jsonThreadId.getLong("thread_id");
		Long msgCount=messageService.messageCount(thread_id);
		return msgCount;
		
	}
}
