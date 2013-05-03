package com.what2do.foursquare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Foursquare_Factory {

	
public static Venue_Dataset buildVenueDataset(String inputJSONData) {
		
		Venue_Dataset vd = new Venue_Dataset();
		
		try {
			JSONObject json = new JSONObject(inputJSONData);
			//Log.e("json",json.toString(0));
			JSONArray venues = json.getJSONObject("response").getJSONArray("venues");
			
			Log.d("number Of Venues", venues.toString(0));

			for (int i = 0; i < venues.length(); i++) {
				Foursquare_Venue fourSquare_Venue = new Foursquare_Venue();	
				JSONObject tempJson = venues.getJSONObject(i);
				
				if (tempJson.has("id")) {fourSquare_Venue.setId(tempJson.getString("id"));}
				if (tempJson.has("name")) {fourSquare_Venue.setName(tempJson.getString("name"));}
				
				if (tempJson.has("price")) {
					JSONObject price =tempJson.getJSONObject("price");					
					fourSquare_Venue.setPrice(price.getInt("tier"));
					}
				
				if (tempJson.has("hours")) {fourSquare_Venue.setOpeningHours(tempJson.getString("hours"));}
				if (tempJson.has("popular")) {fourSquare_Venue.setPopularHours(tempJson.getString("popular"));}
				//Log.d("output", tempJson.toString());
				getLocation(tempJson,fourSquare_Venue);
				getCategories(tempJson,fourSquare_Venue);
				getStats(tempJson,fourSquare_Venue);
				getLikesSpecialsHereNow(tempJson,fourSquare_Venue);
				
				if (tempJson.has("referralId")) {fourSquare_Venue.setReferralId(tempJson.getString("referralId"));}	
				
				Log.d("i=",i+"");
				Log.d("info=",fourSquare_Venue.getId()+"");
				vd.addVenue(fourSquare_Venue.getId(),(Foursquare_Venue) fourSquare_Venue.clone());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return vd;

	}

	private static void getLocation(JSONObject input, Foursquare_Venue fourSquare_Venue)
			throws JSONException {
		JSONObject location = input.getJSONObject("location");
		// Log.d("if",Boolean.toString(location.has("address")));
		if (location.has("address")) {
			// Log.d("address", location.get("address").toString());
			fourSquare_Venue.setAddress(location.get("address").toString());
		}
		if (location.has("crossStreet")) {
			// Log.d("crossStreet", location.get("crossStreet").toString());
			fourSquare_Venue.setCrossStreet(location.get("crossStreet")
					.toString());
		}
		if (location.has("lat")) {
			// Log.d("lat", location.get("lat").toString());
			fourSquare_Venue.setLatitude(location.getDouble("lat"));
		}
		if (location.has("lng")) {
			// Log.d("lng", location.get("lng").toString());
			fourSquare_Venue.setLongitude(location.getDouble("lng"));
		}
		if (location.has("distance")) {
			// Log.d("distance", location.get("distance").toString());
			fourSquare_Venue.setDistance(location.getInt("distance"));
		}
		if (location.has("city")) {
			// Log.d("city", location.get("city").toString());
			fourSquare_Venue.setCity(location.get("city").toString());
		}
		if (location.has("state")) {
			// Log.d("state", location.get("state").toString());
			fourSquare_Venue.setState(location.get("state").toString());
		}
		if (location.has("country")) {
			// Log.d("country", location.get("country").toString());
			fourSquare_Venue.setCountry(location.get("country").toString());
		}
		if (location.has("cc")) {
			// Log.d("cc", location.get("cc").toString());
			fourSquare_Venue.setCc(location.get("cc").toString());
		}

	}

	private static void getCategories(JSONObject input,
			Foursquare_Venue fourSquare_Venue) throws JSONException {
		JSONArray categories = input.getJSONArray("categories");

		Log.d("numberOfCategories", Integer.toString(categories.length()));
		for (int i = 0; i < categories.length(); i++) {
			Venue_Category vc = new Venue_Category();
			JSONObject category = categories.getJSONObject(i);

			if (category.has("id")) {
				//Log.d("id", category.get("id").toString());
				vc.setId(category.get("id").toString());
			}
			if (category.has("name")) {
				Log.d("name", category.get("name").toString());
				vc.setName(category.get("name").toString());
			}
			if (category.has("shortName")) {
				//Log.d("shortName", category.get("shortName").toString());
				vc.setShortName(category.get("shortName").toString());
			}
			if (category.has("pluralName")) {
				//Log.d("pluralName", category.get("pluralName").toString());
				vc.setPluralName(category.get("pluralName").toString());
			}
			if (category.has("primary")) {
				//Log.d("primary", category.get("primary").toString());
				vc.setPrimary(category.getBoolean("primary"));
			}
			
			fourSquare_Venue.addCategories((Venue_Category)vc.clone());
		}
		
		
	}

	private static void getStats(JSONObject input, Foursquare_Venue fourSquare_Venue)
			throws JSONException {
		JSONObject stats = input.getJSONObject("stats");

		if (stats.has("checkinsCount")) {
			//Log.d("checkinsCount", stats.get("checkinsCount").toString());
			fourSquare_Venue.setCheckinCount(stats.getInt("checkinsCount"));
		}
		if (stats.has("usersCount")) {
			//Log.d("usersCount", stats.get("usersCount").toString());
			fourSquare_Venue.setUserCount(stats.getInt("usersCount"));
		}
		if (stats.has("tipCount")) {
			//Log.d("tipCount", stats.get("tipCount").toString());
			fourSquare_Venue.setTipCount(stats.getInt("tipCount"));
		}
	}

	private static void getLikesSpecialsHereNow(JSONObject input,
			Foursquare_Venue fourSquare_Venue) throws JSONException {
		JSONObject likes = null;
		if (input.has("likes")){likes = input.getJSONObject("likes");}
		JSONObject specials = input.getJSONObject("specials");
		JSONObject hereNow = input.getJSONObject("hereNow");

		/*if (likes!=null&&likes.has("count")) {
			//Log.d("count", likes.get("count").toString());
			fourSquare_Venue.setc(likes.getInt("count"));
		}*/
		if (specials.has("count")) {
			//Log.d("count", specials.get("count").toString());
			fourSquare_Venue.setSpecials(specials.getInt("count"));
		}
		if (hereNow.has("count")) {
			//Log.d("count", hereNow.get("count").toString());
			fourSquare_Venue.setHereNow(hereNow.getInt("count"));
		}
	}

	

	
}
