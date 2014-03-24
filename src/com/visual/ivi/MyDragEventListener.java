package com.visual.ivi;

import com.example.ivi.R;
import com.model.ivi.Contact;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyDragEventListener implements View.OnDragListener {

	@Override
	public boolean onDrag(View v, DragEvent event) {
		final int action = event.getAction();
		Contact c = (Contact) event.getLocalState();
		switch (action) {
		case DragEvent.ACTION_DRAG_STARTED:
			
			if(v.getId() == R.id.circle_imageview)
			{

			}
			Log.e("ini", c._username);
			Log.e("ini-x", String.valueOf(event.getX()));
			Log.e("ini-y", String.valueOf(event.getY()));
			
			return true;
		case DragEvent.ACTION_DROP:
			Log.e("drop", c._username);
			if(v.getId() == R.id.circle_imageview)
			{
			RelativeLayout parent = (RelativeLayout) v.getParent();
			Log.e("drop-x", String.valueOf(event.getX()));
			Log.e("drop-y", String.valueOf(event.getY()));
			parent.removeViewAt(2);
			}else if(v.getId() == R.id.contact_content){
				FrameLayout parent = (FrameLayout) v.getParent();
				Log.e("drop-x", String.valueOf(event.getX()));
				Log.e("drop-y", String.valueOf(event.getY()));
				((ViewGroup) parent.getChildAt(0)).removeViewAt(2);
			}else if(v.getId() == R.id.contact_content_overlay)
			{
				Log.e("drop-x", String.valueOf(event.getX()));
				Log.e("drop-y", String.valueOf(event.getY()));
				Log.e("CAIU", "CAIU NO OVERLAY");
			}else if(v.getId() == R.id.list_contacts)
			{
				Log.e("drop-x", String.valueOf(event.getX()));
				Log.e("drop-y", String.valueOf(event.getY()));
				Log.e("CAIU", "CAIU NA LIST");
			}else if(v.getId() == R.id.gridView1)
			{
				Log.e("drop-x", String.valueOf(event.getX()));
				Log.e("drop-y", String.valueOf(event.getY()));
				Log.e("CAIU", "CAIU NA GRID");
			}
			return true;
		}
		return false;
	}
}