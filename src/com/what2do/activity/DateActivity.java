package com.what2do.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.what2do.R;

public class DateActivity extends Activity {

	private Activity thisActivity;
	private SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);

		thisActivity=this;
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		Button nextButton = (Button) findViewById(R.id.goFromDate);

		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//prefs.edit().putLong(getString(R.string.time_key), System.currentTimeMillis()).commit();
				TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
				int hour=timePicker.getCurrentHour();
				int minute=timePicker.getCurrentMinute();
				
				DatePicker datePicker =(DatePicker) findViewById(R.id.datePicker);
				int dayOfMonth=datePicker.getDayOfMonth();
				int month = datePicker.getMonth();
				int year = datePicker.getYear();
				
				Calendar dateTime = Calendar.getInstance();
				dateTime.set(Calendar.YEAR, year);
				dateTime.set(Calendar.MONTH,month);
				dateTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
				dateTime.set(Calendar.HOUR_OF_DAY,hour);
				dateTime.set(Calendar.MINUTE,minute);
				
				prefs.edit().putLong(getString(R.string.time_key), dateTime.getTimeInMillis()).commit();
				Log.e("time",""+dateTime.getTimeInMillis());
				Intent intent = new Intent(thisActivity,SearchTypeActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date, menu);
		return true;
	}

}
