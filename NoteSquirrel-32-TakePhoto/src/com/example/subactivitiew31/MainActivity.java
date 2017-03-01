package com.example.subactivitiew31;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.Toast;

public class MainActivity extends Activity {
	
	//private static final int OTHER_ACTIVITY = 7 ;
	public static final String LOG_TAG = "leagurru" ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addSnapButtonListener();
	}
	
	
	
	private void addSnapButtonListener(){
		
		Button snapButton = (Button) findViewById(R.id.snap);
		snapButton.setOnClickListener(new OnClickListener() {
			

			public void onClick(View arg0) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivity(i);
				
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
