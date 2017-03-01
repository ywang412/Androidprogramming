package com.example.customlists_36; 


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setUpEmailList();
		
	}
	
	private void setUpEmailList() {
		
		List<Message> messages = new ArrayList<Message>();
		
		messages.add( new Message(1, "Juan","Mi gato se escapó", true));
		messages.add( new Message(2, "Juan","Mi gato volvió",false));
		messages.add( new Message(3, "Juli","Toca violín",false));
		messages.add( new Message(4, "Sofi","Toca piano",true));
		
		MessageAdapter adapter = new MessageAdapter(this,messages);

		ListView listView = (ListView)findViewById(R.id.email_list); 
		
		listView.setAdapter(adapter);
		
		// Handle OnItemClick ... (próximos projects)
		
		
	
}
	
}
