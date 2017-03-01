package com.example.notesquirrel;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
	
	private static final String POINTS_TABLE="POINTS";
	private static final String COL_ID = "ID";
	private static final String COL_X  = "X" ;
	private static final String COL_Y  = "Y" ;

	public Database(Context context) {
		super(context, "notes.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER NOT NULL)",
				                    POINTS_TABLE,COL_ID,COL_X,COL_Y);
		
		Log.d(MainActivity.DEBUGTAG,sql);		
		
		db.execSQL(sql);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void storePoint(List<Point>points){
		
		try {
			SQLiteDatabase db = getWritableDatabase();
			db.delete(POINTS_TABLE, null, null);
			
			
			int i=0;
			for (Point point:points){
				ContentValues values = new ContentValues();
				
				values.put(COL_ID, i);
				values.put(COL_X, point.x);
				values.put(COL_Y, point.y);
				
				db.insert(POINTS_TABLE, null, values);
				
				
				i++;
			}
			
			db.close();
			Log.d(MainActivity.DEBUGTAG,"Puntos insertados OK y tabla cerrada");
		} catch (Exception e) {
			Log.d(MainActivity.DEBUGTAG,"Error insertando puntos en SQLite");
		}

	}

	
	public List<Point> getPoints(){

		List<Point> points = new ArrayList<Point>();
		
		try {

			
			SQLiteDatabase db = getReadableDatabase();

			String sql = String.format("SELECT %s, %s FROM %s ORDER BY %s",COL_X, COL_Y, POINTS_TABLE, COL_ID);
			Cursor cursor = db.rawQuery(sql, null);
			
			while (cursor.moveToNext()){
				int x = cursor.getInt(0);
				int y = cursor.getInt(1);
				points.add(new Point(x, y));
				
			}
			
			db.close();
			
		} catch (Exception e) {
			Log.d(MainActivity.DEBUGTAG,"Error recuperando puntos desde SQLite");			
		}
		return points;
	}	
	
	
}
