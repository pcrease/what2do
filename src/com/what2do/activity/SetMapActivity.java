package com.what2do.activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.what2do.R;
import com.what2do.R.layout;
import com.what2do.R.menu;
import com.what2do.foursquare.ParseFourSquareResponse;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class SetMapActivity extends FragmentActivity {

	private GoogleMap mMap;
	private Button setButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_map);
        
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setmap)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        setButton = (Button) findViewById(R.id.select_location);
        
        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	mMap.clear();
            	
            	float zoomLevel=mMap.getCameraPosition().zoom;
            	Log.e("aaa",""+zoomLevel);
            	LatLng mapCenter= mMap.getCameraPosition().target;
            	 Marker marker = mMap.addMarker(new MarkerOptions()
                 .position(mMap.getCameraPosition().target)
                 .title("Search target")
                 .draggable(true)
                 .snippet("Hold finger on the marker and drag to move!"));
            	 
            	 marker.showInfoWindow();
            }
       });
		
		
    }
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.set_map, menu);
		return true;
	}
	


}
