package com.visual.ivi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.os.SystemClock;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;

public class ContactBall {

	int windowwidth;
	int windowheight;
	ImageView circle;
	Boolean isLongClick = false;
	Contact model;
	Bitmap friendImage;
	Activity current;
	MotionEvent motionEvent = MotionEvent.obtain(
			SystemClock.uptimeMillis(), 
			SystemClock.uptimeMillis() + 100, 
	    MotionEvent.ACTION_DOWN, 
	    0.0f, 
	    0.0f, 
	    0
	);
	
	
	public ContactBall(Activity current, Contact modelo)
	{
		this.current = current;
		this.model = modelo;
		setCircleImage();
		circle.setOnTouchListener(touch);
		circle.dispatchTouchEvent(motionEvent);
	}
	
	public void setCircleImage()
	{
		Bitmap conv_bm;
		if (model._image_binary != null) {
			byte[] bytes = Base64.decode(model._image_binary,
					Base64.DEFAULT);
			friendImage = BitmapFactory.decodeByteArray(bytes, 0,
					bytes.length);
			Bitmap resized = Bitmap.createScaledBitmap(friendImage,
					150, 150, true);
			conv_bm = getRoundedRectBitmap(resized, 150);
		} else {
			friendImage = BitmapFactory.decodeResource(
					current.getResources(),
					R.drawable.default_profile_pic);
			Bitmap resized = Bitmap.createScaledBitmap(friendImage,
					150, 150, true);
			conv_bm = getRoundedRectBitmap(resized, 150);
		}
		circle = new ImageView(current);
		RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		vp.leftMargin = (int) Prefs.getMyCoordinates(current).x - 75;
		vp.topMargin = (int) Prefs.getMyCoordinates(current).y - 250;
		circle.setLayoutParams(vp);
		circle.setId(R.id.circle_imageview);
		circle.setImageBitmap(conv_bm);
		circle.setOnDragListener(new MyDragEventListener());
		RelativeLayout content = (RelativeLayout) this.current.findViewById(R.id.contact_content);
		content.addView(circle);
	}
	
	public Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
		Bitmap result = null;
		try {
			result = Bitmap.createBitmap(150, 150,
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(result);
			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 150, 150);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(75, 75, 75, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
		} catch (OutOfMemoryError o) {
		}
		return result;
	}

	public void eliminateBall() {
		RelativeLayout content = (RelativeLayout) this.current.findViewById(R.id.contact_content);
		content.removeView(circle);
	}
	
	OnTouchListener touch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: {
				Prefs.setMyCoordinates(current, event.getRawX() , event.getRawY());
				if(v.getId() == R.id.circle_imageview)
				{
					View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
					//DragShadowBuilder dsb = new MyDragShadowBuilder(v);
					if(model != null)
					{
						LinearLayout overlay = (LinearLayout) current
								.findViewById(R.id.contact_content_overlay);
						overlay.setVisibility(LinearLayout.VISIBLE);
						v.startDrag(null, dsb, model, 0);
					}
				}
				return false;
			}
			case MotionEvent.ACTION_MOVE:{
	            return false;
	            
			}
			case MotionEvent.ACTION_UP:{

				LinearLayout overlay = (LinearLayout) current
						.findViewById(R.id.contact_content_overlay);
				overlay.setVisibility(LinearLayout.GONE);
	            return false;
	            
			}
		}
            return false;
		}
	};
	
}
