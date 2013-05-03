package com.what2do.maps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;
import com.what2do.route.JSON_Transit_GoogleDirection;
import com.what2do.route.JSON_Transit_GoogleDirection_Step;
import com.what2do.route.JSON_Transit_GoogleDirection_SubStep;

public class Geo_Utilities {

	public static List<LatLng> decodePolylineString(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		double lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			// Log.e("point",lat/100000+" "+lng/100000);
			LatLng p = new LatLng(lat / 100000, lng / 100000);
			poly.add(p);
		}

		return poly;
	}

	public static JSON_Transit_GoogleDirection parseDirectionsJSON(
			String responseJSON, String destinationID) throws JSONException {

		JSON_Transit_GoogleDirection jSON_Transit_GoogleDirection = new JSON_Transit_GoogleDirection();

		jSON_Transit_GoogleDirection.setDestinationID(destinationID);

		JSONObject json = new JSONObject(responseJSON);
		Log.e("json",json.toString(0));

		JSONArray routes = json.getJSONArray("routes");
		JSONObject route = routes.getJSONObject(0);

		JSONArray legs = route.getJSONArray("legs");
		JSONObject leg = legs.getJSONObject(0);

		JSONObject duration = leg.getJSONObject("duration");
		jSON_Transit_GoogleDirection
				.setDurationValue(duration.getLong("value"));
		jSON_Transit_GoogleDirection
				.setDurationText(duration.getString("text"));

		JSONObject distance = leg.getJSONObject("distance");
		jSON_Transit_GoogleDirection
				.setDistanceValue(distance.getLong("value"));
		jSON_Transit_GoogleDirection
				.setDistanceText(distance.getString("text"));

		if (leg.has("arrival_time")) {
			JSONObject arrival_time = leg.getJSONObject("arrival_time");
			Date arrival_timeDate = new Date(
					arrival_time.getLong("value") * 1000l);
			jSON_Transit_GoogleDirection.setArrivalTime(arrival_timeDate);
			jSON_Transit_GoogleDirection.setArrivalTimeText(arrival_time
					.getString("text"));
		}

		if (leg.has("departure_time")) {
			JSONObject departure_time = leg.getJSONObject("departure_time");
			Date departure_timeDate = new Date(
					departure_time.getLong("value") * 1000l);
			jSON_Transit_GoogleDirection.setArrivalTime(departure_timeDate);
			jSON_Transit_GoogleDirection.setArrivalTimeText(departure_time
					.getString("text"));
		}

		JSONObject start_location = leg.getJSONObject("start_location");
		jSON_Transit_GoogleDirection.setStartLocation(new LatLng(start_location
				.getDouble("lat"), start_location.getDouble("lng")));

		JSONObject end_location = leg.getJSONObject("end_location");
		jSON_Transit_GoogleDirection.setEndLocation(new LatLng(end_location
				.getDouble("lat"), end_location.getDouble("lng")));

		jSON_Transit_GoogleDirection.setStartAddress(leg
				.getString("start_address"));
		jSON_Transit_GoogleDirection
				.setEndAddress(leg.getString("end_address"));

		JSONArray steps = leg.getJSONArray("steps");

		// three steps mean you have to change twice
		jSON_Transit_GoogleDirection.setNumberOfChanges(steps.length() - 1);

		for (int i = 0; i < steps.length(); i++) {
			JSON_Transit_GoogleDirection_Step jSON_Transit_GoogleDirection_Step = new JSON_Transit_GoogleDirection_Step();
			JSONObject step = (JSONObject) steps.get(i);

			JSONObject stepDuration = step.getJSONObject("duration");
			jSON_Transit_GoogleDirection_Step.setDurationValue(stepDuration
					.getLong("value"));
			jSON_Transit_GoogleDirection_Step.setDurationText(stepDuration
					.getString("text"));

			JSONObject stepDistance = step.getJSONObject("distance");
			jSON_Transit_GoogleDirection_Step.setDistanceValue(stepDistance
					.getLong("value"));
			jSON_Transit_GoogleDirection_Step.setDistanceText(stepDistance
					.getString("text"));

			jSON_Transit_GoogleDirection_Step.setHtmlInstruction(step
					.getString("html_instructions"));

			JSONObject stepStart_location = step
					.getJSONObject("start_location");
			jSON_Transit_GoogleDirection.setStartLocation(new LatLng(
					stepStart_location.getDouble("lat"), stepStart_location
							.getDouble("lng")));

			JSONObject stepEnd_location = step.getJSONObject("end_location");
			jSON_Transit_GoogleDirection.setEndLocation(new LatLng(
					stepEnd_location.getDouble("lat"), stepEnd_location
							.getDouble("lng")));

			jSON_Transit_GoogleDirection_Step.setTravelMode(step
					.getString("travel_mode"));
			if (step.getString("travel_mode").equals("WALKING")) {
				jSON_Transit_GoogleDirection
						.setTotalWalkingDuration(jSON_Transit_GoogleDirection
								.getTotalWalkingDuration()
								+ stepDuration.getInt("value"));
			}

			JSONObject polyline = step.getJSONObject("polyline");
			String polylinePointsEncoded = polyline.getString("points");

			jSON_Transit_GoogleDirection_Step
					.setRoutePolyline(decodePolylineString(polylinePointsEncoded));

			JSONArray subSteps = leg.getJSONArray("steps");

			jSON_Transit_GoogleDirection.setNumberOfInstructions(jSON_Transit_GoogleDirection.getNumberOfInstructions()+1);
			
			for (int j = 0; j < subSteps.length(); j++) {
				
				jSON_Transit_GoogleDirection.setNumberOfInstructions(jSON_Transit_GoogleDirection.getNumberOfInstructions()+1);
				
				JSON_Transit_GoogleDirection_SubStep jSON_Transit_GoogleDirection_SubStep = new JSON_Transit_GoogleDirection_SubStep();
				JSONObject subStep = (JSONObject) subSteps.get(i);

				JSONObject subStepDuration = subStep.getJSONObject("duration");
				jSON_Transit_GoogleDirection_SubStep
						.setDurationValue(subStepDuration.getLong("value"));
				jSON_Transit_GoogleDirection_SubStep
						.setDurationText(subStepDuration.getString("text"));

				JSONObject subStepDistance = subStep.getJSONObject("distance");
				jSON_Transit_GoogleDirection_SubStep
						.setDistanceValue(subStepDistance.getLong("value"));
				jSON_Transit_GoogleDirection_SubStep
						.setDistanceText(subStepDistance.getString("text"));

				jSON_Transit_GoogleDirection_SubStep.setHtmlInstruction(step
						.getString("html_instructions"));

				JSONObject subStepStart_location = subStep
						.getJSONObject("start_location");
				jSON_Transit_GoogleDirection_SubStep
						.setStartLocation(new LatLng(subStepStart_location
								.getDouble("lat"), subStepStart_location
								.getDouble("lng")));

				JSONObject subStepEnd_location = subStep
						.getJSONObject("end_location");
				jSON_Transit_GoogleDirection_SubStep.setEndLocation(new LatLng(
						subStepEnd_location.getDouble("lat"),
						subStepEnd_location.getDouble("lng")));

				jSON_Transit_GoogleDirection_SubStep.setTravelMode(subStep
						.getString("travel_mode"));

				JSONObject subSteppolyline = subStep.getJSONObject("polyline");
				String subSteppolylinePointsEncoded = subSteppolyline
						.getString("points");

				jSON_Transit_GoogleDirection_SubStep
						.setRoutePolyline(decodePolylineString(subSteppolylinePointsEncoded));

				jSON_Transit_GoogleDirection_Step
						.addSteps(jSON_Transit_GoogleDirection_SubStep);
			}

			jSON_Transit_GoogleDirection
					.addSteps(jSON_Transit_GoogleDirection_Step);
		}

		return jSON_Transit_GoogleDirection;
	}

}
