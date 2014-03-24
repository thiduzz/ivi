package com.main.ivi;

import java.util.ArrayList;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;
import com.model.ivi.OptEmail;
import com.visual.ivi.ContactListAdapter;
import com.visual.ivi.MyDragEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;

public class ContactsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		Contact c = Prefs.getMyUserPref(getApplicationContext());
		Contact c1 = new Contact(11,"thi@thi.com", "TIM", "99913738", "FB", "LKDIN", "G+", "SK", "TW", "thizaom", null, null, null, null, null, c._image_binary);
		Contact c2 = new Contact(13,"thi1@thi.com", "VIVO", "99913738", "FB", "LKDIN", "G+", "SK", "TW", "thizaom1", c._optemails, null, null, "33524801", null, null);
		Contact c3 = new Contact(14,"thi2@thi.com", "CLARO", "99913738", "FB", "LKDIN", "G+", "SK", "TW", "thizaom2", null, "33524801", "33524801", null, null,  c._image_binary);
		ArrayList<Contact> cList = new ArrayList<Contact>();
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		Prefs.setPendingFriends(getApplicationContext(), cList);
		ArrayList<Contact> oi = Prefs.getPendingFriends(getApplicationContext());
		
		ListView listView = (ListView) findViewById(R.id.list_contacts);
        ContactListAdapter customAdapter = new ContactListAdapter(oi, this);
        listView.setAdapter(customAdapter);
		RelativeLayout content = (RelativeLayout) findViewById(R.id.contact_content);
		MyDragEventListener myDragEventListener = new MyDragEventListener();
		content.setOnDragListener(myDragEventListener);
		LinearLayout overlay = (LinearLayout) findViewById(R.id.contact_content_overlay);
		overlay.setOnDragListener(myDragEventListener);
		
	}

}