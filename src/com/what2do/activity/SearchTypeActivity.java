package com.what2do.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.what2do.R;
import com.what2do.foursquare.ParseFourSquareResponse.FourSquareFetchTask;

public class SearchTypeActivity extends Activity {
	
	static Activity thisActivity;
	boolean isSearchingForGpsFix;
	static LocationManager manager;
	GetGPSFixandProgress getGPSFixandProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_type);
		thisActivity=this;
		
		manager= (LocationManager) getSystemService( Context.LOCATION_SERVICE );
		
		final Button gpsButton = (Button) findViewById(R.id.gps_search);
		final Button addressButton = (Button) findViewById(R.id.address_search);
		 
		 Log.e("bbb","bbb");
		 
		 
		 gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                
           	 Log.e("bbb","bbb");
           	        	 
           	 
        	    	if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
        	        buildAlertMessageNoGps();
        	    	}
	        	    
        	    	else{ 	
        	    		getGPSFixandProgress = new GetGPSFixandProgress();
        	    		getGPSFixandProgress.run();
        	    		getGPSFixandProgress.requestStop();
	        	    		
        	    	}    
            }
        });
		 
		 
		 addressButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {                
	            	Intent intent = new Intent(thisActivity, MapActivity.class);
	    	    	thisActivity.startActivity(intent);}
	        });
        	
		 
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_type, menu);		 
		return true;
	}

	
	protected void onStop() {
		
		getGPSFixandProgress.requestStop();
		getGPSFixandProgress.interrupt();
		getGPSFixandProgress=null;
		super.onStop();
	}
	
	protected void onDestroy() {
		
		getGPSFixandProgress.requestStop();
		getGPSFixandProgress.interrupt();
		getGPSFixandProgress=null;
		super.onDestroy();
	}
	
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    getGPSFixandProgress.requestStop();
	    getGPSFixandProgress.interrupt();
		getGPSFixandProgress=null;
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
	
	
	
	private static boolean getGPSFix(LocationManager lm){
		
		   Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		   // Get the time of the last fix
		   if(loc==null)return false;
		   long lastFixTimeMillis = loc.getTime(); 
		   Log.e("bbb",Boolean.toString((SystemClock.elapsedRealtime() - lastFixTimeMillis) < 10000));
		   return (SystemClock.elapsedRealtime() - lastFixTimeMillis) < 10000;
	}

	
	
	static public class GetGPSFixandProgress extends Thread {
	   
	    private volatile boolean stop = false;
	    @Override
	    public void run() {
	    	if (this.interrupted()){
	    	    return;
	    	 }
	      boolean hasFix=false;
	    	while(!hasFix||!stop){
	    		hasFix=getGPSFix(manager);
	    		Log.e("bbb","here");
	    		
	    		try {
					this.sleep(1000);
					
				} catch (InterruptedException e) {
			        
			        return;
			    }
	    	}
	    	Intent intent = new Intent(thisActivity, MapActivity.class);
	    	thisActivity.startActivity(intent);
	    	
	    }
	    
	    public synchronized void requestStop() {
	    	Log.e("stopped","stop");
			stop = true;
		}
	  }
}


