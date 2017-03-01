package com.caveofprogramming.android.notesquirrel;

/*
 * Thanks to Nathan Hazout for supplying me with this reconstructed project from lecture 24,
 * which I sadly neglected to save as I went along! That in turn gave us all a bit of a headache,
 * Since the original version of lecture 25 was pretty confusing, and I wasn't able to redo it
 * without going through the first 24 lectures myself, which I couldn't face doing.
 * A big thanks to Nathan, and apologies to everyone who put up with the original lecture 25
 * videos. - John Purcell, October 11 2013
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String DEBUGTAG = "JWP";
	public static final String TEXTFILE = "notesquirrel.txt";
	public static final String FILESAVED = "FileSaved";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addSaveButtonListener();

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		boolean fileSaved = prefs.getBoolean(FILESAVED, false);
		if (fileSaved) {
			loadSavedFile();
		}
	}

	private void loadSavedFile() {
		try {
			FileInputStream fis = openFileInput(TEXTFILE);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new DataInputStream(fis)));

			EditText editText = (EditText) findViewById(R.id.text);
			String line;
			while ((line = reader.readLine()) != null) {
				editText.append(line);
				editText.append("\n");
			}
			fis.close();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(DEBUGTAG, "Unable to read file");
			Toast.makeText(MainActivity.this, getString(R.string.toast_cant_read), Toast.LENGTH_LONG).show();
			
		}
	}

	private void addSaveButtonListener() {
		Button saveBtn = (Button) findViewById(R.id.save);
		saveBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(R.id.text);
				String text = editText.getText().toString();

				try {
					FileOutputStream fos = openFileOutput(TEXTFILE,
							Context.MODE_PRIVATE);
					fos.write(text.getBytes());
					fos.close();

					SharedPreferences prefs = getPreferences(MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putBoolean(FILESAVED, true);
					editor.commit();
					
					Toast.makeText(MainActivity.this, getString(R.string.toast_saved), Toast.LENGTH_LONG).show();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d(DEBUGTAG, "Unable to save file");
					Toast.makeText(MainActivity.this, getString(R.string.toast_cant_save), Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
