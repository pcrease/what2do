package com.what2do.foursquare;

import java.util.ArrayList;
import java.util.List;


public class Foursquare_Venue implements Cloneable {

	private  String id;
	private  String referralId;
	private  String name;
	private  String contact;
	private  String address;
	private  String crossStreet;
	private  String city;
	private  String state;
	private  String country;
	private  String cc;
	
	private  Double latitude;
	private  Double longitude;
	
	private  List<Venue_Category> categories = new ArrayList<Venue_Category>();
	private  String verified;
	
	private  int checkinCount;
	private  int userCount;
	private  int tipCount;
	private  int distance;
	private  int likes;
	private  int specials;
	private  int hereNow;
	
	private  String url; 
	private  String openingHours="";
	
	private  String menuURL = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public List getCategories() {
		return categories;
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}
	
	public void addCategories(Venue_Category vc) {
		categories.add(vc);
	}
	
	public void clearCategories() {
		categories.clear();
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public int getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(int checkinCount) {
		this.checkinCount = checkinCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getTipCount() {
		return tipCount;
	}

	public void setTipCount(int tipCount) {
		this.tipCount = tipCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCrossStreet() {
		return crossStreet;
	}

	public void setCrossStreet(String crossStreet) {
		this.crossStreet = crossStreet;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getSpecials() {
		return specials;
	}

	public void setSpecials(int specials) {
		this.specials = specials;
	}

	public int getHereNow() {
		return hereNow;
	}

	public void setHereNow(int hereNow) {
		this.hereNow = hereNow;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}
	
	@Override
	public Object clone()
    {
        try
    {
            return super.clone();
        }
    catch( CloneNotSupportedException e )
    {
            return null;
        }
    } 
}
