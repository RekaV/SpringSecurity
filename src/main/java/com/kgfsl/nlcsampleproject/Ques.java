package com.kgfsl.nlcsampleproject;

import java.util.ArrayList;
import java.util.List;

public class Ques {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> listInsurance=new ArrayList<String>();
		listInsurance.add("need a travel insurance");
		listInsurance.add("I need travel insurance");
		listInsurance.add("want travel insurance");
		listInsurance.add("I want a travel insurance");
		listInsurance.add("I get travel insurance");
		listInsurance.add("I get travel insurance");
		listInsurance.add("buy a travel insurance");
		listInsurance.add("I want to buy travel insurance");
		
		String q1="I need travel insurance";
		String q2="I need a travel insurance";
		String q3="I want a travel insurance";
		String q4="Can i get a travel insurance";
		
		for(int i=0;i<listInsurance.size();i++)
		{
			/*System.out.println(q1.contains(listInsurance.get(i)));	
			System.out.println(q2.contains(listInsurance.get(i)));	
			System.out.println(q3.contains(listInsurance.get(i)));	
			System.out.println(q4.contains(listInsurance.get(i)));	
			System.out.println("**************");*/
			if(q1.contains(listInsurance.get(i)))
			{
				System.out.println("TRUE"+listInsurance.get(i));
			}
			
		}
		
	}
}
