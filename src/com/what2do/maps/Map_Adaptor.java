package com.what2do.maps;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.what2do.foursquare.Foursquare_Venue;
import com.what2do.foursquare.Venue_Dataset;

public class Map_Adaptor {

	
	private MapView mapView;
	private List<Overlay> mapOverlays;
	private double userLatitude;
	private double userLongitude;
	
	public Map_Adaptor(MapView mapView){
		this.mapView=mapView;
	}
	
	public void drawFourSquareOverlay(Venue_Dataset vd,Drawable defaultMarker, Context context){
		mapOverlays = mapView.getOverlays();
		FourSquareVenueOverlays fourSquareVenueOverlays = new FourSquareVenueOverlays(vd,defaultMarker, context);
                
        Iterator<Entry<String, Foursquare_Venue>> it = vd.getVenue_Data().entrySet().iterator();
        
        while (it.hasNext()) {
        	Entry pairs = it.next();
            //Log.d("value=",pairs.getKey() + " = " + pairs.getValue());
            //Log.d("value=",pairs.getKey() + " = " + pairs.getValue());
            Foursquare_Venue foursquare_Venue= (Foursquare_Venue) pairs.getValue();
            GeoPoint geoPoint = new GeoPoint((int)(foursquare_Venue.getLatitude() * 1E6), (int)(foursquare_Venue.getLongitude()  * 1E6));
            //Log.d("Name", foursquare_Venue.getName());
            OverlayItem overlayitem = new OverlayItem(geoPoint, "Foursquare Venue", foursquare_Venue.getName());
            fourSquareVenueOverlays.addOverlay(overlayitem);            
        }        
       
        mapOverlays.add(fourSquareVenueOverlays);
	}
	
	public void drawUserLocation (Drawable defaultMarker, Context context){
		String message="This is your current GPS location";
		
		mapOverlays = mapView.getOverlays();
		UserOverlay userOverlay = new UserOverlay(defaultMarker, context);
		
		        
        GeoPoint geoPoint = new GeoPoint((int)(getUserLatitude()* 1E6), (int)(getUserLongitude() * 1E6));       
        OverlayItem overlayitem = new OverlayItem(geoPoint, "U R HERE", message);
                
        userOverlay.addOverlay(overlayitem);
        mapOverlays.add(userOverlay);
        
	}

	public double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}

	public double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}

	
	
	
	
}
