package akron.zippy.app;

import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import akron.zippy.app.Bldg_Navgtion_frgmnt.GetDirection;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BusFragment extends Activity {

	// Google Map
	private GoogleMap googleMap;
	double lat, lng;
	String startLocation = null, endLocation = null, endl, startl;
	LocationManager locManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_bus);

		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		locManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,
				500.0f, locationListener);

	}

	private void updateWithNewLocation(Location location) {

		lat = location.getLatitude();
		lng = location.getLongitude();
		try {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
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

		MarkerOptions markerdst = new MarkerOptions().position(
				new LatLng(41.070551, -81.513402)).title("South Route");
		// Changing marker icon
		markerdst.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.my_dest_icon));

		// adding marker
		googleMap.addMarker(markerdst);
		
		CameraUpdate center = CameraUpdateFactory
				.newLatLng(new LatLng(lat, lng));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
		
		googleMap.moveCamera(center);
		googleMap.animateCamera(zoom);

	}

	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {

			updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

}
