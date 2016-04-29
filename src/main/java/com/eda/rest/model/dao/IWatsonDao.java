package com.eda.rest.model.dao;

import java.util.List;

import com.eda.rest.model.WatsonNlc;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

public interface IWatsonDao {

	public Classification getClassification(String questions);
	
	public List<WatsonNlc>  getAnswer(Long msg_id);
}
