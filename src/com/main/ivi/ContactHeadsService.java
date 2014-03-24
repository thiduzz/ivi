package com.main.ivi;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;
import com.visual.ivi.MyDragEventListener;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ContactHeadsService extends Service {

	private WindowManager windowManager;
	private ImageView chatHead;
	WindowManager.LayoutParams params;
	Bitmap friendImage;
	GestureDetector gestureDetector;
	Contact model;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		chatHead = new ImageView(this);
		model = Prefs.getSelectedUserPref(getApplicationContext());
		setCircleImage();
		gestureDetector = new GestureDetector(this,new GestureListener());
		chatHead.setOnTouchListener(new View.OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return gestureDetector.onTouchEvent(event);
				case MotionEvent.ACTION_UP:
					//return true;
					return gestureDetector.onTouchEvent(event);
				case MotionEvent.ACTION_MOVE:
					params.x = initialX
							+ (int) (event.getRawX() - initialTouchX);
					params.y = initialY
							+ (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(chatHead, params);

					return gestureDetector.onTouchEvent(event);
			
				}
				return gestureDetector.onTouchEvent(event);
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null)
			windowManager.removeView(chatHead);
			chatHead = null;
			windowManager = null;
			this.stopSelf();
	}

	public void setCircleImage() {
		Bitmap conv_bm;
		if (model._image_binary != null) {
			byte[] bytes = Base64.decode(model._image_binary, Base64.DEFAULT);
			friendImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			Bitmap resized = Bitmap.createScaledBitmap(friendImage, 150, 150,
					true);
			conv_bm = getRoundedRectBitmap(resized, 150);
		} else {
			friendImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.default_profile_pic);
			Bitmap resized = Bitmap.createScaledBitmap(friendImage, 150, 150,
					true);
			conv_bm = getRoundedRectBitmap(resized, 150);
		}
		//RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(
		//		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//vp.leftMargin = (int) Prefs.getMyCoordinates(this.getApplicationContext()).x - 75;
		//vp.topMargin = (int) Prefs.getMyCoordinates(this.getApplicationContext()).y - 250;
		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = (int) Prefs.getMyCoordinates(this.getApplicationContext()).x - 75;
		params.y = (int) Prefs.getMyCoordinates(this.getApplicationContext()).y - 155;
		chatHead.setImageBitmap(conv_bm);
		windowManager.addView(chatHead, params);
		chatHead.requestFocus();
	}

	public Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
		Bitmap result = null;
		try {
			result = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
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



class GestureListener extends GestureDetector.SimpleOnGestureListener {
	
	@Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }
	
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
    // event when double tap occurs
    @Override
    public boolean onDoubleTap(MotionEvent e) {
    	onDestroy();

        return true;
    }

}
}