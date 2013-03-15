package com.rotten;

import java.util.ArrayList;

public class CompositeResult {
	private String name;
	private String description;
	private String nfRating;
	private String rtAudienceRating;
	private String rtCriticsRating;
	private String imageURL;
	private int year;
	private ArrayList<String> cast;
	
	public CompositeResult(String name, String description){
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNfRating() {
		return nfRating;
	}

	public void setNfRating(String nfRating) {
		this.nfRating = nfRating;
	}

	public String getRtAudienceRating() {
		return rtAudienceRating;
	}

	public void setRtAudienceRating(String rtAudienceRating) {
		this.rtAudienceRating = rtAudienceRating;
	}

	public String getRtCriticsRating() {
		return rtCriticsRating;
	}

	public void setRtCriticsRating(String rtCriticsRating) {
		this.rtCriticsRating = rtCriticsRating;
	}
	
	public String toString(){
		return getName() + "(" + getYear() + ").  Netflix Rating: " + getNfRating() + ".  RT Audience Rating: " + getRtAudienceRating() + ".  RT Critics Rating: " + getRtCriticsRating();
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public ArrayList<String> getCast() {
		return cast;
	}

	public void setCast(ArrayList<String> cast) {
		this.cast = cast;
	}
	
	public String castToString(){
		String returnStr = getName() + ": ";
		if(cast.size() == 0){
			returnStr += " no cast information available";
		}
		else{
			for(int i=0; i<cast.size(); i++){
				if(i == cast.size()-2){
					returnStr += ", ";
				}
				if(i == cast.size()-1 && cast.size() != 1){
					returnStr += " and ";
				}
				returnStr += cast.toArray()[i];
			}
		}
			
		return returnStr;
	}
	
	public boolean isFresh(){
		try{
			if(Double.valueOf(rtCriticsRating) > 75){
				return true;
			}
		}
		catch(NumberFormatException e){
			
		}
		
		return false;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}	
