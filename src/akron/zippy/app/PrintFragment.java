package akron.zippy.app;



import java.io.File;





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PrintFragment extends Activity implements OnClickListener {

	EditText print_file_nm;
	Button brwsBtn, upBtn;
	ImageButton Gdrive_Btn, SD_Btn, Dbox_btn;
	TextView file_print, Imgtxt;
	Spinner spinner;
	File CurrDir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_print);

		Bundle basket = getIntent().getExtras();
		if (basket != null) {
			
			// file_print.setText(file_to_print);

		}

		Gdrive_Btn = (ImageButton) findViewById(R.id.google_drv_btn);
		SD_Btn = (ImageButton) findViewById(R.id.sd_Card_btn);
		Dbox_btn = (ImageButton) findViewById(R.id.drpbox_btn);
		upBtn = (Button) findViewById(R.id.uploadBtn);
		spinner = (Spinner) findViewById(R.id.spinner1);
		file_print = (TextView) findViewById(R.id.files_to_print);

		Gdrive_Btn.setOnClickListener(this);
		SD_Btn.setOnClickListener(this);
		upBtn.setOnClickListener(this);
		Dbox_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.sd_Card_btn:
			Intent listpage = new Intent("akron.zippy.app.SD_CARD");
			startActivity(listpage);
			break;

		case R.id.uploadBtn:
			break;

		case R.id.google_drv_btn:

			Intent GglDrvpage = new Intent("akron.zippy.app.GOOGLEDRIVEACCESS");
			startActivity(GglDrvpage);
			break;

		case R.id.drpbox_btn:

			Toast.makeText(this, "coding in progress", Toast.LENGTH_SHORT)
					.show();
			break;

		}

	}

}
