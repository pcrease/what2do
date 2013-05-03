package com.what2do.foursquare;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

import com.what2do.R;

public class FourSquare_Category_Data {

	private static ArrayList<String> idList = new ArrayList<String>();
	private static ArrayList<String> nameList = new ArrayList<String>();
	private static ArrayList<String> pluralNameList = new ArrayList<String>();
	private static ArrayList<String> hierachicalPlaceList = new ArrayList<String>();

	public static void loadDataFromResources(Context context) {
		idList = new ArrayList(Arrays.asList(context.getResources()
				.getStringArray(R.array.sqrCategoryIds)));

		nameList = new ArrayList(Arrays.asList(context.getResources()
				.getStringArray(R.array.sqrCategoryNames)));

		pluralNameList = new ArrayList(Arrays.asList(context.getResources()
				.getStringArray(R.array.sqrCategoryPluralNames)));

		hierachicalPlaceList = new ArrayList(Arrays.asList(context
				.getResources().getStringArray(R.array.sqrHierarichalPlace)));
	}

	public static ArrayList<String> getIdlist() {
		return idList;
	}

	public static ArrayList<String> getNamelist() {
		return nameList;
	}

	public static ArrayList<String> getPluralnamelist() {
		return pluralNameList;
	}

	public static ArrayList<String> getHierachicalplacelist() {
		return hierachicalPlaceList;
	}

}
