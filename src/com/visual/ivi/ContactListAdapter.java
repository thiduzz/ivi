package com.visual.ivi;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.main.ivi.ContactHeadsService;
import com.main.ivi.GroupsActivity;
import com.main.ivi.LoggedActivity;
import com.model.ivi.Contact;
import com.model.ivi.OptEmail;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.SystemClock;
import android.provider.ContactsContract;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {

	private static final String TAG = ContactListAdapter.class.getSimpleName();
	ArrayList<Contact> listArray;
	ContactBall ball;
	Bitmap friendImage;
	Intent starting;
	Activity current;
	Contact model;
	MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(),
			SystemClock.uptimeMillis() + 100, MotionEvent.ACTION_DOWN, 0.0f,
			0.0f, 0);

	public ContactListAdapter(ArrayList<Contact> l, Activity ct) {
		listArray = l;
		this.current = ct;
		fetchGroupContacts();
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
				if (ball != null) {
					ball.eliminateBall();
					ball = null;
				}
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
				LinearLayout overlay = (LinearLayout) current
						.findViewById(R.id.contact_content_overlay);
				final int action = event.getAction();
				switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN: {
					Resources r = current.getResources();
					Prefs.setMyCoordinates(current, event.getRawX(),
							event.getRawY());
					model = dataModel;
					return false;
				}
				case MotionEvent.ACTION_UP: {
					if (overlay.getVisibility() == LinearLayout.VISIBLE) {
						overlay.setVisibility(LinearLayout.GONE);
					}
					Resources r = current.getResources();
					Prefs.setMyCoordinates(current, event.getRawX(),
							event.getRawY());
					model = dataModel;
					return false;
				}
				}
				return false;
			}
		});

		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				if (dataModel != null) {
					if (starting != null) {
						current.stopService(starting);
						starting = new Intent(current,
								ContactHeadsService.class);
						model = dataModel;
						Prefs.setSelectedUserPref(current, model);
						current.startService(starting);
					} else {
						starting = new Intent(current,
								ContactHeadsService.class);
						model = dataModel;
						Prefs.setSelectedUserPref(current, model);
						current.startService(starting);
					}
				}
				/**
				 * model = dataModel; ball = new ContactBall(current, model);
				 **/
				/**
				 * Intent startNewActivityOpen = new
				 * Intent(current.getApplicationContext(),
				 * GroupsActivity.class);
				 * startNewActivityOpen.putExtra("modelo", model);
				 * current.getApplicationContext
				 * ().startActivity(startNewActivityOpen);
				 **/
				return true;
			}
		});
		return view;
	}

	public void fetchGroupContacts() {
		ArrayList<String> list = new ArrayList<String>();
		final String[] GROUP_PROJECTION = new String[] {
				ContactsContract.Groups._ID, ContactsContract.Groups.TITLE };
		Cursor cursor = current.getContentResolver().query(
				ContactsContract.Groups.CONTENT_URI, GROUP_PROJECTION, null,
				null, ContactsContract.Groups.TITLE);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Groups._ID));

			String gTitle = (cursor.getString(cursor
					.getColumnIndex(ContactsContract.Groups.TITLE)));
			if (gTitle.contains("Group:")) {
				gTitle = gTitle.substring(gTitle.indexOf("Group:") + 6).trim();

			}
			list.add(gTitle);
		}
		Set<String> s = new HashSet<String>(list);
		if (!s.contains("Friends")) {
			createGroup("Friends");
			s.add("Friends");
		}
		if (!s.contains("Coworkers")) {
			createGroup("Coworkers");
			s.add("Coworkers");
		}
		if (!s.contains("Family")) {
			createGroup("Family");
			s.add("Family");
		}
		ArrayList<String> groups = new ArrayList<String>();
		for (String t : s) {
			if ((t.equals("Family") || t.equals("Friends")
					|| t.equals("Coworkers") || t.startsWith("IvIGroup"))) {
				groups.add(t.toString());
			}
		}
		s = null;
		list = null;
		GridView grid = (GridView) current.findViewById(R.id.gridView1);
		grid.setAdapter(new ContactGroupsAdapter(current, groups));
		grid.setOnDragListener(new MyDragEventListener());
		LinearLayout overlay = (LinearLayout) current
				.findViewById(R.id.contact_content_overlay);
		int height = current.getWindowManager().getDefaultDisplay().getHeight();
		LayoutParams p = (LayoutParams) overlay.getLayoutParams();
		p.height = height / 2;
		p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		overlay.setLayoutParams(p);
	}

	void createGroup(String name) {
		ContentResolver cr = current.getContentResolver();
		ContentValues groupValues = new ContentValues();
		groupValues.put(ContactsContract.Groups.TITLE, name);
		groupValues.put(ContactsContract.Groups.GROUP_VISIBLE, 1);
		cr.insert(ContactsContract.Groups.CONTENT_URI, groupValues);
	}

}