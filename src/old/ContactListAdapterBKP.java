package old;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;
import com.model.ivi.OptEmail;
import com.visual.ivi.ContactInfoItemControl;
import com.visual.ivi.MyDragEventListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.SystemClock;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ContactListAdapterBKP extends BaseAdapter {

	private static final String TAG = ContactListAdapterBKP.class.getSimpleName();
	ArrayList<Contact> listArray;
	Bitmap friendImage;
	Activity current;
	int windowwidth;
	int windowheight;
	ImageView circle;
	Boolean isLongClick = false;
	Contact model;
	
	MotionEvent motionEvent = MotionEvent.obtain(
			SystemClock.uptimeMillis(), 
			SystemClock.uptimeMillis() + 100, 
	    MotionEvent.ACTION_DOWN, 
	    0.0f, 
	    0.0f, 
	    0
	);

	public ContactListAdapterBKP(ArrayList<Contact> l, Activity ct) {
		listArray = l;
		this.current = ct;
	}

	@Override
	public int getCount() {
		return listArray.size(); // total number of elements in the list
	}

	@Override
	public Object getItem(int i) {
		return listArray.get(i); // single item in the list
	}

	@Override
	public long getItemId(int i) {
		return i; // index number
	}

	@Override
	public View getView(int index, View view, final ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.pendingcontacts_item, parent,
					false);
		}

		final Contact dataModel = listArray.get(index);
		TextView textView = (TextView) view.findViewById(R.id.contactName);
		textView.setText(dataModel._username);

		if (dataModel._image_binary != null) {
			byte[] bytes = Base64.decode(dataModel._image_binary,
					Base64.DEFAULT);
			friendImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			ImageView img = (ImageView) view.findViewById(R.id.contactImage);
			img.setImageBitmap(friendImage);
		}

		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ImageView v = (ImageView) view.findViewById(R.id.contactImage);
				final Drawable pic = v.getDrawable();
				final Dialog dialog = new Dialog(current);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.contactinfo_dialog);
				ImageView img = (ImageView) dialog
						.findViewById(R.id.contactProfilePic);
				img.setImageDrawable(pic);
				TextView name = (TextView) dialog
						.findViewById(R.id.contactUserName);
				name.setText(dataModel.get_username());
				// Phones
				ArrayList<BasicNameValuePair> phones = new ArrayList<BasicNameValuePair>();
				if (dataModel._cellphone != null
						&& (dataModel._cellphone_st == "pub" || dataModel._cellphone_st == null)) {
					phones.add(new BasicNameValuePair("cellphone",
							dataModel._cellphone));
				}
				if (dataModel._homephone != null
						&& (dataModel._homephone_st == "pub" || dataModel._homephone_st == null)) {
					phones.add(new BasicNameValuePair("homephone",
							dataModel._homephone));

				}
				if (dataModel._altphone != null
						&& (dataModel._altphone_st == "pub" || dataModel._altphone_st == null)) {
					phones.add(new BasicNameValuePair("altphone",
							dataModel._altphone));

				}
				if (dataModel._comphone != null
						&& (dataModel._comphone_st == "pub" || dataModel._comphone_st == null)) {
					phones.add(new BasicNameValuePair("comphone",
							dataModel._comphone));

				}
				RelativeLayout phoneContainer = (RelativeLayout) dialog
						.findViewById(R.id.list_phones);
				RelativeLayout emailsContainer = (RelativeLayout) dialog
						.findViewById(R.id.list_emails);
				RelativeLayout snsContainer = (RelativeLayout) dialog
						.findViewById(R.id.list_sns);
				if (phones.size() > 0) {
					int cont = 1;
					for (BasicNameValuePair t : phones) {

						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						params.addRule(RelativeLayout.BELOW, cont - 1);
						ContactInfoItemControl g = new ContactInfoItemControl(
								current, t);
						g.setId(cont);
						phoneContainer.addView(g, params);
						cont++;
					}
				}
				ArrayList<OptEmail> oi;

				RelativeLayout emailSeparador = (RelativeLayout) dialog
						.findViewById(R.id.separadorEmail);
				if (dataModel._optemails != null) {
					oi = dataModel._optemails;
					oi.add(new OptEmail(dataModel.get_email(), "pub"));
				} else {
					oi = new ArrayList<OptEmail>();
					oi.add(new OptEmail(dataModel.get_email(), "pub"));
				}
				int cont = 1;
				for (OptEmail o : oi) {
					if (o.get_OptEmailStats().equals("pub")) {
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						params.addRule(RelativeLayout.BELOW, cont - 1);
						ContactInfoItemControl g = new ContactInfoItemControl(
								current, o);
						g.setId(cont);
						emailsContainer.addView(g, params);
						cont++;
					}
				}
				if (emailsContainer.getChildCount() <= 0) {
					emailSeparador.setVisibility(RelativeLayout.INVISIBLE);
				} else {
					emailSeparador.setVisibility(RelativeLayout.VISIBLE);
				}
				RelativeLayout snsSeparador = (RelativeLayout) dialog
						.findViewById(R.id.separadorSn);

				if (dataModel._sn_facebook != null
						|| dataModel._sn_linkedin != null
						|| dataModel._sn_twitter != null
						|| dataModel._sn_googleplus != null
						|| dataModel._sn_skype != null) {
					ArrayList<String> listSnsAvailable = new ArrayList<String>();
					snsSeparador.setVisibility(RelativeLayout.VISIBLE);
					if (dataModel._sn_facebook != null) {
						listSnsAvailable.add("Facebook");
					}
					if (dataModel._sn_linkedin != null) {
						listSnsAvailable.add("LinkedIn");
					}
					if (dataModel._sn_twitter != null) {
						listSnsAvailable.add("Twitter");
					}
					if (dataModel._sn_googleplus != null) {
						listSnsAvailable.add("Google+");
					}
					if (dataModel._sn_skype != null) {
						listSnsAvailable.add("Skype");
					}
					if (listSnsAvailable.size() > 0) {
						int cont2 = 1;
						for (String t : listSnsAvailable) {

							RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
									LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT);
							params.addRule(RelativeLayout.BELOW, cont2 - 1);
							ContactInfoItemControl g = new ContactInfoItemControl(
									current, t);
							g.setId(cont2);
							snsContainer.addView(g, params);
							cont2++;
						}
					}
				} else {
					snsSeparador.setVisibility(RelativeLayout.INVISIBLE);
				}
				ImageView close = (ImageView) dialog
						.findViewById(R.id.closeDialog);
				close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				long eventDuration = event.getEventTime() - event.getDownTime();
				final int action = event.getAction();
				RelativeLayout content = (RelativeLayout) current
						.findViewById(R.id.contact_content);
				switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN: {
					Resources r = current.getResources();
					Prefs.setMyCoordinates(current, event.getRawX() , event.getRawY());
					model = dataModel;
					if(content.getChildCount()>1 && eventDuration > 1000)
					{
						content.getChildAt(1).dispatchTouchEvent(motionEvent);
					}
					return false;
				}
				case MotionEvent.ACTION_MOVE: {
					model = dataModel;
					if(content.getChildCount()>1 && eventDuration > 1000)
					{
						content.getChildAt(1).dispatchTouchEvent(motionEvent);
					}
					return false;
				}
			}
	            return false;
			}
		});
		
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				isLongClick = true;
				setCircleImage(dataModel);
				circle.setOnTouchListener(touch2);
				model = dataModel;
				RelativeLayout content = (RelativeLayout) current
						.findViewById(R.id.contact_content);
				//content.setBackgroundColor(current.getResources().getColor(R.color.semi_transparente));
				content.addView(circle);
				return true;
			}
		});
		return view;
	}

	OnTouchListener touch2 = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: {
				Prefs.setMyCoordinates(current, event.getRawX() , event.getRawY());
				if(v.getId() == R.id.circle_imageview && isLongClick)
				{
					View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
					//DragShadowBuilder dsb = new MyDragShadowBuilder(v);
					if(model != null)
					{
						
						v.startDrag(null, dsb, model, 0);
					}
					isLongClick = false;
				}
				return false;
			}
			case MotionEvent.ACTION_MOVE:{

	            return false;
	            
			}
			case MotionEvent.ACTION_UP:{
				RelativeLayout content = (RelativeLayout) current
						.findViewById(R.id.contact_content);
				
				//content.setBackgroundColor(current.getResources().getColor(R.color.transparente));
	            return false;
	            
			}
		}
            return false;
		}
	};
	
	public void setCircleImage(Contact dataModel)
	{
		Bitmap conv_bm;
		if (dataModel._image_binary != null) {
			byte[] bytes = Base64.decode(dataModel._image_binary,
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
}