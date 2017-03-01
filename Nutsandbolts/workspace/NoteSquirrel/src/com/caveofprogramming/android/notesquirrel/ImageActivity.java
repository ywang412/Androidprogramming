package com.caveofprogramming.android.notesquirrel;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends Activity implements PointCollecterListener {

	public static final String RESET_PASSPOINTS = "ResetPasspoints";
	public static final String RESET_IMAGE = "ResetImage";
	
	private boolean doPasspointReset = false;

	private final static String PASSWORD_SET = "PasswordSet";
	private final static String CURRENT_IMAGE = "CurrentImage";

	// The point touched must be within this many pixels of the point
	// saved in order to qualify as correct.
	private final static int POINT_CLOSENESS = 40;

	private PointCollector pointCollector = new PointCollector();
	private Database db = new Database(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		addTouchListener();

		pointCollector.setListener(this);

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);

		String newImage = null;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			
			// See if a flag was passed to this intent to indicate 
			// that the user wants to change the passpoint sequence.
			doPasspointReset = extras.getBoolean(RESET_PASSPOINTS);
			
			// See if an image path was passed to this intent, 
			// indicating that the user wants to change the image.
			newImage = extras.getString(RESET_IMAGE);
		}
		
		
		if(newImage == null) {
			// If the user didn't specify a new image via the reset image
			// option, check to see if we've got an image stored which
			// we should use.
			newImage = prefs.getString(CURRENT_IMAGE, null);
		}
		else {
			// If the user has specified a new image to be used,
			// save it in the preferences.
			Editor editor = prefs.edit();
			editor.putString(CURRENT_IMAGE, newImage);
			editor.commit();
		}

		// Set the current image. If newImage is null, this will
		// display the default.
		setImage(newImage);

		if (!passpointsSet || doPasspointReset) {
			showSetPasspointsPrompt();
		}
	}

	private void setImage(String path) {
		
		ImageView imageView = (ImageView) findViewById(R.id.touch_image);

		if (path == null) {
			Drawable image = getResources().getDrawable(R.drawable.prettyface);
			imageView.setImageDrawable(image);
		} else {
			imageView.setImageURI(Uri.parse(path));
		}

	}

	private void showSetPasspointsPrompt() {
		AlertDialog.Builder builder = new Builder(this);

		builder.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setTitle(R.string.create_passpoints);
		builder.setMessage(R.string.create_passpoints_text);

		AlertDialog dlg = builder.create();

		dlg.show();
	}

	private void showLoginPrompt() {
		AlertDialog.Builder builder = new Builder(this);

		builder.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setTitle(R.string.enter_passpoints_title);
		builder.setMessage(R.string.enter_passpoints_text);

		AlertDialog dlg = builder.create();

		dlg.show();
	}

	private void addTouchListener() {
		ImageView image = (ImageView) findViewById(R.id.touch_image);
		image.setOnTouchListener(pointCollector);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_image, menu);
		return true;
	}

	private void savePasspoints(final List<Point> points) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.saving_passpoints);

		final AlertDialog dlg = builder.create();
		dlg.show();

		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				db.storePoints(points);

				List<Point> test = db.getPoints();
				Log.d(MainActivity.DEBUGTAG, "Saved Points: " + test.size());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(PASSWORD_SET, true);
				editor.commit();

				dlg.dismiss();
				pointCollector.clear();
			}
		};

		task.execute();
	}

	private void verifyPasspoints(final List<Point> touchedPoints) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.checking_passpoints);

		final AlertDialog dlg = builder.create();
		dlg.show();

		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
			protected Boolean doInBackground(Void... params) {

				List<Point> savedPoints = db.getPoints();

				Log.d(MainActivity.DEBUGTAG,
						"Saved points: " + savedPoints.size());

				if (savedPoints.size() != PointCollector.NUM_POINTS
						|| touchedPoints.size() != PointCollector.NUM_POINTS) {
					return false;
				}

				for (int i = 0; i < touchedPoints.size(); i++) {
					Point saved = savedPoints.get(i);
					Point touched = touchedPoints.get(i);

					int xDiff = saved.x - touched.x;
					int yDiff = saved.y - touched.y;

					int distSquared = xDiff * xDiff + yDiff * yDiff;

					Log.d(MainActivity.DEBUGTAG, "Distance: " + distSquared);

					if (distSquared > POINT_CLOSENESS * POINT_CLOSENESS) {
						return false;
					}
				}

				return true;
			}

			@Override
			protected void onPostExecute(Boolean pass) {
				dlg.dismiss();
				pointCollector.clear();

				if (pass) {
					Intent i = new Intent(ImageActivity.this,
							MainActivity.class);
					startActivity(i);
				} else {
					Toast.makeText(ImageActivity.this, R.string.access_denied,
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		task.execute();
	}

	public void pointsCollected(final List<Point> points) {

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);

		if (doPasspointReset || !passpointsSet) {
			savePasspoints(points);
			doPasspointReset = false;
			showLoginPrompt();
		} else {
			verifyPasspoints(points);
		}
	}
}
