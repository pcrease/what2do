package com.what2do.route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.maps.GeoPoint;

public class JSON_Transit_GoogleDirection {

	String destinationID;
	
	String startAddress;
	String endAddress;
	
	Long  distanceValue;
	String distanceText;
	
	Long durationValue;
	
	String durationText;
	
	Date arrivalTime;
	String arrivalTimeText;
	Date departureTime;
	String departureTimeText;
	
	LatLngBounds routeBounds;
	
	LatLng startLocation;
	LatLng endLocation;
	
	int numberOfChanges=0;
	int totalWalkingDuration=0;
	
	int numberOfInstructions=0;
	
	public int getNumberOfInstructions() {
		return numberOfInstructions;
	}

	public void setNumberOfInstructions(int numberOfInstructions) {
		this.numberOfInstructions = numberOfInstructions;
	}
	public int getNumberOfChanges() {
		return numberOfChanges;
	}

	public void setNumberOfChanges(int numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}

	
	
	
	public int getTotalWalkingDuration() {
		return totalWalkingDuration;
	}

	public void setTotalWalkingDuration(int totalWalkingDuration) {
		this.totalWalkingDuration = totalWalkingDuration;
	}




	List<JSON_Transit_GoogleDirection_Step> steps;
	


public String getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}
	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

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

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalTimeText() {
		return arrivalTimeText;
	}

	public void setArrivalTimeText(String arrivalTimeText) {
		this.arrivalTimeText = arrivalTimeText;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getDepartureTimeText() {
		return departureTimeText;
	}

	public void setDepartureTimeText(String departureTimeText) {
		this.departureTimeText = departureTimeText;
	}

	public LatLngBounds getRouteBounds() {
		return routeBounds;
	}

	public void setRouteBounds(LatLngBounds routeBounds) {
		this.routeBounds = routeBounds;
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

	public List<JSON_Transit_GoogleDirection_Step> getSteps() {
		return steps;
	}

	public void setSteps(List<JSON_Transit_GoogleDirection_Step> steps) {
		this.steps = steps;
	}
	
	public void clearSteps() {
		if (steps!=null){steps.clear();}
	}
	
	public void addSteps(JSON_Transit_GoogleDirection_Step step) {
		if (steps==null){steps= new ArrayList<JSON_Transit_GoogleDirection_Step>();}
		
		steps.add(step);
	}
}
