package com.caveofprogramming.samples.lists;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setUpEmailList();
        
    }

    private void setUpEmailList() {
    	
    	// Retrieve these message from somewhere ....
		List<Message> messages = new ArrayList<Message>();
		
		messages.add(new Message(0, "Bob Smith", "My cat has gone missing.", true));
		messages.add(new Message(1, "Sue Blake", "Special Offer", false));
		messages.add(new Message(2, "Mike Peters", "Read any good books?", false));
		messages.add(new Message(3, "Sarah Rogers", "Is the canteen on fire?", true));

		MessageAdapter adapter = new MessageAdapter(this, messages);
		
		ListView listView = (ListView)findViewById(R.id.email_list);
		
		listView.setAdapter(adapter);
		
		// Handle OnItemClick ....
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
