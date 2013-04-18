package com.what2do.activity;

import com.what2do.R;
import com.what2do.R.layout;
import com.what2do.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SetCategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_category);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_category, menu);
		return true;
	}

}
