package com.what2do.search;

public class Context_Data {

	private String targetCategory;
	private double maxWalkingDuration;
	private double minWalkingDuration=Double.MAX_VALUE;
	private double maxTravelDuration;
	private double minTravelDuration=Double.MAX_VALUE;
	private int maxNumberOfInstructions;
	private int minNumberOfInstructions=Integer.MAX_VALUE;
	private int maxCheckins;
	private int minCheckins;
	private int maxUserCount;
	private int minUserCount;
	private int maxTipCount;
	private int minTipCount;
	private int maxLikes;
	private int minLikes;
	private double maxRating;
	private double minRating;
	private final int maxPrice=4;
	private final int minPrice=1;
	
			
	
	public int getMaxPrice() {
		return maxPrice;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public String getTargetCategory() {
		return targetCategory;
	}
	public void setTargetCategory(String targetCategory) {
		this.targetCategory = targetCategory;
	}
	public double getMaxWalkingDuration() {
		return maxWalkingDuration;
	}
	public void setMaxWalkingDuration(double maxWalkingDuration) {
		this.maxWalkingDuration = maxWalkingDuration;
	}
	public double getMinWalkingDuration() {
		return minWalkingDuration;
	}
	public void setMinWalkingDuration(double minWalkingDuration) {
		this.minWalkingDuration = minWalkingDuration;
	}
	public double getMaxTravelDuration() {
		return maxTravelDuration;
	}
	public void setMaxTravelDuration(double maxTravelDuration) {
		this.maxTravelDuration = maxTravelDuration;
	}
	public double getMinTravelDuration() {
		return minTravelDuration;
	}
	public void setMinTravelDuration(double minTravelDuration) {
		this.minTravelDuration = minTravelDuration;
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
	public int getMinNumberOfInstructions() {
		return minNumberOfInstructions;
	}
	public void setMinNumberOfInstructions(int minNumberOfInstructions) {
		this.minNumberOfInstructions = minNumberOfInstructions;
	}
	public int getMaxNumberOfInstructions() {
		return maxNumberOfInstructions;
	}
	public void setMaxNumberOfInstructions(int maxNumberOfInstructions) {
		this.maxNumberOfInstructions = maxNumberOfInstructions;
	}
	
}
