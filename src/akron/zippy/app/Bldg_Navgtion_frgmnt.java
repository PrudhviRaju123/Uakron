package akron.zippy.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Bldg_Navgtion_frgmnt extends Fragment {

	List<LatLng> polyz;
	ProgressDialog pDialog;
	GoogleMap googleMap;
	LocationManager locManager;
	double lat, lng;
	Geocoder geocoder;
	List<Address> addresses;
	String startLocation = null, endLocation = null, endl,startl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		endLocation = getArguments().getString("location");
		View rootView = inflater.inflate(R.layout.mapview, container, false);
		

		googleMap = ((SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		locManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,
				500.0f, locationListener);

		
		return rootView;
	}

	private void updateWithNewLocation(Location location) {

		lat = location.getLatitude();
		lng = location.getLongitude();
		try {
			geocoder = new Geocoder(getActivity(), Locale.getDefault());
			addresses = geocoder.getFromLocation(lat, lng, 1);
			startLocation = addresses.get(0).getAddressLine(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(location.getLatitude(), location.getLongitude()))
				.title("Your position");
		// Changing marker icon
		marker.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.my_marker_icon));

		// adding marker
		googleMap.addMarker(marker);

		 CameraUpdate center=
			        CameraUpdateFactory.newLatLng(new LatLng(lat,
			                                                 lng));
			    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

			    googleMap.moveCamera(center);
			    googleMap.animateCamera(zoom);

		
		 new GetDirection().execute();
	}

	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
			
			Toast.makeText(getActivity(),
					"Gps is Disabled.Please enabale gps", Toast.LENGTH_SHORT)
					.show();

			updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	class GetDirection extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading route. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			
			startLocation =startLocation.replace(" ", "+");
					//"311+Sterling+Ct"; 
					//
			endLocation = endLocation.replace(" ", "+"); 
					//"University+of+Akron+Student+Union";
					//
					//
					

			String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ startLocation
					+ ",+akron&destination="
					+ endLocation
					+ ",+akron&sensor=false";
			StringBuilder response = new StringBuilder();
			try {
				URL url = new URL(stringUrl);
				HttpURLConnection httpconn = (HttpURLConnection) url
						.openConnection();
				if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader input = new BufferedReader(
							new InputStreamReader(httpconn.getInputStream()),
							8192);
					String strLine = null;

					while ((strLine = input.readLine()) != null) {
						response.append(strLine);
					}
					input.close();
				}

				String jsonOutput = response.toString();

				JSONObject jsonObject = new JSONObject(jsonOutput);

				// routesArray contains ALL routes
				JSONArray routesArray = jsonObject.getJSONArray("routes");
				// Grab the first route
				JSONObject route = routesArray.getJSONObject(0);

				JSONObject poly = route.getJSONObject("overview_polyline");
				String polyline = poly.getString("points");
				polyz = decodePoly(polyline);

			} catch (Exception e) {

			}

			return null;

		}

		protected void onPostExecute(String file_url) {

			for (int i = 0; i < polyz.size() - 1; i++) {
				LatLng src = polyz.get(i);
				LatLng dest = polyz.get(i + 1);
				Polyline line = googleMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(20).color(Color.RED).geodesic(true));

			}
			pDialog.dismiss();

		}
	}

	/* Method to decode polyline points */
	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

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

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

}
