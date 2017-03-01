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

import android.graphics.Point;

public interface PointCollecterListener {
	public void pointsCollected(List<Point> points);
}
