package com.example.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setListListener();
	}

	private void setListListener() {
		ListView list = (ListView) findViewById(R.id.list);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				Toast.makeText(MainActivity.this, "Posición: " + position + " , Valor: " + adapter.getItemAtPosition(position) , Toast.LENGTH_LONG).show();
				
			}
		}); 
	}
}
