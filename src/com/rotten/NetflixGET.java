package com.rotten;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Encoder;

public class NetflixGET{
	
	@SuppressWarnings("unchecked")
	public static ArrayList<NetflixResult> getResults(String input) {
		String response = getResponse(input);
		
		ArrayList<NetflixResult> results =  new ArrayList<NetflixResult>();
		
		try{
			Document responseDocument = DocumentHelper.parseText(response);
			
			System.out.println(responseDocument.asXML());
			
			List<Element> elements = responseDocument.getRootElement().elements();
			for(Element e : elements){
				if(e.getName().equals("catalog_title")){

					Element titleElement = e.element("title");
					String name = titleElement.attributeValue("regular");
					
					Element ratingElement = e.element("average_rating");
					String rating = ratingElement.getText();
					
					Element releaseYear = e.element("release_year");
					int year = Integer.valueOf(releaseYear.getText());
					
					Element boxArtElement = e.element("box_art");
					String boxArt = boxArtElement.attributeValue("medium");
					
					NetflixResult result = new NetflixResult(name, rating);
					result.setImageURL(boxArt);
					result.setYear(year);
					
//					if(name.toLowerCase().contains(input.toLowerCase())){
						results.add(result);
//					}
				}
			}
		}
		catch(DocumentException e){
			
		}
				
		return results;
	}
	
	private static String buildNetflixURL(String input){
		
		String verb = "GET";
		String baseURL = "http://api-public.netflix.com/catalog/titles";
		String parameters = "oauth_consumer_key=" + GlobalVars.getNetflixKey()
			       		  + "&oauth_nonce=" + NetflixGET.encode(generateNonce())
			       		  + "&oauth_signature_method=HMAC-SHA1"
			       		  + "&oauth_timestamp=" + generateTimestamp()
			       		  + "&oauth_version=1.0"
			       		  + "&term=" + NetflixGET.encode(input);
					       
		String signature = generateSignature(verb, baseURL, parameters);
		
		String url = baseURL + "?" + parameters + "&oauth_signature=" + signature;
		return url;
	}
	
	private static String generateSignature(String verb, String URL, String parameters){
		try{
			String encodedURL = NetflixGET.encode(URL);
			String encodedParameters = NetflixGET.encode(parameters);

			String baseString = verb + "&" + encodedURL + "&" + encodedParameters;
			
			Mac mac = Mac.getInstance("HmacSHA1");
			String encodedSecret = encode(GlobalVars.getNetflixSecret()) + "&";
			SecretKeySpec secret = new SecretKeySpec(encodedSecret.getBytes(), mac.getAlgorithm());
			mac.init(secret);
			byte[] digest = mac.doFinal(baseString.getBytes());
			
			BASE64Encoder encoder = new BASE64Encoder();
			
			String unEncodedSignature = encoder.encode(digest);
			
			String encodedSignature = NetflixGET.encode(unEncodedSignature);

			return encodedSignature;
		}
		catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		catch(InvalidKeyException e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static String generateNonce(){
		try{
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[32];
			sr.nextBytes(bytes);
			
			byte[] nonce = sr.generateSeed(32);
			
			return nonce.toString();
		}
		catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return "";
	}
	
	private static String generateTimestamp(){
		java.util.Date today = new java.util.Date();
	    
	    return String.valueOf(today.getTime()/1000);
	}
	
	private static String getResponse(String input){
		URL url;
	    HttpURLConnection conn;
	    BufferedReader rd;
	    String line;
	    String result = "";
	      try {
	         url = new URL(buildNetflixURL(input));
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
	
	private static String encode(String s){
		//! * ' ( ) ; : @ & = + $ , / ? % # [ ]
		//%21 %2A %27 %28 %29 %3B %3A %40 %26 %3D %2B %24 %2C %2F %3F %25 %23 %5B %5D
		
		s = s.replace("%", "%25");
		s = s.replace("!", "%21");
		s = s.replace("*", "%2A");
		s = s.replace("'", "%27");
		s = s.replace("(", "%28");
		s = s.replace(")", "%29");
		s = s.replace(";", "%3B");
		s = s.replace(":", "%3A");
		s = s.replace("@", "%40");
		s = s.replace("&", "%26");
		s = s.replace("=", "%3D");
		s = s.replace("+", "%2B");
		s = s.replace("$", "%24");
		s = s.replace(",", "%2C");
		s = s.replace("/", "%2F");
		s = s.replace("?", "%3F");
		s = s.replace("#", "%23");
		s = s.replace("[", "%5B");
		s = s.replace("]", "%5D");
		s = s.replace(" ", "%2B");
		
		return s;
	}

}
