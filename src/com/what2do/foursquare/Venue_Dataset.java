package com.what2do.foursquare;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Venue_Dataset {

	private Map<String, Foursquare_Venue> venue_Data = new HashMap<String, Foursquare_Venue>();

	public Map<String, Foursquare_Venue> getVenue_Data() {
		return venue_Data;
	}

	public void setVenue_Data(Map<String, Foursquare_Venue> venue_Data) {
		this.venue_Data = venue_Data;
	}
	
	public void addVenue(String id, Foursquare_Venue fv){
		Log.d("size", venue_Data.size()+"");
		venue_Data.put(id, fv);	
	}
	
	public void removeVenue(String id){
		venue_Data.remove(id);
	}
	
	public void clearVenue(String id){
		venue_Data.clear();
	}
}
