package com.rotten;

public class NetflixResult {
	private String name;
	private String rating;
	private int year;
	private String imageURL;
	
	public NetflixResult(String name, String rating){
		this.name = name;
		this.rating = rating;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setRating(String rating){
		this.rating = rating;
	}
	
	public String getRating(){
		return rating;
	}
	
	public String toString(){
		return getName() + "(" + getYear() + ").  Rating: " + getRating();
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
