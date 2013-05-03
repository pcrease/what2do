package com.what2do.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.what2do.R;

public class Time_Activity extends Activity {

	private TextView timeText;
	private Integer selectedItem;
	private Activity timeActivity;
	private SeekBar timebar;
	private ListView lv;
	private ArrayAdapter<String> myarrayAdapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_);
		timeActivity=this;
		
		timeText = (TextView)findViewById(R.id.timeDescription);
		
		timebar = (SeekBar) findViewById(R.id.timeBar);
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
		
		final Button goToNext = (Button) findViewById(R.id.moveFromTime);
		
		goToNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent (timeActivity,SetCategoryActivity.class);
            	
            	Bundle b=new Bundle();				
				b.putDouble("lat", getIntent().getExtras().getDouble("lat"));
				b.putDouble("long",  getIntent().getExtras().getDouble("long"));
				b.putInt("time", timebar.getProgress()+5);
				b.putString("mode", myarrayAdapter.getItem(selectedItem));
				
				intent.putExtras(b);
            	startActivity(intent);
            }
       });
		
		lv = (ListView) findViewById(R.id.modeList);
		
		
		
		myarrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_item_row, 
				getResources().getStringArray(R.array.modeTravelList));
		lv.setAdapter(myarrayAdapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		    		    	
		        String selectedFromList =(String) (myAdapter.getItemAtPosition(myItemInt));
		        if(selectedItem==null){
		        	selectedItem=myItemInt;
		        	myAdapter.getChildAt(myItemInt).setBackgroundColor(Color.parseColor("#3fa2d4"));
		        	goToNext.setVisibility(View.VISIBLE);
		        	return;
		        }
		        else if(selectedItem!=myItemInt){
		        myAdapter.getChildAt(selectedItem).setBackgroundColor(Color.parseColor("#2F6699"));
		        selectedItem=myItemInt;
		        myAdapter.getChildAt(myItemInt).setBackgroundColor(Color.parseColor("#3fa2d4"));
		        
		      	}		
		        
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
