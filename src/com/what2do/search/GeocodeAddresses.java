package com.what2do.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GeocodeAddresses extends AsyncTask<String, Void, List<Address>> {

	// three dots is java for an array of strings
	private Activity thisActivity;
	private List<Address> geocodeResults=new ArrayList<Address>();
	private AutoCompleteTextView textView;
	private ArrayAdapter<String> adapter;
	private int numberOfResults=5;
	private ArrayList<String> stringAddresses = new ArrayList<String>();
	
	public GeocodeAddresses(Activity thisActivity, AutoCompleteTextView textView, ArrayAdapter<String> adapter){
		this.thisActivity=thisActivity;		
		this.textView=textView;
		this.adapter=adapter;
	}

	@Override
	protected List<Address> doInBackground(String... arg0) {
				
		/*
		 * find the addresses by using getFromLocationName() method with the
		 * given address
		 */
		try {
			geocodeResults = new Geocoder(thisActivity).getFromLocationName(
					arg0[0], numberOfResults);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.e("here", "" + geocodeResults.size());
		return geocodeResults;
	}
	
	protected void onPostExecute(List<Address> geocodeResults) {
		
		stringAddresses= addressesToStringArrayList(geocodeResults);		
		adapter=new ArrayAdapter<String>(thisActivity,android.R.layout.simple_dropdown_item_1line,stringAddresses);
		textView.setAdapter(this.adapter);
		textView.showDropDown();
		Log.e("geo",""+geocodeResults.size()+" "+44444);
    }
	
	private ArrayList<String> addressesToStringArrayList(List<Address> addresses){
		ArrayList<String> stringAddresses= new ArrayList<String>();
		for(Address address:addresses){
			StringBuilder strReturnedAddress = new StringBuilder();
			for(int i=0; i<address.getMaxAddressLineIndex(); i++) {
			     strReturnedAddress.append(address.getAddressLine(i)).append("\n");
			    }
			stringAddresses.add(strReturnedAddress.toString());
			Log.e("here",strReturnedAddress.toString());
		}
		
		return stringAddresses;
	}

	public int getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	
	public LatLng getCoordinateOfSelectedItem(int indexOfSelection){
		Log.e("geo",""+geocodeResults.size()+" "+indexOfSelection);
		
		if (indexOfSelection < geocodeResults.size()) {
		return new LatLng( 	geocodeResults.get(indexOfSelection).getLatitude(),
							geocodeResults.get(indexOfSelection).getLongitude());
		}
		return null;
	}
	
	public List<Address> getAddresses(){
		return geocodeResults;		
	}
	
}
