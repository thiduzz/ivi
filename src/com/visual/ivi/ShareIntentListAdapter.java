package com.visual.ivi;

import com.example.ivi.R;
import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareIntentListAdapter extends ArrayAdapter {
	Activity context;
	Object[] items;
	boolean[] arrows;
	int layoutId;

	public ShareIntentListAdapter(Activity context, int layoutId, Object[] items) {
		super(context, layoutId, items);
		this.context = context;
		this.items = items;
		this.layoutId = layoutId;
	}

	public View getView(int position, View view, ViewGroup parent) {
		String app = ((ResolveInfo)items[position]).activityInfo.packageName;

		LayoutInflater inflater = context.getLayoutInflater();
		View row = inflater.inflate(layoutId, null);
		TextView label = (TextView) row.findViewById(R.id.text1);
		label.setText(((ResolveInfo) items[position]).activityInfo.applicationInfo
				.loadLabel(context.getPackageManager()).toString());
		ImageView image = (ImageView) row.findViewById(R.id.logo);
		image.setImageDrawable(((ResolveInfo) items[position]).activityInfo.applicationInfo
				.loadIcon(context.getPackageManager()));
		return (row);
	}
}