package akron.zippy.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	boolean timespan;
	int timecount;
	Intent nextpage;
	SharedPreferences getprefs;
	String builder = "HomeScreen11";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		/*
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */

		setContentView(R.layout.logo_xml);

		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		builder = sharedPrefs.getString("listtheme", "NULL");

		try {

			Class className = Class.forName("akron.zippy.app." + builder);
			Intent nextpage = new Intent(MainActivity.this, className);
			startActivity(nextpage);
			finish();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater menuinf = getMenuInflater();
		menuinf.inflate(R.menu.main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// toggle nav drawer on selecting action bar app icon/title
		// Handle action bar actions click

		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;

		case R.id.pref:

			Intent prefpage = new Intent("akron.zippy.app.PREFS");
			startActivity(prefpage);

			break;

		case R.id.exit:

			finish();

			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

}
