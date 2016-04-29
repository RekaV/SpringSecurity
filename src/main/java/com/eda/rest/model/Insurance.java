package com.eda.rest.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="eda_insurance")
public class Insurance {

	@Id
	String id;
	
	String application_no;
	String insurance_plan;
	String name;
	int age;
	String gender;
	String mobile_no;
	String email;
	String type_of_person;
	int no_of_people;
	String trip_details;
	String direction;
	Object journy_date;
	Object return_date;
	String proof_doc;
	Long thread_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInsurance_plan() {
		return insurance_plan;
	}
	public void setInsurance_plan(String insurance_plan) {
		this.insurance_plan = insurance_plan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType_of_person() {
		return type_of_person;
	}
	public void setType_of_person(String type_of_person) {
		this.type_of_person = type_of_person;
	}
	public int getNo_of_people() {
		return no_of_people;
	}
	public void setNo_of_people(int no_of_people) {
		this.no_of_people = no_of_people;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTrip_details() {
		return trip_details;
	}
	public void setTrip_details(String trip_details) {
		this.trip_details = trip_details;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Long getThread_id() {
		return thread_id;
	}
	public void setThread_id(Long thread_id) {
		this.thread_id = thread_id;
	}
	public Object getJourny_date() {
		return journy_date;
	}
	public void setJourny_date(Object journy_date) {
		this.journy_date = journy_date;
	}
	public Object getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Object return_date) {
		this.return_date = return_date;
	}
	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}
	public String getProof_doc() {
		return proof_doc;
	}
	public void setProof_doc(String proof_doc) {
		this.proof_doc = proof_doc;
	}
	public String getApplication_no() {
		return application_no;
	}
	public void setApplication_no(String application_no) {
		this.application_no = application_no;
	}
	
	
}
