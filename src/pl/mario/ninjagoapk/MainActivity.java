package pl.mario.ninjagoapk;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import pl.mario.ninjagoapk.R.id;

public class MainActivity extends Activity {

	Button sprawdz;
	static Dane dane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File f = Environment.getExternalStorageDirectory();
		File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "NinjagoApk");
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		dane = new Dane();
		EditText t = (EditText) findViewById(R.id.adres);
		t.setText(dane.adres);
		RadioButton dzwiek ;
		if(dane.idsRadioButton.contains("dzwonek")){
			dzwiek = (RadioButton) findViewById(id.radio0);
		} else if (dane.idsRadioButton.contains("alarm")){
			dzwiek = (RadioButton) findViewById(id.radio2);
		} else {
			dzwiek = (RadioButton) findViewById(id.radio1);
		}
		dzwiek.setChecked(true);
		
		Button sprawdz = (Button) findViewById(R.id.sprawdz);
		sprawdz.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText t = (EditText)findViewById(R.id.adres);
				dane.adres = t.getText().toString();
				RadioGroup radio = (RadioGroup) findViewById(id.radioGroup1);
				dane.idRadioButton = radio.getCheckedRadioButtonId();
				RadioButton b = (RadioButton) findViewById(dane.idRadioButton);
				dane.idsRadioButton = b.getText().toString();
				if(!dane.adres.isEmpty()){
					Dane.setClassFile(dane);
					startMyService();
				} else {
					toast("Wpisz adres strony!!!");
				}
				
			}
		});
		
		Button stop = (Button) findViewById(R.id.zamknij);
		stop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopMyService();
				
			}
		});

	}
	

	public void toast(String txt){
		Toast.makeText(this.getApplicationContext(), txt, Toast.LENGTH_LONG).show();
	}
	
	private void startMyService() {
		Intent serviceIntent = new Intent(this, NinService.class);
		startService(serviceIntent);
	}

	private void stopMyService() {
		Intent serviceIntent = new Intent(this, NinService.class);
		stopService(serviceIntent);
	}

}
