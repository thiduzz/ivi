package com.main.ivi;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.example.ivi.R.layout;
import com.example.ivi.R.menu;
import com.model.ivi.Contact;
import com.visual.ivi.MyDragEventListener;
import com.visual.ivi.TransparentPanel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GroupsActivity extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/**
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groups);
		current = getParent();
		Intent i = getIntent();
		Log.i("ENTROU!", "ENTROU!");
		model = (Contact)i.getSerializableExtra("modelo");
		RelativeLayout v = (RelativeLayout) findViewById(R.id.groups_content);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 500);
		v.addView(new TransparentPanel(current), params);
		**/
	}

	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.groups, menu);
		return true;
	}
}
