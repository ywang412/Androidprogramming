// java pattern: Observer
// esta clase es el "Sujeto" del patrón

package com.example.notesquirrel;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PointCollector implements OnTouchListener{
	//@Override 
	
	
	private PointCollecterListener listener;
	private List<Point> points = new ArrayList<Point>();
	
	
	public boolean onTouch(View v, MotionEvent event) {

		
		int x = Math.round(event.getX());
		int y = Math.round(event.getY());
		
		String message = String.format("Coordenadas: (%d,%d)",x,y);
		Log.d(MainActivity.DEBUGTAG, message);
		
		points.add(new Point(x,y));
		
		if (points.size() == 4 ){
			if (listener!=null){
				listener.PointsCollected(points);  // notifica a la vista: ImageActivity.java
				points.clear();
			}
		}
		
		return false;
	}


	public void setListener(PointCollecterListener listener) {
		this.listener = listener;
	}
}
