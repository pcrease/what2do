package com.what2do.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.what2do.R;

public class SearchTypeActivity extends Activity {
	
	private static Activity thisActivity;
	private static LocationManager manager;
	private LocationListener locationListener;
	 // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 minute
    
    private static Button gpsButton;
    private static Button addressButton;
    private static Button mapButton;
    private static ProgressDialog waitingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_type);
		thisActivity=this;
		
		
		manager= (LocationManager) getSystemService( Context.LOCATION_SERVICE );
		
		gpsButton = (Button) findViewById(R.id.gps_search);
		addressButton = (Button) findViewById(R.id.address_search);
		mapButton = (Button) findViewById(R.id.map_search);
	
			
		 
		 gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  
            	
            	waitingDialog = ProgressDialog.show(thisActivity, "", 
    	                "Searching for GPS fix. Please wait...", true);   	 
            	waitingDialog.setCancelable(true);
            	waitingDialog.setOnCancelListener(new OnCancelListener(){
                   @Override
                   public void onCancel(DialogInterface dialog){
                	   if(waitingDialog!=null)waitingDialog.dismiss();
               		if(locationListener!=null)manager.removeUpdates(locationListener);
                }});           
                
            	 if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            	        buildAlertMessageNoGps();
            	 }
            	    	
            	 locationListener = new LocationListener() {
     				public void onLocationChanged(Location location) {
     					Intent intent = new Intent(thisActivity, MapActivity.class);
     			    	thisActivity.startActivity(intent);
     			    	waitingDialog.dismiss();
     				}

     				public void onStatusChanged(String provider, int status,
     						Bundle extras) {
     					if (status == LocationProvider.AVAILABLE) {
     						// droydMapComponent.layerEnable(LAYER_MY_LOCATION, true);
     					}
     				}

     				public void onProviderEnabled(String provider) {
     				}

     				public void onProviderDisabled(String provider) {
     				}
     			};
     			
     			manager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
            }
        });
		 
		 
		 addressButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {                
	            	Intent intent = new Intent(thisActivity, MapActivity.class);
	    	    	thisActivity.startActivity(intent);}
	       });
		 
		 mapButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {                
	            	Intent intent = new Intent(thisActivity, SetMapActivity.class);
	    	    	thisActivity.startActivity(intent);}
	       });
        	
		 
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_type, menu);		 
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(waitingDialog!=null)waitingDialog.dismiss();
		if(locationListener!=null)manager.removeUpdates(locationListener);
		Log.e("bbb","stopping");
		
		}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(waitingDialog!=null)waitingDialog.dismiss();
		if(locationListener!=null)manager.removeUpdates(locationListener);
		Log.e("bbb","destroying");
		
		
		
	}
	@Override
	protected void onPause() {
		super.onPause(); 
		if(waitingDialog!=null)waitingDialog.dismiss();
		if(locationListener!=null)manager.removeUpdates(locationListener);
		Log.e("bbb","pausing");
		
	}
	
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		Log.e("bbb","down pressed");
	    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	            && keyCode == KeyEvent.KEYCODE_BACK
	            && event.getRepeatCount() == 0) {
	    	if(waitingDialog!=null)waitingDialog.dismiss();
			if(locationListener!=null)manager.removeUpdates(locationListener);
	        onBackPressed();
	    }

	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		Log.e("bbb","down pressed");
		if(waitingDialog!=null)waitingDialog.dismiss();
		if(locationListener!=null)manager.removeUpdates(locationListener);
	    return;
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


