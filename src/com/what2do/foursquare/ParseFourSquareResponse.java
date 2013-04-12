package com.what2do.foursquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;


import android.os.AsyncTask;
import android.util.Log;

public class ParseFourSquareResponse {

	private Venue_Dataset vd = new Venue_Dataset();
	
	public void getJSONStringAndParse(double lat, double lon) {
		
		String url="https://api.foursquare.com/v2/venues/search?ll="+lat+","+lon+"&section=bar&client_id=CK4ENMLHHV23P2QQNXOB2IWSMWF2MRYKBS5V2OXDPSSZYX2K&client_secret=YATORC45OMNLXNNZS3R0KIF0RT4VF1RQIO5FE23111VSMPAV&v=201220801&limit=5";
		new FourSquareFetchTask().execute(url);
	}

	
private void buildVenueDataset(String inputData) {
		
		try {
			JSONObject json = new JSONObject(inputData);
			JSONArray venues = json.getJSONObject("response").getJSONArray("venues");
			Log.d("number Of Venues", Integer.toString(venues.length()));

			for (int i = 0; i < venues.length(); i++) {
				Foursquare_Venue fourSquare_Venue = new Foursquare_Venue();	
				JSONObject tempJson = venues.getJSONObject(i);
				
				if (tempJson.has("id")) {fourSquare_Venue.setId(tempJson.getString("id"));}
				if (tempJson.has("name")) {fourSquare_Venue.setName(tempJson.getString("name"));}
				
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

	}

	private void getLocation(JSONObject input, Foursquare_Venue fourSquare_Venue)
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

	private void getCategories(JSONObject input,
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

	private void getStats(JSONObject input, Foursquare_Venue fourSquare_Venue)
			throws JSONException {
		JSONObject stats = input.getJSONObject("stats");

		if (stats.has("checkinsCount")) {
			//Log.d("checkinsCount", stats.get("checkinsCount").toString());
			fourSquare_Venue.setCheckinCount(stats.getInt("checkinsCount"));
		}
		if (stats.has("usersCount")) {
			//Log.d("usersCount", stats.get("usersCount").toString());
			fourSquare_Venue.setCheckinCount(stats.getInt("usersCount"));
		}
		if (stats.has("tipCount")) {
			//Log.d("tipCount", stats.get("tipCount").toString());
			fourSquare_Venue.setCheckinCount(stats.getInt("tipCount"));
		}
	}

	private void getLikesSpecialsHereNow(JSONObject input,
			Foursquare_Venue fourSquare_Venue) throws JSONException {
		JSONObject likes = null;
		if (input.has("likes")){likes = input.getJSONObject("likes");}
		JSONObject specials = input.getJSONObject("specials");
		JSONObject hereNow = input.getJSONObject("hereNow");

		if (likes!=null&&likes.has("count")) {
			//Log.d("count", likes.get("count").toString());
			fourSquare_Venue.setCheckinCount(likes.getInt("count"));
		}
		if (specials.has("count")) {
			//Log.d("count", specials.get("count").toString());
			fourSquare_Venue.setSpecials(specials.getInt("count"));
		}
		if (hereNow.has("count")) {
			//Log.d("count", hereNow.get("count").toString());
			fourSquare_Venue.setHereNow(hereNow.getInt("count"));
		}
	}

	

	public Venue_Dataset getVd() {
		return vd;
	}

	public void setVd(Venue_Dataset vd) {
		this.vd = vd;
	}

	
	public class FourSquareFetchTask extends AsyncTask<String, String, StringBuilder> {
	    

	    protected void onPostExecute(StringBuilder result) {
	        if (result != null) {	        	
	        	buildVenueDataset(result.toString());
	        		        		    		
	    		    
	        } else {
	            // error occured
	        }
	    }

		@Override
		protected StringBuilder doInBackground(String... string) {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(string[0]);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("json", "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
						
			return builder;
		}
	}
}
