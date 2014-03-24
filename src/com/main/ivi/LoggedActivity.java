package com.main.ivi;

import old.AnimatedTabHostListener;

import com.example.ivi.R;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class LoggedActivity extends ActivityGroup   {

	@Override
     public void onCreate(Bundle savedInstanceState) {         

        super.onCreate(savedInstanceState);    
        setContentView(R.layout.activity_logged);
        TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup(getLocalActivityManager());
        
        TabSpec profspec = tabHost.newTabSpec("Profile");
        // setting Title and Icon for the Tab
        profspec.setIndicator("", getResources().getDrawable(R.drawable.icon_profile_tab));
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profspec.setContent(profileIntent);
 
   
        TabSpec contspec = tabHost.newTabSpec("Contacts");
        contspec.setIndicator("", getResources().getDrawable(R.drawable.icon_contact_tab));
        Intent contactsIntent = new Intent(this, ContactsActivity.class);
        contspec.setContent(contactsIntent);
 
       
        TabSpec notifspec = tabHost.newTabSpec("Notification");
        notifspec.setIndicator("", getResources().getDrawable(R.drawable.icon_notification_tab));
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        notifspec.setContent(notificationIntent);
        tabHost.addTab(profspec);
        tabHost.addTab(contspec);
        tabHost.addTab(notifspec);
        setTabColor(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
        	  @Override
        	  public void onTabChanged(String tabId) {
        		  TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
        	        setTabColor(tabHost);
        	  }
        	});
        tabHost.getTabWidget().getChildAt(0).setPadding(0, 30, 0, 10);
        tabHost.getTabWidget().getChildAt(1).setPadding(0, 30, 0, 10);
        tabHost.getTabWidget().getChildAt(2).setPadding(0, 30, 0, 10);
        tabHost.setOnTabChangedListener(new AnimatedTabHostListener(getApplicationContext(), tabHost));

	 }
	
	 public static void setTabColor(TabHost tabhost) {
		    for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
		    {
		        tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF")); //unselected
		    }
		    tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#666666")); // selected
		}

}
