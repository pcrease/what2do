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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.what2do.R;
import com.what2do.foursquare.FourSquare_Category_Data;

public class SetCategoryActivity extends Activity {
	private Activity thisActivity;
	private LinearLayout termLayout;
	private AutoCompleteTextView autoTextView;
	private int numberOfTerms;
	private HashMap<Integer, String> selectedItems = new HashMap<Integer, String>();
	private static final int NUMBER_OF_SEARCH_TERMS_ALLOWED = 4;
	private Button addTerm;
	private Button searchButton;
	private ListView lv;
	private ArrayList<String> categoryTypes = new ArrayList<String>();
	private boolean isKeyWordSearch = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_category);
		thisActivity = this;

				
		ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this,
				R.layout.listview_item_row, getResources().getStringArray(
						R.array.activityList));
		lv = (ListView) findViewById(R.id.actList);
		lv.setAdapter(myarrayAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {

			}
		});

		termLayout = (LinearLayout) findViewById(R.id.keywordLayout);
		numberOfTerms = termLayout.getChildCount() + 1;

		final Button Keyword = (Button) findViewById(R.id.Keywordbtn);
		final Button Activity = (Button) findViewById(R.id.Activitybtn);
		searchButton = (Button) findViewById(R.id.startSearch);

		Activity.setBackgroundColor(Color.parseColor("#2F6699"));

		Keyword.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removeActivitySearchandAddKeyword();
				Activity.setBackgroundColor(Color.parseColor("#2F6699"));
				Activity.setTextAppearance(thisActivity,
						R.style.ButtonTextGreyGlow);
				Keyword.setBackgroundColor(Color.parseColor("#3fa2d4"));
				Keyword.setTextAppearance(thisActivity, R.style.ButtonText);
				isKeyWordSearch = true;
			}
		});

		Activity.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removeKeywordSearchandAddActivity();
				Keyword.setBackgroundColor(Color.parseColor("#2F6699"));
				Keyword.setTextAppearance(thisActivity,
						R.style.ButtonTextGreyGlow);
				Activity.setBackgroundColor(Color.parseColor("#3fa2d4"));
				Activity.setTextAppearance(thisActivity, R.style.ButtonText);
				isKeyWordSearch = false;
			}
		});

		String[] categories = loadCategories("en");

		autoTextView = (AutoCompleteTextView) findViewById(R.id.keywordAutoComplete);
		setTheme(android.R.style.Theme);

		autoTextView.setThreshold(1);
		autoTextView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, categories));

		addTerm = (Button) findViewById(R.id.addSearchTerm);
		addTerm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (numberOfTerms > NUMBER_OF_SEARCH_TERMS_ALLOWED) {
					Toast.makeText(thisActivity.getApplicationContext(),
							"Only 5 key words are allowed...",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!selectedItems.containsValue(autoTextView.getText()
						.toString())) {
					selectedItems.put(numberOfTerms, autoTextView.getText()
							.toString());
					addKeyword(autoTextView.getText().toString());
				} else
					Toast.makeText(thisActivity.getApplicationContext(),
							"keyword is already part of your search list",
							Toast.LENGTH_SHORT).show();

				autoTextView.setText("");
			}
		});

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(thisActivity,
						MapResultsActivity.class);
				
				Bundle b = new Bundle();
				b.putDouble("lat", getIntent().getExtras().getDouble("lat"));
				b.putDouble("long", getIntent().getExtras().getDouble("long"));
				b.putInt("time", getIntent().getExtras().getInt("time"));
				b.putString("mode", getIntent().getExtras().getString("mode"));

				if (isKeyWordSearch == true) {
					b.putStringArrayList("category",
							 categoryTypes);
				}
				
				intent.putExtras(b);
				startActivity(intent);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_category, menu);
		return true;
	}

	private String[] loadCategories(String locale) {
		String[] returnArray = getResources().getStringArray(
				R.array.sqrCategoryNames);
		return returnArray;
	}

	private void addKeyword(String keyword) {

		categoryTypes.add(keyword);

		final TextView keyText = new TextView(this);
		keyText.setText(keyword + " (click to remove)");
		keyText.setBackgroundResource(R.layout.keyword_items);
		keyText.setId(numberOfTerms);
		numberOfTerms += 1;
		keyText.setTextAppearance(this, R.style.smallButtonText);

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right,
										// bottom);

		keyText.setLayoutParams(llp);

		keyText.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(thisActivity.getApplicationContext(),
						"removing", Toast.LENGTH_SHORT).show();

				String stringToRemove = keyText.getText().toString();

				// removes the click to remove text before removing it from the
				// arraylist

				categoryTypes.remove(stringToRemove.substring(0,
						stringToRemove.length() - 18));

				selectedItems.remove(keyText.getId());
				termLayout.removeView(keyText);
				numberOfTerms -= 1;
				Log.e("length", "" + categoryTypes.size());
			}
		});
		Log.e("length", "" + categoryTypes.size());
		termLayout.addView(keyText);
	}

	private void removeKeywordSearchandAddActivity() {
		autoTextView.setVisibility(View.GONE);
		addTerm.setVisibility(View.GONE);
		findViewById(R.id.keywordLayout).setVisibility(View.GONE);
		findViewById(R.id.activityLayout).setVisibility(View.VISIBLE);
	}

	private void removeActivitySearchandAddKeyword() {
		autoTextView.setVisibility(View.VISIBLE);
		addTerm.setVisibility(View.VISIBLE);
		findViewById(R.id.keywordLayout).setVisibility(View.VISIBLE);
		findViewById(R.id.activityLayout).setVisibility(View.GONE);
	}

}
