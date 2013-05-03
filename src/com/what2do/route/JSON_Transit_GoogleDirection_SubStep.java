package com.what2do.route;

import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.maps.GeoPoint;

public class JSON_Transit_GoogleDirection_SubStep {

	
	Long  distanceValue;
	String distanceText;
	
	Long durationValue;
	String durationText;
	
	LatLng startLocation;
	LatLng endLocation;
	
	String htmlInstruction;
		
	String travelMode;
		
	List<LatLng> routePolyline;
	
	List<JSON_Transit_GoogleDirection_SubStep> steps;

	public Long getDistanceValue() {
		return distanceValue;
	}

	public void setDistanceValue(Long distanceValue) {
		this.distanceValue = distanceValue;
	}

	public String getDistanceText() {
		return distanceText;
	}

	public void setDistanceText(String distanceText) {
		this.distanceText = distanceText;
	}

	public Long getDurationValue() {
		return durationValue;
	}

	public void setDurationValue(Long durationValue) {
		this.durationValue = durationValue;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	public LatLng getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(LatLng startLocation) {
		this.startLocation = startLocation;
	}

	public LatLng getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(LatLng endLocation) {
		this.endLocation = endLocation;
	}

	public String getHtmlInstruction() {
		return htmlInstruction;
	}

	public void setHtmlInstruction(String htmlInstruction) {
		this.htmlInstruction = htmlInstruction;
	}

	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}

	public List<LatLng> getRoutePolyline() {
		return routePolyline;
	}

	public void setRoutePolyline(List<LatLng> routePolyline) {
		this.routePolyline = routePolyline;
	}

	public List<JSON_Transit_GoogleDirection_SubStep> getSteps() {
		return steps;
	}

	public void setSteps(List<JSON_Transit_GoogleDirection_SubStep> steps) {
		this.steps = steps;
	}

}
