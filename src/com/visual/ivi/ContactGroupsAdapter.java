package com.visual.ivi;

import java.util.ArrayList;

import com.example.ivi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactGroupsAdapter extends BaseAdapter {
		private Context context;
		private final ArrayList<String> groupValues;
	 
		public ContactGroupsAdapter(Context context, ArrayList<String> groups) {
			this.context = context;
			this.groupValues = groups;
		}
	 
		public View getView(int position, View convertView, ViewGroup parent) {
	 
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View gridView;
	 
			if (convertView == null) {
	 
				gridView = new View(context);
	 
				// get layout from mobile.xml
				gridView = inflater.inflate(R.layout.group_gridview_item, null);
	 
				// set value into textview
				TextView textView = (TextView) gridView
						.findViewById(R.id.grid_item_label);
				if(groupValues.get(position).startsWith("IvIGroup"))
				{
					textView.setText(groupValues.get(position).substring(8));
				}else{
				textView.setText(groupValues.get(position));
				}
				// set image based on selected text
				ImageView imageView = (ImageView) gridView
						.findViewById(R.id.grid_item_image);
	 
				String group = groupValues.get(position);
	 
				if (group.equals("Family")) {
					imageView.setImageResource(R.drawable.hover_gridview_item_family);
				} else if (group.equals("Friends")) {
					imageView.setImageResource(R.drawable.hover_gridview_item_friends);
				} else if (group.equals("Coworkers")) {
					imageView.setImageResource(R.drawable.hover_gridview_item_work);
				} else {
					imageView.setImageResource(R.drawable.hover_gridview_item_default);
				}
	 
			} else {
				gridView = (View) convertView;
			}
	 
			return gridView;
		}
	 
		@Override
		public int getCount() {
			return groupValues.size();
		}
	 
		@Override
		public Object getItem(int position) {
			return null;
		}
	 
		@Override
		public long getItemId(int position) {
			return 0;
		}
	 
	}
	
	

