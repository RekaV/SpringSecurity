package com.eda.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eda.rest.model.Agent;
import com.eda.rest.model.MsgThread;
import com.eda.rest.service.IThreadService;

@RestController
public class ThreadController extends AbstractController {

	@Autowired
	IThreadService threadService;

	private Logger logger = Logger.getLogger(ThreadController.class);

	@RequestMapping(value = "/thread/updatestatus/{thread_id}", method = RequestMethod.GET)
	public boolean updateThreadStatus(@PathVariable String thread_id) {

		Long threadid = Long.parseLong(thread_id);
		boolean result = threadService.updateStatusInThread(threadid);
		logger.info("SUCCESSFULLY UPDATED");
		return result;
	}

	@RequestMapping(value = "/thread/getstatus/", produces = { "application/json" }, method = RequestMethod.POST)
	public String getThreadStatus(String thread_id, HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {

		JSONObject jsonGetThread = readJSONData(request);
		return threadService.getThreadStatus(jsonGetThread);

	}

	/*
	 * @RequestMapping(value = "/thread/listthread/", method =
	 * RequestMethod.POST) public String listThread(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException, JSONException {
	 * JSONObject jsonMsg = new JSONObject(); JSONObject jsonAgentId =
	 * readJSONData(request); Long agent_id = jsonAgentId.getLong("agent_id");
	 * 
	 * List<MsgThread> listThread = threadService.listThread(agent_id);
	 * System.out.println("Agent Id ::" + agent_id); System.out.println(
	 * "list size ::" + listThread.size());
	 * 
	 * if (listThread.size() != 0) { Long threadid =
	 * listThread.get(0).getThread_id(); System.out.println("THREAD ID:::" +
	 * threadid); List<MsgThread> list_all_thread = threadService
	 * .listAllThread(agent_id); List<Message1> msg =
	 * threadService.listMessage(threadid); JSONArray msgArray = new
	 * JSONArray(); for (int i = 1; i < msg.size(); i++) { JSONObject msgObj =
	 * new JSONObject(); msgObj.put("msg_id", msg.get(i).getMsg_id());
	 * msgObj.put("msg_content", msg.get(i).getMsg_content());
	 * msgObj.put("direction", msg.get(i).getDirection()); msgObj.put("path",
	 * msg.get(i).getPath()); msgObj.put("thread_id",
	 * msg.get(i).getThread_ref_id()); msgObj.put("time_stamp",
	 * msg.get(i).getTime_stamp()); msgArray.put(msgObj); }
	 * 
	 * jsonMsg.put("thread_id", threadid); jsonMsg.put("agent_id", agent_id);
	 * jsonMsg.put("msgList", msgArray); jsonMsg.put("all_thread",
	 * list_all_thread);
	 * 
	 * }
	 * 
	 * 
	 * return jsonMsg.toString(); }
	 */
	/**
	 * ********* METHODS FOR WEB APPLICATION **************
	 **/

	@RequestMapping(value = "/thread/listthread1/", method = RequestMethod.POST)
	public List<MsgThread> listThread1(HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException {
		JSONObject jsonAgentId = readJSONData(request);
		List<MsgThread> listThread = threadService.listThread(jsonAgentId);
		return listThread;

	}

	@RequestMapping(value = "/thread/listallthread/", method = RequestMethod.POST)
	public List<MsgThread> listAllThread(HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException {
		JSONObject jsonAgentId = readJSONData(request);

		List<MsgThread> listAllThread = threadService.listAllThread(jsonAgentId);
		return listAllThread;

	}

	@RequestMapping(value = "/agent/updatestatus/", method = RequestMethod.POST)
	public String updateAgentStatus(Long agentId, HttpServletRequest request) throws IOException, JSONException {
		JSONObject jsonAgentId = readJSONData(request);
		return threadService.updateAgentStatus(jsonAgentId);
	}

	@RequestMapping(value = "/agent/insert/", method = RequestMethod.POST)
	public boolean agentInsert(HttpServletRequest request) throws IOException, JSONException {
		JSONObject agentJson = readJSONData(request);
		return threadService.agentInsert(agentJson);
	}

	@RequestMapping(value = "/agent/list/", method = RequestMethod.POST)
	public List<Agent> listAgent() {
		return threadService.listAgent();
	}
	@RequestMapping(value="/agent/update/",method=RequestMethod.POST)
	public boolean updateAgent(HttpServletRequest request) throws JSONException
	{
		JSONObject jsonAgent=readJSONData(request);
		return threadService.updateAgent(jsonAgent);
	}
	@RequestMapping(value = "/thread/allthreadcount/", method = RequestMethod.POST)
	public String totalNoOfThreads() {
		return String.valueOf(threadService.totalNoOfThreads());
	}

	@RequestMapping(value = "/thread/unpickedthreads/", method = RequestMethod.POST)
	public String totalNoOfUnPickedThreads() {
		return String.valueOf(threadService.totalNoOfUnPickedThreads());
	}

	@RequestMapping(value = "/thread/pickedthreads/", method = RequestMethod.POST)
	public String totalNoOfWorkingThreads() {
		return String.valueOf(threadService.totalNoOfWorkingThreads());
	}

	@RequestMapping(value = "/thread/closedthreads/", method = RequestMethod.POST)
	public String totalNoOfClosedThreads() {
		return String.valueOf(threadService.totalNoOfClosedThreads());
	}
}
