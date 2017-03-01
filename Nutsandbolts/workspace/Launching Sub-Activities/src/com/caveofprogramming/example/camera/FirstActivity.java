package com.caveofprogramming.example.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends Activity {

	private static final int OTHER_ACTIVITY = 7;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addLaunchListener();
	}

	private void addLaunchListener() {
		Button launch = (Button) findViewById(R.id.launch);

		launch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(FirstActivity.this, OtherActivity.class);

				startActivityForResult(i, OTHER_ACTIVITY);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case OTHER_ACTIVITY:
			Toast.makeText(this, "Returned with result code: " + resultCode,
					Toast.LENGTH_LONG).show();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
