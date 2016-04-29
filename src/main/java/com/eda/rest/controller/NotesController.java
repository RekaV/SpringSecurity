package com.eda.rest.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.model.dao.SequenceDao;
import com.eda.rest.service.INotesService;

@RestController
public class NotesController extends AbstractController{

	//private Logger logger = Logger.getLogger(NotesController.class);
	
	
	@Autowired
	SequenceDao sequenceDao;
	
	@Autowired
	INotesService notesService;
	
	@RequestMapping(value = "/notes/send/", method = RequestMethod.POST)
	public String emailSend(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		
		JSONObject actionidJson=readJSONData(request);
		return notesService.listContent(actionidJson);

	}
	
	@RequestMapping(value = "/notes/create/", method = RequestMethod.POST)
	public String receiveMailContent(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		JSONObject receivedJson=readJSONData(request);
		return notesService.createNotesAction(receivedJson);
		
	}
	
	@RequestMapping(value="/notes/updatestatus/",method=RequestMethod.POST)
	public void updateActionStatus(Long action_id,HttpServletRequest request) throws IOException, JSONException
	{
		JSONObject jsonAction=readJSONData(request);
		notesService.updateActionStatus(jsonAction);
		
	}
	
	
	
}
