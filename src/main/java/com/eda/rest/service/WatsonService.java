package com.eda.rest.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.WatsonNlc;
import com.eda.rest.model.dao.IWatsonDao;

@Service
public class WatsonService extends GoogleService implements IWatsonService {

	@Autowired
	IWatsonDao watsonDao;

	@Override
	public String getClassification(JSONObject questions) {

		String question = null;
		try {
			question = questions.getString("question");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return watsonDao.getClassification(question).getTopClass();

	}

	@Override
	public String getAnswer(Long msg_id) {
	
		String answer=null;
		List<WatsonNlc> list=watsonDao.getAnswer(msg_id);
		if(list.size()!=0)
		{
		answer=list.get(0).getAnswer();	
		}
		return answer;
	}

}
