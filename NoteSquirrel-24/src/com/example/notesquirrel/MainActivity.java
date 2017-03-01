/*
 * 
 * 
 * 25-verifyingthepasspointsavi.avi (video muy extenso: 47 minutos)
 * 24-asynchronous-tasks.avi
 * 23-retrieving-database-values.avi
 * 22-inserting-database-values.avi
 * 21-creating-databases.avi
 * 20-the-event-listener-pattern.avi
 * 19-alert-dialogs.avi
 * 18-getting-touch-coordinates.avi 
 * 17-displaying-images.avi         http://developer.android.com/guide/practices/screens_support.html
 * 16-adding-a-new-activity.avi
 * 15-toasts.avi
 * 14-preferences.avi               http://developer.android.com/guide/topics/data/data-storage.html
 * 13-debugging-on-your-phone.avi
 * 12-icons.avi
 * 11-string-resources.avi
 * 10-reading-files-from-internal-storage.avi
 * 09-saving-files-to-internal-storage.avi
 * 08-debugging-with-ddms-and-logcat.avi
 * 07-responding-to-button-clicks.avi
 * 06-buttons-and-linear-layouts.avi
 * 04-hello-world.avi
 * 05-the-edittext-view.avi
 * 04-hello-world.avi
 * 
 */

package com.example.notesquirrel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	public static final String DEBUGTAG = "LG";
	public static final String TEXTFILE = "notesquirrel.txt";
	public static final String FILESAVED = "FileSaved";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addSaveButtonListener();
		
		
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		boolean fileSaved = prefs.getBoolean(FILESAVED, false);
		
		if (fileSaved)			loadSavedFile();

		
	}

	
	private void loadSavedFile(){
		try {
			FileInputStream fis = openFileInput(TEXTFILE);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fis)));
			
			EditText editText = (EditText) findViewById(R.id.text);
			
			String line;
			
			while ( (line=reader.readLine()) != null ){
				editText.append(line);
				editText.append("\n");
			}
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(DEBUGTAG, "Unable to load file");
		}
		
		
		
		
	}
	
	private void addSaveButtonListener(){
		Button saveBtn = (Button)findViewById(R.id.save);
		saveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText editText = (EditText) findViewById(R.id.text);
				String text = editText.getText().toString();
				
				try {
					FileOutputStream fos = openFileOutput(TEXTFILE, Context.MODE_PRIVATE);
					fos.write(text.getBytes());
					fos.close();

					SharedPreferences prefs = getPreferences(MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putBoolean(FILESAVED, true);
					editor.commit();
					
					Toast.makeText(MainActivity.this, getString( R.string.OkSave ), Toast.LENGTH_SHORT).show();
					
				} catch (Exception e) {
					// TODO: handle exception
					Log.d(DEBUGTAG, "Unable to save file");
					Toast.makeText(MainActivity.this, getString( R.string.NotSaved ), Toast.LENGTH_SHORT).show();
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
