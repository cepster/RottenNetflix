package com.rotten;

import java.util.ArrayList;

public class ResultsHolder {
	
	private ArrayList<RottenTomatoResult> rtResults;
	private ArrayList<NetflixResult> nfResults;
	private ArrayList<CompositeResult> exactMatches;
	private ArrayList<CompositeResult> partialMatches;
	
	public ResultsHolder(){
		rtResults = new ArrayList<RottenTomatoResult>();
		nfResults = new ArrayList<NetflixResult>();
		exactMatches = new ArrayList<CompositeResult>();
		partialMatches = new ArrayList<CompositeResult>();
	}
		
	public void doWork(String input){
		
		nfResults = NetflixGET.getResults(input);
		rtResults = RottenTomatoGET.getResults(input);
		exactMatches = new ArrayList<CompositeResult>();
		partialMatches = new ArrayList<CompositeResult>();
		
		for(NetflixResult nfResult : nfResults){
			for(RottenTomatoResult rtResult: rtResults){
				String nfName = nfResult.getName().toLowerCase();
				String rtName = rtResult.getName().toLowerCase();
				if(parseTitle(nfName).equals(parseTitle(rtName)) && nfResult.getYear() == rtResult.getYear()){
					if((nfName.equals(input.toLowerCase()) || rtName.equals(input.toLowerCase()))){
						CompositeResult result = new CompositeResult(nfResult.getName(), rtResult.getDescription());
						result.setNfRating(nfResult.getRating());
						result.setRtAudienceRating(String.valueOf(rtResult.getAudienceRating()));
						result.setRtCriticsRating(String.valueOf(rtResult.getCriticsRating()));
						result.setImageURL(nfResult.getImageURL());
						result.setCast(rtResult.getCast());
						result.setYear(rtResult.getYear());
						exactMatches.add(result);
					}
					else{
						CompositeResult result = new CompositeResult(nfResult.getName(), rtResult.getDescription());
						result.setNfRating(nfResult.getRating());
						result.setRtAudienceRating(String.valueOf(rtResult.getAudienceRating()));
						result.setRtCriticsRating(String.valueOf(rtResult.getCriticsRating()));
						result.setImageURL(nfResult.getImageURL());
						result.setCast(rtResult.getCast());
						result.setYear(rtResult.getYear());
						partialMatches.add(result);
					}
				}
			}
		}
		
		System.out.println("-------Exact Matches-------");
		for(CompositeResult r: exactMatches){
			System.out.println(r.toString());
		}
		
		System.out.println("-------Partial Matches-----");
		for(CompositeResult r: partialMatches){
			System.out.println(r.toString());
		}
		
		System.out.println("-------Netflix Results-----");
		for(NetflixResult r: nfResults){
			System.out.println(r.toString());
		}
		
		System.out.println("-------Rotten Tomatoes Results-------");
		for(RottenTomatoResult r: rtResults){
			System.out.println(r.toString());
		}
	}
	
	private String parseTitle(String title){
		title = title.replace(":", "");
		title = title.replace("-", "");
		title = title.replace(" ", "");
		
		return title;
	}

	public ArrayList<RottenTomatoResult> getRtResults() {
		return rtResults;
	}

	public void setRtResults(ArrayList<RottenTomatoResult> rtResults) {
		this.rtResults = rtResults;
	}

	public ArrayList<NetflixResult> getNfResults() {
		return nfResults;
	}

	public void setNfResults(ArrayList<NetflixResult> nfResults) {
		this.nfResults = nfResults;
	}

	public ArrayList<CompositeResult> getExactMatches() {
		return exactMatches;
	}

	public void setExactMatches(ArrayList<CompositeResult> exactMatches) {
		this.exactMatches = exactMatches;
	}

	public ArrayList<CompositeResult> getPartialMatches() {
		return partialMatches;
	}

	public void setPartialMatches(ArrayList<CompositeResult> partialMatches) {
		this.partialMatches = partialMatches;
	}
}
