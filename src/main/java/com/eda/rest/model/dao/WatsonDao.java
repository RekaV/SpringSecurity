package com.eda.rest.model.dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eda.rest.model.WatsonNlc;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

@Repository
public class WatsonDao extends AbstractSpringMongoDb implements IWatsonDao{

	@Override
	public Classification getClassification(String questions) {
		NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		service.setUsernameAndPassword("c4a4fcc1-2d95-47b4-9c7b-840d60d6b3ae", "lyyo1g1NDJP8");
		Classification classification = null;
		try {
			classification = service.classify("5E00F7x2-nlc-749", questions);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		System.out.println("CLASSIFICATION::::::"+classification.getTopClass());
		return classification;
	}

	@Override
	public List<WatsonNlc>  getAnswer(Long msg_id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("msg_id").is(msg_id)).fields().include("answer");
		List<WatsonNlc> list=mongoOperations.find(query,WatsonNlc.class);
		return list;
	}

}
