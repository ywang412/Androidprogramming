package com.example.taking.a.photo;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private File imageFile;
	private static final int PHOTO_TAKEN = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addSnapButtonListener();
	}

	private void addSnapButtonListener() {
		Button snap = (Button) findViewById(R.id.snap);

		snap.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
				File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				imageFile = new File(picturesDirectory, "passpoints_image");
				
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
				startActivityForResult(i, PHOTO_TAKEN);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == PHOTO_TAKEN) {
			Bitmap photo = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			
			if(photo != null) {
				ImageView imageView = (ImageView)findViewById(R.id.view);
				imageView.setImageBitmap(photo);
			}
			else {
				Toast.makeText(this, R.string.unable_to_save_photo_file, Toast.LENGTH_LONG).show();
			}
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
