package com.what2do.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.what2do.R;

public class SearchTypeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_type);
		
		final Button gpsButton = (Button) findViewById(R.id.gps_search);
		 
		 Log.e("bbb","bbb");
		 
		 gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                
           	 Log.e("bbb","bbb");
           	 final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        	    	if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
        	        buildAlertMessageNoGps();
        	    	}
        	    	else{
        	    	Intent intent = new Intent(SearchTypeActivity.this, MapActivity.class);
        	        startActivity(intent);
        	    	}
        	    
            }
        });
        		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_type, menu);		 
		return true;
	}

	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("This option requires GPS to be enabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	
}
