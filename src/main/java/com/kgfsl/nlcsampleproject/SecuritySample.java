package com.kgfsl.nlcsampleproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class SecuritySample {

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) {
	    try {
	    	String auth = "user" + ":" + "user";
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			String authHeader = "Basic " + new String(encodedAuth);
			System.out.println(authHeader);
			
			
	        HttpClient Client=new DefaultHttpClient();

	        HttpGet httpGet = new HttpGet("http://10.100.4.128:8080/eda-web-service/get/");
	        httpGet.setHeader("Authorization", authHeader);
	        HttpResponse response = Client.execute(httpGet);

	        System.out.println("response = " + response);

	        BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        StringBuilder responseString = new StringBuilder();
	        String line = "";
	        while ((line = breader.readLine()) != null) {
	            responseString.append(line);
	        }
	        breader.close();
	        String responseStr = responseString.toString();
	        System.out.println("responseStr = " + responseStr);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
