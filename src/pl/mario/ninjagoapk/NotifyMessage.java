package pl.mario.ninjagoapk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NotifyMessage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_message);
	
		TextView txt= (TextView) findViewById(R.id.TextView);
        
        txt.setText("Activity after click on notification");
        setContentView(txt);
	}

	
}
