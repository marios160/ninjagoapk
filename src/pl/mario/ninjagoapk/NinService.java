package pl.mario.ninjagoapk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;
import pl.mario.ninjagoapk.R.id;

public class NinService extends Service {

	private Toast toast;
	private Timer timer;
	private TimerTask timerTask;
	private static int MY_NOTIFICATION = 1;
	private NotificationManager notificationManager;
	private Notification notification;
	Dane dane;

	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			if (dane.adres.isEmpty()) {
				showToast("Brak adresu strony!");
				onDestroy();
			}
			String txt = readHTML(dane.adres);
			if (!txt.isEmpty()) {
				showNotification("Atak", txt, txt);
			}
		}
	}

	public String readHTML(String adres) {
		StringBuffer content = new StringBuffer();
		try {

			URL url = null;

			url = new URL("http://" + adres);
			BufferedReader br = null;

			br = new BufferedReader(new InputStreamReader(url.openStream()));

			String line = null;
			while ((line = br.readLine()) != null) {
				content.append(line).append("\n");
			}
			if (br != null) {

				br.close();
			}
		} catch (IOException ex) {
			showToast("HtmlReader: read: " + ex);
		}
		return content.toString();
	}

	public void showNotification(String temat, String tytul, String tresc) {
		Uri alarmSound;
		switch (dane.idRadioButton) {
		case R.id.radio0:
			alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			break;
		case R.id.radio1:
			alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			break;
		case R.id.radio2:
			alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
			break;
		default:
			alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			break;
		}
		
		Notification notification = new Notification.Builder(this).setTicker(temat).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(tytul).setContentText(tresc).setAutoCancel(true)
				.setVibrate(new long[] { 0, 400, 100, 400 }).setSound(alarmSound).build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(MY_NOTIFICATION, notification);
		MY_NOTIFICATION++;
	}

	private void showToast(String text) {
		toast.setText(text);
		toast.show();
	}

	private void writeToLogs(String message) {
		Log.d("HelloServices", message);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		writeToLogs("Called onCreate() method.");
		timer = new Timer();
		toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		writeToLogs("Called onStartCommand() methond");
		clearTimerSchedule();
		initTask();
		timer.scheduleAtFixedRate(timerTask, 10 * 1000, 10 * 1000);
		showToast("NinApk dzia³a w tle");
		return super.onStartCommand(intent, flags, startId);
	}

	private void clearTimerSchedule() {
		if (timerTask != null) {
			timerTask.cancel();
			timer.purge();
		}
	}

	private void initTask() {
		timerTask = new MyTimerTask();
		dane = new Dane();
	}

	@Override
	public void onDestroy() {
		writeToLogs("Called onDestroy() method");
		clearTimerSchedule();
		showToast("NinApk zatrzymana");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
