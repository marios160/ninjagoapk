package pl.mario.ninjagoapk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.os.Environment;
import android.widget.RadioButton;
import android.widget.RadioGroup;



public class Dane implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String adres;
	int idRadioButton;
	String idsRadioButton;
	
	
	public Dane() {
		
		Dane x = getConfFile();
		if (x == null) {
			this.adres = "igi2.xaa.pl/nin/atak.txt";
			this.idRadioButton = 0;
			this.idsRadioButton = "dzwonek";
			setClassFile(this);
		} else {
			this.adres = x.adres;
			this.idRadioButton = x.idRadioButton;
			this.idsRadioButton = x.idsRadioButton;
		}
	}

	public static void setClassFile(Dane c) {
		ObjectOutputStream pl = null;
		try {

			File f = Environment.getExternalStorageDirectory();
			pl = new ObjectOutputStream(new FileOutputStream(f.getAbsolutePath() + "/NinjagoApk/conf.txt"));

			pl.writeObject(c);
			pl.flush();
			pl.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Dane getConfFile() {
		File f = Environment.getExternalStorageDirectory();
		File file2 = new File(f.getAbsolutePath() + "/NinjagoApk/conf.txt");
		Dane c = null;

		if (file2.exists()) {
			ObjectInputStream pl2 = null;
			try {
				pl2 = new ObjectInputStream(new FileInputStream(f.getAbsolutePath() + "/NinjagoApk/conf.txt"));
				c = (Dane) pl2.readObject();
				pl2.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}

		return c;
	}

}
