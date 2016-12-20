package com.starters;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.starters.json.LocationJson;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";
	private String latlongs;
	private String mainURL ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
	private String radius ="&radius=";
	private String type="&type=";
	private String keyword="&keyword=";
	private String keystring = "&key=";
	private String actualRadius;
	private String actualType;
	private String key="AIzaSyCQTLdXTkL9cqq16YMfTx3hnEx9jGG4nec";
	private String actualKeyword;
	private String json;
	private String finallats;
	private String finallongs;

	
	public HttpURLConnectionExample(String s,String rad,String type,String keyword)
	{
		latlongs =s;
		actualRadius =rad;
		actualType= type;
		actualKeyword= keyword;
	}

	// HTTP GET request
	public String sendGet() throws Exception {

		
		String url = mainURL+latlongs+radius+actualRadius+type+actualType+keyword+actualKeyword+keystring+key;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

	    BufferedReader streamReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    StringBuilder responseStrBuilder = new StringBuilder();

	    String inputStr;
	    while ((inputStr = streamReader.readLine()) != null)
	        responseStrBuilder.append(inputStr);
	    json = responseStrBuilder.toString();
	    streamReader.close();
	    Gson gson= new Gson();
	    LocationJson locationjson = gson.fromJson(json,LocationJson.class);
	    Random random =new Random();
	    int n = random.nextInt(locationjson.getResults().length);
	    	
	    	finallats=locationjson.getResults()[n].getGeometry().getLocation().getLat();
	    	finallongs=locationjson.getResults()[n].getGeometry().getLocation().getLng();
	    	return finallats+","+finallongs;
	    
	    
	}
	
}

