package com.rotten;

import java.util.ArrayList;

public class RottenTomatoResult {
	private String name;
	private String description;
	private double criticsRating;
	private double audienceRating;
	private int year;
	private ArrayList<String> cast;
	
	public RottenTomatoResult(String name){
		this.name = name;
		cast = new ArrayList<String>();
	}
	
	public RottenTomatoResult(String name, String description){
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
	public String getCriticsRating() {
		if(criticsRating == -1){
			return "N/A";
		}
		return String.valueOf(criticsRating);
	}
	public void setCriticsRating(double rating) {
		this.criticsRating = rating;
	}
	public String getAudienceRating() {
		if(audienceRating == -1){
			return "N/A";
		}
		return String.valueOf(audienceRating);
	}
	public void setAudienceRating(double rating) {
		this.audienceRating = rating;
	}
	
	public String toString(){
		return getName() + "(" + getYear() + ").  Audience Rating: " + getAudienceRating() + ".  Critics Rating: " + getCriticsRating();
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
			if(criticsRating >= 75){
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
