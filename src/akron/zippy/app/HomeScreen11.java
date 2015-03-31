package akron.zippy.app;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class HomeScreen11 extends Activity

{

	GridView gridView;
	ArrayList<HomeScreen13> gridArray = new ArrayList<HomeScreen13>();
	HomeScreen12 customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen_11);
		// set grid view item
		Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.home_icon);
		Bitmap campus_search = BitmapFactory.decodeResource(
				this.getResources(), R.drawable.building_search);
		Bitmap people_search = BitmapFactory.decodeResource(
				this.getResources(), R.drawable.people_search);
		Bitmap Roo_bus = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.roo_bus_srch);
		Bitmap printer = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.document_print);

		gridArray.add(new HomeScreen13(homeIcon, "Home"));
		gridArray.add(new HomeScreen13(campus_search, "campus"));
		gridArray.add(new HomeScreen13(people_search, "people"));
		gridArray.add(new HomeScreen13(Roo_bus, "Roo Bus"));
		gridArray.add(new HomeScreen13(printer, "Printer"));

		gridView = (GridView) findViewById(R.id.gridView1);

		customGridAdapter = new HomeScreen12(this, R.layout.homescreen_12,
				gridArray);
		gridView.setAdapter(customGridAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				
				
				switch (position) {
				case 0:
					Toast.makeText(getApplicationContext(),
							"Coding in progress for home", Toast.LENGTH_SHORT)
							.show();
					break;

				case 1:

					Intent bldgPg = new Intent(
							"akron.zippy.app.BUILDINGFRAGMENT");
					startActivity(bldgPg);

					break;

				case 2:
					Intent popg=new Intent("akron.zippy.app.PEOPLEFRAGMENT");
					startActivity(popg);
					break;

				case 3:
					Intent bsfg=new Intent("akron.zippy.app.BUSFRAGMENT");
					startActivity(bsfg);
					break;

				case 4:
					Intent prtfg = new Intent("akron.zippy.app.PRINTFRAGMENT");
					startActivity(prtfg);
					break;
				}
       
			}
		});
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
		// Handle action bar actions click

		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;

		case R.id.pref:

			Intent prefpage = new Intent("akron.zippy.app.PREFS");
			startActivity(prefpage);
			finish();

			break;

		case R.id.exit:

			finish();

			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage("Do you want to Exit")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();

	}

}
