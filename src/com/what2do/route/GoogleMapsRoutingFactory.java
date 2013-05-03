package com.what2do.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class GoogleMapsRoutingFactory {


	private static String modeOfTransport;
	private static LatLng origin;
	private static LatLng destination;
	private final static String UNIT_VALUE="metric";
	private final static String GOOGLE_HTTP_ADDRESS="https://maps.googleapis.com/maps/api/directions/json?";
	private static long  departureTime;
	private static int maxWalkingDuration=0;
	private static int minWalkingDuration=Integer.MAX_VALUE;
	private static int maxWalkingDistance=0;
	private static int minWalkingDistance=Integer.MAX_VALUE;
	 
	
	public static String executeRouteCalculation() throws JSONException{
		departureTime=System.currentTimeMillis() / 1000l;
		
		String url = GOOGLE_HTTP_ADDRESS+"origin="+origin.latitude+","+origin.longitude+"&destination="+destination.latitude+","+destination.longitude
				+"&sensor=true&mode="+modeOfTransport+"&departure_time="+departureTime;
		
		String response=getResponse(url);
		return response;		
		
		
	}
	
	public String getModeOfTransport() {
		return modeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}

	public LatLng getOrigin() {
		return origin;
	}

	public void setOrigin(LatLng origin) {
		this.origin = origin;
	}

	public LatLng getDestination() {
		return destination;
	}

	public void setDestination(LatLng destination) {
		this.destination = destination;
	}

	public long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(long departureTime) {
		this.departureTime = departureTime;
	}

	
	
	private String jsonResponse;
	private LatLng[] routeCoordinates;
	private static long travelTime=0;
	

	
	public static long returnTravelTime(){
		return travelTime;
	}
	
	public static LatLng[] returnRouteGeometry(){
		return null;
	}
	
		
	private static String getResponse(String url) {
	
	StringBuilder builder = new StringBuilder();
	HttpClient client = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(url);
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
				
	return builder.toString();
}

	
	
}
