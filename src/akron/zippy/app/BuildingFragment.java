package akron.zippy.app;


import java.util.ArrayList;
import java.util.List;


import com.google.android.gms.maps.GoogleMap;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class BuildingFragment extends FragmentActivity  implements OnClickListener {

	protected static final int RESULT_SPEECH = 1;

	private ImageButton btnSpeak;
	private Button srchBldg;
	private EditText edtTxt;
	String sample;
	double lat, lng;
	Geocoder geocoder;
	List<Address> addresses;
	GoogleMap googleMap;
	LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_bldg);

		edtTxt = (EditText) findViewById(R.id.editText1);

		btnSpeak = (ImageButton) findViewById(R.id.imageButton1);

		srchBldg = (Button) findViewById(R.id.button1);

		Toast.makeText(this, "GPS is turned on ", Toast.LENGTH_LONG).show();
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			shownotification();

		} else {
			showGPSDisabledAlertToUser();
		}

		btnSpeak.setOnClickListener(this);
		srchBldg.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "please turn gps  off ", Toast.LENGTH_LONG).show();
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				edtTxt.setText(text.get(0));
			}
			break;
		}

		}
	}

	private void shownotification() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_LONG)
				.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.imageButton1:

			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

			try {
				startActivityForResult(intent, RESULT_SPEECH);
				edtTxt.setText("");
			} catch (ActivityNotFoundException a) {
				a.printStackTrace();
			}

			break;

		case R.id.button1:

			
			Bundle bundle = new Bundle();
			bundle.putString("location", edtTxt.getText().toString());

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			Fragment fragment = new Bldg_Navgtion_frgmnt();
			fragment.setArguments(bundle);
			fragmentTransaction.add(R.id.fragment1, fragment);
			fragmentTransaction.commit();

			break;
		}

	}

	private void showGPSDisabledAlertToUser() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(
						"GPS is disabled in your device."+"\n"
						+ "Goto Settings Page To Enable GPS under location")
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								startActivity(
								        new Intent(Settings.ACTION_SETTINGS));
													}
						});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

}
