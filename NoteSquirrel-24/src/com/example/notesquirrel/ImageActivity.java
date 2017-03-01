// java pattern: Observer
// esta clase es el "Observer" del patr�n

package com.example.notesquirrel;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class ImageActivity extends Activity implements PointCollecterListener{

	private PointCollector pointCollector = new PointCollector();
	private Database db = new Database(this);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		addTouchListener();
		
		showPrompt();
		pointCollector.setListener(this);  // registra el controller: PointCollector.java
	
	}
	
	private void showPrompt(){
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.setTitle("Create your passpoint sequence");
		builder.setMessage("Touch 4 point to set de passpoint");
		
		AlertDialog dlg = builder.create();
		dlg.show();
		
	}
	
	
	private void addTouchListener(){
		ImageView image = (ImageView) findViewById(R.id.touch_image);
		
		image.setOnTouchListener(pointCollector);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

	/*
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
	*/

	@Override
	public void PointsCollected(final List<Point> points) {
		Log.d(MainActivity.DEBUGTAG,"Collected points: "+points.size());
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.storing_data);
		
		final AlertDialog dlg = builder.create();
		dlg.show();
		
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
				db.storePoint(points);
				Log.d(MainActivity.DEBUGTAG,"Points saved");
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				dlg.dismiss();
			}
		};

		task.execute();
	
		List<Point>list = db.getPoints();
		
		for (Point point:list){
			
			Log.d(MainActivity.DEBUGTAG,String.format("Got Point: %d, %d",point.x,point.y));
			
			
			
			
		}
		
		
	}
}
