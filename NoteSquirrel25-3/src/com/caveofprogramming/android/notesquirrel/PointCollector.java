package com.caveofprogramming.android.notesquirrel;

/*
 * Thanks to Nathan Hazout for supplying me with this reconstructed project from lecture 24,
 * which I sadly neglected to save as I went along! That in turn gave us all a bit of a headache,
 * Since the original version of lecture 25 was pretty confusing, and I wasn't able to redo it
 * without going through the first 24 lectures myself, which I couldn't face doing.
 * A big thanks to Nathan, and apologies to everyone who put up with the original lecture 25
 * videos. - John Purcell, October 11 2013
 */

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class PointCollector implements OnTouchListener {
	
	public static final int NUM_POINTS = 4;
	
	private List<Point> points = new ArrayList<Point>();
	private PointCollecterListener listener;
	
	public boolean onTouch(View v, MotionEvent event) {
		
		int x = Math.round(event.getX());
		int y = Math.round(event.getY());
		
		String message = String.format("Coordinates: (%d,  %d)",x, y);
		Log.d(MainActivity.DEBUGTAG, message);
		
		points.add(new Point (x,y));
		
		if(points.size() == NUM_POINTS){
			if(listener != null){
				listener.pointsCollected(points);
			}
		}
		
		return false;
	}

	public void setListener(PointCollecterListener listener) {
		this.listener = listener;
	}
	
	public void clear() {
		points.clear();
	}
}
