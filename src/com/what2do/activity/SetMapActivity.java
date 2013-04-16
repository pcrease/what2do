package com.what2do.activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.what2do.R;
import com.what2do.R.layout;
import com.what2do.R.menu;
import com.what2do.foursquare.ParseFourSquareResponse;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;

public class SetMapActivity extends FragmentActivity {

	private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_map);
        
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setmap)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        

		
		
    }
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.set_map, menu);
		return true;
	}
	


}
