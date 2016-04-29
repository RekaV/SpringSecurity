package com.eda.rest.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@PropertySource("classpath:db.properties")
@Configuration
public class SpringMongoConfiguration {
	/*String dbname = "IbmCloud_1vgfo81a_7a2dekiv";
	String user = "IbmCloud_1vgfo81a_7a2dekiv_s3jmf4eq";
	String pwd = "rK-1KZlElewon-WAKR8k1XPEVILPswi3";
	String host = "ds029814.mongolab.com:29814";*/
	
	@Value("${ibm.mongodb.database}")
	String dbname;
	@Value("${ibm.mongodb.username}")
	String user;
	@Value("${ibm.mongodb.password}")
	String pwd;
	@Value("${ibm.mongodb.host}")
	String host;
	@Value("${ibm.mongodb.port}")
	String port;
	@Bean
	public Mongo mongo() throws Exception {

		MongoClientURI uri = new MongoClientURI(
				"mongodb://IbmCloud_1vgfo81a_7a2dekiv_s3jmf4eq:rK-1KZlElewon-WAKR8k1XPEVILPswi3@ds029814.mongolab.com:29814/IbmCloud_1vgfo81a_7a2dekiv");
		return new MongoClient(uri);
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		String url1="mongodb://" + user + ":" + pwd + "@" + host + ":" + port + "/" + dbname;
		System.out.println("URL IN MONGO11::"+url1);
		//MongoClientURI uri = new MongoClientURI("mongodb://" + user + ":" + pwd + "@" + host + "/" + dbname);
		MongoClientURI uri = new MongoClientURI(url1);
		return new SimpleMongoDbFactory(new MongoClient(uri), dbname);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		System.out.println("SPRING MONGO:::::::::::::::"+mongoTemplate);
		
		
		return mongoTemplate;

	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
