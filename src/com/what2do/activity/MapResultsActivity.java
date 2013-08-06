package com.what2do.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.what2do.R;
import com.what2do.foursquare.FourSquare_Category_Data;
import com.what2do.foursquare.Foursquare_Factory;
import com.what2do.foursquare.Foursquare_Venue;
import com.what2do.foursquare.Venue_Category;
import com.what2do.foursquare.Venue_Dataset;
import com.what2do.maps.Geo_Utilities;
import com.what2do.route.GoogleMapsRoutingFactory;
import com.what2do.route.JSON_Transit_GoogleDirection;
import com.what2do.route.JSON_Transit_GoogleDirection_Step;
import com.what2do.search.Context_Data;
import com.what2do.search.Relevance_Calculator;

public class MapResultsActivity extends FragmentActivity {

	private GoogleMap mMap;
	private ArrayList<Marker> resultMarkers = new ArrayList<Marker>();
	private final static String NUMBER_OF_RESULTS = "10";
	private Double lat;
	private Double lon;
	private Venue_Dataset vd;
	private Marker originLocation;
	private Map<String, JSON_Transit_GoogleDirection> transit_GoogleDirection_Map = new HashMap<String, JSON_Transit_GoogleDirection>();
	private Activity thisActivity;
	private String testStringID;
	private String targetCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_results);

		thisActivity = this;

		FourSquare_Category_Data.loadDataFromResources(this);

		resultMarkers.clear();

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.resultmap)).getMap();

		mMap.clear();

		lat = getIntent().getExtras().getDouble("lat");
		lon = getIntent().getExtras().getDouble("long");

		originLocation = mMap.addMarker(new MarkerOptions()
				.position(new LatLng(lat, lon))
				.title("current position")
				.draggable(false)
				.snippet("U R HERE")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

		String category = getIntent().getExtras()
				.getStringArrayList("category").get(0);
		int categoryIndex = FourSquare_Category_Data.getNamelist().indexOf(
				category);
		String categoryId = FourSquare_Category_Data.getIdlist().get(
				categoryIndex);

		targetCategory = category;

		LatLng mapCenter = new LatLng(lat, lon);
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 15.0f));

		String url = "https://api.foursquare.com/v2/venues/search?ll="
				+ lat
				+ ","
				+ lon
				+ "&categoryId="
				+ categoryId
				+ "&client_id=CK4ENMLHHV23P2QQNXOB2IWSMWF2MRYKBS5V2OXDPSSZYX2K&client_secret=YATORC45OMNLXNNZS3R0KIF0RT4VF1RQIO5FE23111VSMPAV&v=20130401&limit="
				+ NUMBER_OF_RESULTS;
		new FourSquareFetchTask().execute(url);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_results, menu);
		return true;

	}

	private class FourSquareFetchTask extends
			AsyncTask<String, String, StringBuilder> {

		ProgressDialog progressDialog;
		Context_Data context_Data;
		GoogleMapsRoutingFactory gmrf = new GoogleMapsRoutingFactory();

		protected void onPostExecute(StringBuilder result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result != null) {

				try {
					drawOnResultMap(vd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// error occured
			}
		}

		private void SetRoutingandSpatialContextData(Venue_Dataset vd) {
			context_Data = new Context_Data();

			for (Map.Entry<String, Foursquare_Venue> venue : vd.getVenue_Data()
					.entrySet()) {

				gmrf.setOrigin(new LatLng(lat, lon));
				gmrf.setDestination(new LatLng(venue.getValue().getLatitude(),
						venue.getValue().getLongitude()));
				// walking,driving,bicycling ,transit
				gmrf.setModeOfTransport("driving");

				try {
					JSON_Transit_GoogleDirection jtgd = Geo_Utilities
							.parseDirectionsJSON(
									gmrf.executeRouteCalculation(), venue
											.getValue().getId());
					transit_GoogleDirection_Map.put(venue.getValue().getId(),
							jtgd);
					venue.getValue().setjSON_Transit_GoogleDirection(jtgd);

					// set context data
					if (context_Data.getMaxWalkingDuration() < jtgd
							.getTotalWalkingDuration()) {
						context_Data.setMaxWalkingDuration(jtgd
								.getTotalWalkingDuration());
					}

					if (context_Data.getMaxTravelDuration() < jtgd
							.getDurationValue()) {
						context_Data.setMaxTravelDuration(jtgd
								.getDurationValue());
					}

					if (context_Data.getMinTravelDuration() > jtgd
							.getDurationValue()) {
						context_Data.setMinTravelDuration(jtgd
								.getDurationValue());
					}

					if (context_Data.getMinWalkingDuration() > jtgd
							.getTotalWalkingDuration()) {
						context_Data.setMinWalkingDuration(jtgd
								.getTotalWalkingDuration());
					}
					if (context_Data.getMaxNumberOfInstructions() < jtgd
							.getNumberOfInstructions()) {
						context_Data.setMaxNumberOfInstructions(jtgd
								.getNumberOfInstructions());
					}

					if (context_Data.getMinNumberOfInstructions() > jtgd
							.getNumberOfInstructions()) {
						context_Data.setMinNumberOfInstructions(jtgd
								.getNumberOfInstructions());
					}
					Log.e("context", " " + jtgd.getNumberOfInstructions() + " "
							+ jtgd.getDurationValue());
					// e.g. ----
					// venue.setWalkingDistance(jtgd.getwalkingdistance));
					testStringID = venue.getValue().getId();// test id
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		private void PopulateSocialContextDataandRelevanceValues(
				Venue_Dataset vd) {

			context_Data.setMaxCheckins(vd.getMaxCheckins());
			context_Data.setMinCheckins(vd.getMinCheckins());

			context_Data.setMaxLikes(vd.getMaxLikes());
			context_Data.setMinLikes(vd.getMinLikes());

			context_Data.setMaxTipCount(vd.getMaxTipCount());
			context_Data.setMinTipCount(vd.getMinTipCount());

			context_Data.setMaxUserCount(vd.getMaxUserCount());
			context_Data.setMinUserCount(vd.getMinUserCount());

			context_Data.setTargetCategory(targetCategory);

		}

		private void drawOnResultMap(Venue_Dataset vd) throws IOException {

			Log.e("context",
					" " + context_Data.getMaxCheckins() + " "
							+ context_Data.getMinCheckins() + " "
							+ context_Data.getMaxLikes() + " "
							+ context_Data.getMinLikes() + " "
							+ context_Data.getMaxUserCount() + " "
							+ context_Data.getMinUserCount() + " "
							+ context_Data.getMaxTipCount() + " "
							+ context_Data.getMinTipCount() + " "
							+ context_Data.getMaxNumberOfInstructions() + " "
							+ context_Data.getMinNumberOfInstructions() + " "
							+ context_Data.getMaxWalkingDuration() + " "
							+ context_Data.getMinWalkingDuration() + " "
							+ context_Data.getMaxTravelDuration() + " "
							+ context_Data.getMinTravelDuration());

			Relevance_Calculator relevance_Calculator = new Relevance_Calculator();
			relevance_Calculator.setCategoryWeight(0.5);

			// Hassle weights
			relevance_Calculator.setWalkingDistanceWeight(0.3);
			relevance_Calculator.setTravelDurationWeight(0.5);
			relevance_Calculator.setNumOfInstructionsWeight(0.2);

			relevance_Calculator.calculateOverallRelevance(context_Data, vd);

			JSON_Transit_GoogleDirection firstRoute = transit_GoogleDirection_Map
					.get(testStringID);

			List<JSON_Transit_GoogleDirection_Step> firstRouteSteps = firstRoute
					.getSteps();
			PolylineOptions rectOptions = new PolylineOptions();

			for (JSON_Transit_GoogleDirection_Step step : firstRouteSteps) {
				List<LatLng> lineCoords = step.getRoutePolyline();

				for (LatLng ll : lineCoords) {
					rectOptions.add(ll);
				}
			}

			Polyline polyline = mMap.addPolyline(rectOptions);

			for (Map.Entry<String, Foursquare_Venue> venue : vd.getVenue_Data()
					.entrySet()) {

				List<Venue_Category> objectCategories = venue.getValue()
						.getCategories();
				Log.e("working", venue.getValue().getRankHassle() + " "
						+ venue.getValue().getName());
				String primaryCategory = "";
				for (Venue_Category vc : objectCategories) {
					if (vc.isPrimary())
						primaryCategory = vc.getName();

					Log.e("catAll", vc.getName() + " " + vc.isPrimary());
				}

				primaryCategory = primaryCategory.replace("&", "and");
				primaryCategory = primaryCategory.replace("'", "");

				int categoryIndex = FourSquare_Category_Data.getNamelist()
						.indexOf(primaryCategory);
				String categoryId = FourSquare_Category_Data.getIdlist().get(
						categoryIndex);
				Log.e("cat", categoryId);

				Marker marker;

				AssetManager mg = getResources().getAssets();

				try {
					mg.open("foursquare_icons/" + categoryId + "_44.png");
				} catch (IOException ex) {

					String primaryCategoryName;
					primaryCategoryName = FourSquare_Category_Data
							.getHierachicalplacelist().get(categoryIndex);
					Log.e("marker info", primaryCategoryName);

					categoryIndex = FourSquare_Category_Data.getNamelist()
							.indexOf(primaryCategoryName);
					categoryId = FourSquare_Category_Data.getIdlist().get(
							categoryIndex);
				}

				marker = mMap.addMarker(new MarkerOptions()
						.position(
								new LatLng(venue.getValue().getLatitude(),
										venue.getValue().getLongitude()))
						.title(venue.getValue().getName())
						.draggable(false)
						.snippet(
								"Likes " + venue.getValue().getLikes()
										+ "\n Checkins"
										+ venue.getValue().getCheckinCount())
						.icon(BitmapDescriptorFactory
								.fromAsset("foursquare_icons/" + categoryId
										+ "_44.png")));

				resultMarkers.add(marker);
			}

			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			for (Marker m : resultMarkers) {
				builder.include(m.getPosition());
			}
			LatLngBounds bounds = builder.build();

			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 60);
			mMap.animateCamera(cu);

		}

		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(thisActivity, "Searching....",
					"Won't be a minute....", true);

		};

		@Override
		protected StringBuilder doInBackground(String... string) {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();

			HttpGet httpGet = new HttpGet((string[0].replace(" ", "%20")));
			Log.e("url", string[0].replace(" ", "%20"));
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("json", "Failed to download file");
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			vd = Foursquare_Factory.buildVenueDataset(builder.toString());

			SetRoutingandSpatialContextData(vd);
			PopulateSocialContextDataandRelevanceValues(vd);

			return builder;
		}

	}

}
