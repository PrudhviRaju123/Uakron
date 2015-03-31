package akron.zippy.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Sd_card extends Activity {

	private ArrayList<String> items = null;

	String allowed_formats[] = { "pdf", "doc", "rtf", "txt", "docx", "ppt",
			"pptx", "jpeg", "mpeg", "png", "jpg" };
	int folder_chek_enable = 0;
	String array_items[];
	File parent, child_path;
	String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getFiles(new File("/storage/emulated/0").listFiles());

	}

	private void getFiles(File[] files) {
		items = new ArrayList<String>();
		// items.add(files[0].getParent());
		// items.add("Back ..");

		parent = files[0].getParentFile();

		for (int i = 0; i < files.length; i++) {

			if (files[i].isDirectory()) {
				items.add(files[i].getName());
			}
		}

		for (int j = 0; j < files.length; j++) {
			if (!files[j].isDirectory()) {

				if (Arrays.asList(allowed_formats).contains(
						files[j].toString()
								.toLowerCase()
								.substring(
										files[j].toString().indexOf(".") + 1,
										files[j].toString().length())))

				{
					items.add(files[j].getName());
				}
			}
		}

		// String array_items[] = items.toArray(new String[items.size()]);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(
				Sd_card.this);

		builder1.setTitle("Select the files to print");
		builder1.setPositiveButton("ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Bundle MainActy = new Bundle();
				// MainActy.putString("key", file.getPath().toString());

				Intent Mainpage = new Intent(Sd_card.this,
						PrintFragment.class);
				Mainpage.putExtras(MainActy);
				startActivity(Mainpage);

			}
		});
		builder1.setNegativeButton("Back", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				getFiles(new File(parent.getParent()).listFiles());

			}
		});

		builder1.setItems(items.toArray(new String[items.size()]),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// TODO Auto-generated method stub

						String text = items.toArray(new String[items.size()])[which];
						if (new File(parent.toString() + "/" + text)
								.isDirectory()) {

							if (new File(parent.toString() + "/" + text).list().length == 0) {
								Toast.makeText(getApplicationContext(),
										"Empty directory", Toast.LENGTH_SHORT)
										.show();
							} else {
								getFiles(new File(parent.toString() + "/"
										+ text).listFiles());
							}

						} else {
							Toast.makeText(getApplicationContext(),
									text + " is queued to print",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

		AlertDialog alertDialog_folder = builder1.create();

		// show it
		alertDialog_folder.show();

		// ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
		// R.layout.fragment_main, items);
		// setListAdapter(fileList);
	}

}
