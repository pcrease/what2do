package com.what2do.foursquare;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Venue_Dataset {

	private Map<String, Foursquare_Venue> venue_Data = new HashMap<String, Foursquare_Venue>();
	
	//this is used to calculate the min and max as the objects are added to the venue dataset;
	private int maxCheckins=0;
	private int minCheckins=Integer.MAX_VALUE;
	private int maxUserCount=0;
	private int minUserCount=Integer.MAX_VALUE;
	private int maxTipCount=0;
	private int minTipCount=Integer.MAX_VALUE;
	private int maxLikes=0;
	private int minLikes=Integer.MAX_VALUE;
	private double maxRating=0.0f;
	private double minRating=Double.MAX_VALUE;

	public Map<String, Foursquare_Venue> getVenue_Data() {
		return venue_Data;
	}

	public void setVenue_Data(Map<String, Foursquare_Venue> venue_Data) {
		this.venue_Data = venue_Data;
	}
	
	public void addVenue(String id, Foursquare_Venue fv){
		Log.d("size", venue_Data.size()+"");
		
		if(fv.getCheckinCount()>maxCheckins){maxCheckins=fv.getCheckinCount();}
		if(fv.getUserCount()>maxUserCount){maxUserCount=fv.getUserCount();}
		if(fv.getTipCount()>maxTipCount){maxTipCount=fv.getTipCount();}
		if(fv.getLikes()>maxLikes){maxLikes=fv.getLikes();}
		if(fv.getRating()>maxRating){maxRating=fv.getRating();}
		
		if(fv.getCheckinCount()<minCheckins){minCheckins=fv.getCheckinCount();}
		if(fv.getUserCount()<minUserCount){minUserCount=fv.getUserCount();}
		if(fv.getTipCount()<minTipCount){minTipCount=fv.getTipCount();}
		if(fv.getLikes()<minLikes){minLikes=fv.getLikes();}
		if(fv.getRating()<minRating){minRating=fv.getRating();}
				
		venue_Data.put(id, fv);	
	}
	
	public void removeVenue(String id){
		venue_Data.remove(id);
	}
	
	public void clearVenue(String id){
		venue_Data.clear();
		setMaxCheckins(0);setMinCheckins(Integer.MAX_VALUE);
		setMaxUserCount(0);setMinUserCount(Integer.MAX_VALUE);
		setMaxTipCount(0);setMinTipCount(Integer.MAX_VALUE);
		setMaxLikes(0);setMinLikes(Integer.MAX_VALUE);
		setMaxRating(0.0);setMinRating(Double.MAX_VALUE);
	}

	public int getMaxCheckins() {
		return maxCheckins;
	}

	public void setMaxCheckins(int maxCheckins) {
		this.maxCheckins = maxCheckins;
	}

	public int getMinCheckins() {
		return minCheckins;
	}

	public void setMinCheckins(int minCheckins) {
		this.minCheckins = minCheckins;
	}

	public int getMaxUserCount() {
		return maxUserCount;
	}

	public void setMaxUserCount(int maxUserCount) {
		this.maxUserCount = maxUserCount;
	}

	public int getMinUserCount() {
		return minUserCount;
	}

	public void setMinUserCount(int minUserCount) {
		this.minUserCount = minUserCount;
	}

	public int getMaxTipCount() {
		return maxTipCount;
	}

	public void setMaxTipCount(int maxTipCount) {
		this.maxTipCount = maxTipCount;
	}

	public int getMinTipCount() {
		return minTipCount;
	}

	public void setMinTipCount(int minTipCount) {
		this.minTipCount = minTipCount;
	}

	public int getMaxLikes() {
		return maxLikes;
	}

	public void setMaxLikes(int maxLikes) {
		this.maxLikes = maxLikes;
	}

	public int getMinLikes() {
		return minLikes;
	}

	public void setMinLikes(int minLikes) {
		this.minLikes = minLikes;
	}

	public double getMaxRating() {
		return maxRating;
	}

	public void setMaxRating(double maxRating) {
		this.maxRating = maxRating;
	}

	public double getMinRating() {
		return minRating;
	}

	public void setMinRating(double minRating) {
		this.minRating = minRating;
	}
	
	
	
	
}
