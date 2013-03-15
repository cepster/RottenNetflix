package com.rotten;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.rotten.json.JSONArray;
import com.rotten.json.JSONException;
import com.rotten.json.JSONObject;

public class RottenTomatoGET {
	
	public static ArrayList<RottenTomatoResult> getResults(String input) {
		
		try {
			input = URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ArrayList<RottenTomatoResult> resultSet = new ArrayList<RottenTomatoResult>();
		
		String flixsterResult = getResponse(input);  
	    System.out.println(flixsterResult);
		String substr = flixsterResult.substring(flixsterResult.lastIndexOf("movies\":") + 8, flixsterResult.lastIndexOf("]") + 1);

	    JSONArray a;
		try {
			a = new JSONArray(substr);
			for(int i=0; i<a.length(); i++){
		    	JSONObject o = a.getJSONObject(i);

		    	RottenTomatoResult r = new RottenTomatoResult(o.getString("title"));
		    	r.setYear(o.getInt("year"));
		    	
		    	JSONObject ratingsObject = o.getJSONObject("ratings");
		    	JSONArray castArray = o.getJSONArray("abridged_cast");

		    	ArrayList<String> castResultList = new ArrayList<String>();
		    	for(int j=0; j<castArray.length() && j < 3; j++){
		    		JSONObject o2 = castArray.getJSONObject(j);
		    		castResultList.add(o2.getString("name"));
		    	}
		    	
		    	r.setCast(castResultList);
		    	r.setCriticsRating(ratingsObject.getDouble("critics_score"));
		    	r.setAudienceRating(ratingsObject.getDouble("audience_score"));
		    	r.setDescription(o.getString("synopsis"));
		    	
		    	resultSet.add(r);
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    return resultSet;
	}
	
	public static String getResponse(String input){
		URL url;
	    HttpURLConnection conn;
	    BufferedReader rd;
	    String line;
	    String result = "";
	      try {
	         url = new URL("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + GlobalVars.getFlixsterKey()
					   + "&q=" + input);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      return result;
	}

}
