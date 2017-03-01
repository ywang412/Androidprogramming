package com.example.customlists_36;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class MessageAdapter extends BaseAdapter implements ListAdapter {

	private List<Message> messages;
	private Context context;

	public MessageAdapter(Context context, List<Message> messages) {
		super();
		this.messages = messages;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return messages.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_message_item, null);

		Message message = messages.get(position);

		String title = message.getTitle();
		String sender = message.getSender();

		TextView titleView = (TextView) view
				.findViewById(R.id.list_message_title);
		TextView senderView = (TextView) view
				.findViewById(R.id.list_message_sender);
		ImageView iconView = (ImageView) view
				.findViewById(R.id.list_message_icon);

		boolean isRead = message.isRead();



		int iconId = R.drawable.btn_radio_on_focused_holo_dark;

		if (isRead)
			iconId = R.drawable.btn_radio_on_disabled_focused_holo_light;
		else
			iconId = R.drawable.btn_radio_on_focused_holo_dark;
		
		
		Drawable icon = context.getResources().getDrawable(iconId);
		iconView.setImageDrawable(icon);
		
		

		titleView.setText(title);
		senderView.setText(sender);

		// devuelve la vista inflated
		return view;
	}

}
