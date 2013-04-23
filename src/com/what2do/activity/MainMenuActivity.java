package com.what2do.activity;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.what2do.R;
import com.what2do.R.layout;
import com.what2do.R.menu;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	private SharedPreferences prefs;
	
	private Activity thisActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		thisActivity=this;
		
		
		
		
		final Button nowButton = (Button) findViewById(R.id.searchNow);
		
		nowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	prefs.edit().putLong(getString(R.string.time_key), System.currentTimeMillis()).commit();
            	Log.e("time",""+System.currentTimeMillis());
            	Intent intent = new Intent(thisActivity,SearchTypeActivity.class);
            	startActivity(intent);
            }
       });
		
		final Button laterButton = (Button) findViewById(R.id.searchLater);
		
		laterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(thisActivity,DateActivity.class);
            	startActivity(intent);
            }
       });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
