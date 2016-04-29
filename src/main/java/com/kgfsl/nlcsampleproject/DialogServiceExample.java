/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kgfsl.nlcsampleproject;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Conversation;
import com.ibm.watson.developer_cloud.dialog.v1.model.ConversationData;
import com.ibm.watson.developer_cloud.dialog.v1.model.Dialog;
import com.ibm.watson.developer_cloud.dialog.v1.model.DialogContent;

@SuppressWarnings("unused")
public class DialogServiceExample {
	public static void main(String[] args) throws URISyntaxException, FileNotFoundException, UnsupportedEncodingException {
		DialogService service = new DialogService();
		service.setUsernameAndPassword("3b1c2fce-016c-4c21-b8e1-1387965bcfc5", "PEGLOaNItcFU");

		System.out.println("SERVICES::"+service);
		
		/** CREATE NEW DIALOGUE **/
		/*File dialogFile = new File("src/main/resources/tarvel_sample.xml");
		//String dialogName = ""+UUID.randomUUID().toString().substring(0, 15);
		String dialogName = "travel_insurance";
		Dialog newDialog = service.createDialog(dialogName, dialogFile);
		System.out.println("ID:::::"+newDialog.getId());
		 */				
		
		/** UPDATE DIALOG **/
		File dialogFile = new File("src/main/resources/tarvel_sample.xml");
		Dialog newDialog = service.updateDialog("1f096294-9328-4046-abe3-575f32ca2334", dialogFile);
		
		/** GET DIALOGUE CONTENT **/
		List<Dialog> dialogs = service.getDialogs();
		//System.out.println(dialogs);
		String dialogId=dialogs.get(1).getId();
		System.out.println(dialogId);
		/*List<DialogContent> dialogContent = service.getContent(dialogId);
		System.out.println(dialogContent);*/
		
		
		/** CREATE CONVERSATION **/
		Conversation conversation=service.createConversation(dialogId);
		System.out.println("CONVERSATION:::"+conversation);
	
		Map<String, Object> params = new HashMap<String,Object>();
		params.put(DialogService.DIALOG_ID, dialogId);
		params.put(DialogService.CLIENT_ID, conversation.getClientId());
		params.put(DialogService.CONVERSATION_ID, conversation.getId());
		params.put(DialogService.INPUT, "What do you have?");
		conversation = service.converse(params);
		System.out.println("CONVERSATION1:::"+conversation);
		
		
		Map<String, Object> params1 = new HashMap<String,Object>();
		params1.put(DialogService.DIALOG_ID, dialogId);
		params1.put(DialogService.CLIENT_ID, conversation.getClientId());
		params1.put(DialogService.CONVERSATION_ID, conversation.getId());
		params1.put(DialogService.INPUT, "I want silver");
		conversation = service.converse(params1);
		System.out.println("CONVERSATION2:::"+conversation);
		
		Map<String, Object> params2 = new HashMap<String,Object>();
		params2.put(DialogService.DIALOG_ID, dialogId);
		params2.put(DialogService.CLIENT_ID, conversation.getClientId());
		params2.put(DialogService.CONVERSATION_ID, conversation.getId());
		params2.put(DialogService.INPUT, "no");
		conversation = service.converse(params2);
		System.out.println("CONVERSATION3:::"+conversation);
		
	}
	
	
}
