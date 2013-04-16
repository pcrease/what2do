package com.what2do.activity;

import com.what2do.R;
import com.what2do.R.layout;
import com.what2do.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Time_Activity extends Activity {

	private TextView timeText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_);
		
		timeText = (TextView)findViewById(R.id.timeDescription);
		
		SeekBar timebar = (SeekBar) findViewById(R.id.timeBar);
		timebar.setMax(115);
		timebar.setProgress(10);
		
		timebar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

		    public void onStopTrackingTouch(SeekBar seekBar) {
		        // TODO Auto-generated method stub
		    }

		    
		    public void onStartTrackingTouch(SeekBar seekBar) {
		        // TODO Auto-generated method stub
		    }

		    
		    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		        // TODO Auto-generated method stub
		    	timeText.setText("I Have "+(seekBar.getProgress()+5)+" minutes");
		    }
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_, menu);
		return true;
	}

}
