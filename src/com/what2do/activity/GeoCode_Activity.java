package com.what2do.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.what2do.R;

public class GeoCode_Activity extends FragmentActivity {

	private Activity thisActivity;
	private ArrayAdapter adapter;
	private AutoCompleteTextView textView;
	private GeocodeAddresses geocodeAddresses;
	private GoogleMap mMap;
	private List<Address> geocodeResults = new ArrayList<Address>();
	private boolean itemSelected = false;
	private Marker mMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geo_code_);
		thisActivity = this;

		final Button nextButton = (Button) findViewById(R.id.goFromAddressGeocode);
		nextButton.setVisibility(View.GONE);
		
		nextButton.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent (thisActivity,Time_Activity.class);
				
				Bundle b=new Bundle();				
				b.putDouble("lat", mMarker.getPosition().latitude);
				b.putDouble("long", mMarker.getPosition().longitude);
				
				intent.putExtras(b);
				startActivity(intent);
			}
			
		});
		
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.addressmap)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setZoomControlsEnabled(false);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);
		textView = (AutoCompleteTextView) findViewById(R.id.addressAutoComplete);

		textView.setAdapter(adapter);
		// chekc for internet connection

		textView.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (count > 4 && !itemSelected) {

					Log.e("here", "again " + itemSelected);
					geocodeAddresses = new GeocodeAddresses();
					adapter.clear();
					geocodeAddresses.execute(textView.getText().toString());
				}
				itemSelected = false;
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				itemSelected = false;
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});

		textView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						geocodeAddresses.getCoordinateOfSelectedItem(arg2),
						15.0f));
				itemSelected = true;
				textView.setDropDownHeight(0);

				mMap.clear();

				Marker marker = mMap.addMarker(new MarkerOptions()
						.position(
								geocodeAddresses
										.getCoordinateOfSelectedItem(arg2))
						.title("Address Found").draggable(true)
						.snippet("Hold finger on the marker and drag to move!"));
				nextButton.setVisibility(View.VISIBLE);
				marker.showInfoWindow();
				mMarker=marker;
			}
		});

		textView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				textView.setDropDownHeight(LayoutParams.WRAP_CONTENT);
				return false;
			}

		});

	}

	private class GeocodeAddresses extends
			AsyncTask<String, Void, List<Address>> {

		// three dots is java for an array of strings

		private int numberOfResults = 5;
		private ArrayList<String> stringAddresses = new ArrayList<String>();

		@Override
		protected List<Address> doInBackground(String... arg0) {

			/*
			 * find the addresses by using getFromLocationName() method with the
			 * given address
			 */
			try {
				geocodeResults = new Geocoder(thisActivity)
						.getFromLocationName(arg0[0], numberOfResults);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.e("here", "" + geocodeResults.size());
			return geocodeResults;
		}

		protected void onPostExecute(List<Address> geocodeResults) {

			stringAddresses = addressesToStringArrayList(geocodeResults);
			adapter = new ArrayAdapter<String>(thisActivity,
					android.R.layout.simple_dropdown_item_1line,
					stringAddresses);
			textView.setAdapter(adapter);
			textView.showDropDown();
			Log.e("geo", "" + geocodeResults.size() + " " + 44444);
		}

		private ArrayList<String> addressesToStringArrayList(
				List<Address> addresses) {
			ArrayList<String> stringAddresses = new ArrayList<String>();
			for (Address address : addresses) {
				StringBuilder strReturnedAddress = new StringBuilder();
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
					strReturnedAddress.append(address.getAddressLine(i))
							.append(" ");
				}
				stringAddresses.add(strReturnedAddress.toString());
				Log.e("here", strReturnedAddress.toString());
			}

			return stringAddresses;
		}

		public LatLng getCoordinateOfSelectedItem(int indexOfSelection) {
			Log.e("geo", "" + geocodeResults.size() + " " + indexOfSelection);

			if (indexOfSelection < geocodeResults.size()) {
				return new LatLng(geocodeResults.get(indexOfSelection)
						.getLatitude(), geocodeResults.get(indexOfSelection)
						.getLongitude());
			}
			return null;
		}

	}

}
