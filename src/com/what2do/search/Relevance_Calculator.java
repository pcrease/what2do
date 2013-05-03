package com.what2do.search;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.util.Log;

import com.what2do.foursquare.FourSquare_Category_Data;
import com.what2do.foursquare.Foursquare_Venue;
import com.what2do.foursquare.Venue_Category;
import com.what2do.foursquare.Venue_Dataset;

public class Relevance_Calculator {
//the secondary weights
	private double changesWeight;
	private double walkingDistanceWeight;
	private double travelDurationWeight;
	private double CheckinWeight;
	private double userCountWeight;
	private double tipCountWeight;
	private double numOfInstructionsWeight;
	
//the primary weights
	private double categoryWeight;
	private double hassleWeight;
	private double popularityWeight;
	private double ContentWeight;
	
	private static double Hassle;

	public static void calculateOverallRelevance(Context_Data context_Data,
			Venue_Dataset vd) {

		for (Map.Entry<String, Foursquare_Venue> venue : vd.getVenue_Data()
				.entrySet()) {
			double categoricalRelevance = calculateCategory(context_Data,
					venue.getValue());
		}
		
		calculateHassle(context_Data,vd);
	}

	public static void calculateHassle(Context_Data context_Data,
			Venue_Dataset vd) {

		TreeMap<Double,Foursquare_Venue> rankTreeMap = new TreeMap<Double,Foursquare_Venue>();
		
		for (Map.Entry<String, Foursquare_Venue> venue : vd.getVenue_Data()
				.entrySet()) {
			Foursquare_Venue v = venue.getValue();
			
			int walkTime = v.getjSON_Transit_GoogleDirection()
					.getTotalWalkingDuration();
			long TotalTravelTime = v.getjSON_Transit_GoogleDirection()
					.getDurationValue();
			int numberOfInstructions = v.getjSON_Transit_GoogleDirection()
					.getNumberOfInstructions();

			double standardisedwalkTime = (v.getjSON_Transit_GoogleDirection()
					.getTotalWalkingDuration() - context_Data
					.getMinWalkingDuration())
					/ (context_Data.getMaxWalkingDuration() - context_Data
					.getMinWalkingDuration());
			
			double standardTotalTravelTime= (v.getjSON_Transit_GoogleDirection()
					.getDurationValue() - context_Data
					.getMinTravelDuration())
					/ (context_Data.getMaxTravelDuration() - context_Data
					.getMinTravelDuration());
			
			double standardNumberOfInstructions= (v.getjSON_Transit_GoogleDirection()
					.getNumberOfInstructions() - context_Data
					.getMinNumberOfInstructions())
					/ (context_Data.getMaxNumberOfInstructions() - context_Data
					.getMinNumberOfInstructions());
			//higher value = more hassle = baddddd
			
			
			if(Double.isNaN(standardisedwalkTime)){standardisedwalkTime=0.0;}
			if(Double.isNaN(standardTotalTravelTime)){standardTotalTravelTime=0.0;}
			if(Double.isNaN(standardNumberOfInstructions)){standardNumberOfInstructions=0.0;}
			Hassle = standardisedwalkTime+standardTotalTravelTime+standardNumberOfInstructions;			
			
			v.setOverallHassle(Hassle);
			
			rankTreeMap.put(v.getOverallHassle(), v);			
			Log.e("hassle = ",v.getName()+" "+Hassle+" "+standardisedwalkTime+" "+standardTotalTravelTime+" "+standardNumberOfInstructions+" "+numberOfInstructions);
		}
		
		int rank=0;
		//ranks them lowest first, as hassle is a negative thing......
		
		for (Entry<Double, Foursquare_Venue> e : rankTreeMap.entrySet()) {
		    Log.e("venue rank =",e.getValue().getName());
		    vd.getVenue_Data().get(e.getValue().getId()).setRankHassle(rank+=1);
		}

	}

	public static void calculatePopularity(Context_Data context_Data,
			Venue_Dataset vd) {
		
		
			

	}

	public static void calculateContent(Context_Data context_Data,
			Venue_Dataset vd) {

	}

	private static double calculateCategory(Context_Data context_Data,
			Foursquare_Venue v) {

		// compares category of object, if it is not equal then it checks to see
		// if it has the same primary category.
		// if it does, then it return0.5, if not then 0;

		List<Venue_Category> objectCategories = v.getCategories();

		String primaryCategory = "";
		for (Venue_Category vc : objectCategories) {
			if (vc.isPrimary())
				primaryCategory = vc.getName();
		}

		primaryCategory = primaryCategory.replace("&", "and");
		primaryCategory = primaryCategory.replace("'", "");

		if (context_Data.getTargetCategory().equals(primaryCategory)) {
			return 1.0;
		} else {
			// should be loaded as the mapresult has been creared, and this
			// loads data from resouorces.

			int categoryIndex = FourSquare_Category_Data.getNamelist().indexOf(
					primaryCategory);

			String primaryCategoryName;
			primaryCategoryName = FourSquare_Category_Data
					.getHierachicalplacelist().get(categoryIndex);

			int targetCategoryIndex = FourSquare_Category_Data.getNamelist()
					.indexOf(primaryCategory);

			String targetPrimaryCategoryName;
			targetPrimaryCategoryName = FourSquare_Category_Data
					.getHierachicalplacelist().get(categoryIndex);

			Log.e("target, actual", targetPrimaryCategoryName + " "
					+ primaryCategoryName);
			if (targetPrimaryCategoryName.equals(primaryCategoryName)) {
				return 0.5;
			}
			return 0.0;
		}
	}

	public double getChangesWeight() {
		return changesWeight;
	}

	public void setChangesWeight(double changesWeight) {
		this.changesWeight = changesWeight;
	}

	public double getWalkingDistanceWeight() {
		return walkingDistanceWeight;
	}

	public void setWalkingDistanceWeight(double walkingDistanceWeight) {
		this.walkingDistanceWeight = walkingDistanceWeight;
	}

	public double getTravelDurationWeight() {
		return travelDurationWeight;
	}

	public void setTravelDurationWeight(double travelDurationWeight) {
		this.travelDurationWeight = travelDurationWeight;
	}

	public double getCategoryWeight() {
		return categoryWeight;
	}

	public void setCategoryWeight(double categoryWeight) {
		this.categoryWeight = categoryWeight;
	}

	public double getCheckinWeight() {
		return CheckinWeight;
	}

	public void setCheckinWeight(double checkinWeight) {
		CheckinWeight = checkinWeight;
	}

	public double getTipCountWeight() {
		return tipCountWeight;
	}

	public void setTipCountWeight(double tipCountWeight) {
		this.tipCountWeight = tipCountWeight;
	}

	public double getUserCountWeight() {
		return userCountWeight;
	}

	public void setUserCountWeight(double userCountWeight) {
		this.userCountWeight = userCountWeight;
	}

	public double getNumOfInstructionsWeight() {
		return numOfInstructionsWeight;
	}

	public void setNumOfInstructionsWeight(double numOfInstructionsWeight) {
		this.numOfInstructionsWeight = numOfInstructionsWeight;
	}

}
