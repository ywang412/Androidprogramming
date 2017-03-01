package com.caveofprogramming.android.notesquirrel;

/*
 * Thanks to Nathan Hazout for supplying me with this reconstructed project from lecture 24,
 * which I sadly neglected to save as I went along! That in turn gave us all a bit of a headache,
 * Since the original version of lecture 25 was pretty confusing, and I wasn't able to redo it
 * without going through the first 24 lectures myself, which I couldn't face doing.
 * A big thanks to Nathan, and apologies to everyone who put up with the original lecture 25
 * videos. - John Purcell, October 11 2013
 */

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class ImageActivity extends Activity implements PointCollecterListener {

	private static final String PASSWORD_SET = "PASSWORD_SET";
	private PointCollector pointCollector = new PointCollector();
	private Database db = new Database(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		addTouchListener();
		
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);
		
		if(!passpointsSet) {
			showSetPasspointsPrompt();
		}
		
		pointCollector.setListener(this);

	}

	private void showSetPasspointsPrompt() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.setTitle("Create your passpoint sequence");
		builder.setMessage("Touch four points to set pass sequence");
		AlertDialog dlg = builder.create();
		dlg.show();
	}

	private void addTouchListener() {
		ImageView image = (ImageView) findViewById(R.id.touch_image);
		image.setOnTouchListener(pointCollector);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}
	
	private void savePasspoints(final List<Point> points) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("storing...");
		
		final AlertDialog dlg = builder.create();
		dlg.show();
		
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				db.storePoints(points);
				Log.d(MainActivity.DEBUGTAG,"Points saved: " + points.size());
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(PASSWORD_SET, true);
				editor.commit();
				
				pointCollector.clear();
				dlg.dismiss();
			}
			
			
		};
		task.execute();
	}
	
	private void verifyPasspoints(final List<Point> points) {
		
	}
	
	@Override
	public void pointsCollected(final List<Point> points) {
		
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);
		
		if(!passpointsSet) {
			Log.d(MainActivity.DEBUGTAG, "Saving passpoints...");
			savePasspoints(points);
		}
		else {
			Log.d(MainActivity.DEBUGTAG, "Verifying passpoints...");
			verifyPasspoints(points);
		}
		
		
	}
}
