package com.visual.ivi;

/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.ivi.LinkedinTask;
import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.facebook.*;
import com.facebook.model.GraphUser;
import com.model.ivi.Contact;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LinkedinUserSettingsFragment extends Fragment {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String PICTURE = "picture";
    private static final String FIELDS = "fields";
    
    private static final String REQUEST_FIELDS = TextUtils.join(",", new String[] {ID, NAME, PICTURE});
    private Contact c;
    private Button loginButton;
    private TextView connectedStateLabel;
    private Drawable userProfilePic;
    private String userProfilePicID;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.user_settings_linkedin, container, false);
        loginButton = (Button) v.findViewById(R.id.linkedin_usersettingsfragment_login_button);
        connectedStateLabel = (TextView) v.findViewById(R.id.linkedin_usersettingsfragment_profile_name);
	    c = Prefs.getMyUserPref(getActivity().getApplicationContext());
		loginButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v)
		{

			Button ClickedButton = (Button) v;
			if(((Button) v).getText() == getResources().getString(R.string.com_facebook_loginview_log_out_button) )
			{
				logoutSession();
			}else{
				//Login
				Intent startNewActivityOpen = new Intent(getActivity(), LinkedinTask.class);
				getActivity().startActivityForResult(startNewActivityOpen, 1253);
			}
		}
		});
        if (v.getBackground() == null) {
            v.setBackgroundColor(getResources().getColor(R.color.linkedin_gray));
        } else {
            v.getBackground().setDither(true);
        }
		String id = Prefs.getMyUserPref(getActivity().getApplicationContext()).get_sn_linkedin();
		String name = Prefs.getMyStringPref(getActivity().getApplicationContext(), "LinkedinFirstName");
		String lname = Prefs.getMyStringPref(getActivity().getApplicationContext(), "LinkedinLastName");
        retrievePreviousAndSetSession(id, name, lname);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	String result = data.getExtras().getString("resultLinkedin");
    	if(result.equals("CANCELED"))
    	{
    		
    	}
    	if(result.equals("FAILED"))
    	{
    		Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.LinkedinFailedMessage) , Toast.LENGTH_LONG);
    	}
    	if(result.equals("GOOD"))
    	{
    		String id = Prefs.getMyUserPref(getActivity().getApplicationContext()).get_sn_linkedin();
    		String name = Prefs.getMyStringPref(getActivity().getApplicationContext(), "LinkedinFirstName");
    		String lname = Prefs.getMyStringPref(getActivity().getApplicationContext(), "LinkedinLastName");
    		retrievePreviousAndSetSession(id, name, lname);
    	}
}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    /**
     * @throws com.facebook.FacebookException if errors occur during the loading of user information
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    private void logoutSession() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	String title = getActivity().getResources().getString(R.string.linkedin_loginview_logged_in_as) + Prefs.getMyStringPref(getActivity().getApplicationContext(), "LinkedinFirstName") + " " + Prefs.getMyStringPref(getActivity().getApplicationContext(), "LinkedinLastName");  
    	builder
    	   .setTitle(title)
    	   .setCancelable(false)
    	   .setPositiveButton(getActivity().getResources().getString(R.string.com_facebook_loginview_log_out_action), new DialogInterface.OnClickListener() {
    	       public void onClick(DialogInterface dialog, int id) {
    	   		c = Prefs.getMyUserPref(getActivity().getApplicationContext());
    			c.set_sn_linkedin(null);
    			Prefs.setMyUserPref(getActivity().getApplicationContext(), c);
    	    	Prefs.setLinkedinUserPref(getActivity().getApplicationContext(), null);
    			Prefs.setMyStringPref(getActivity().getApplicationContext(), null, "LinkedinProfPicUrl");
    			Prefs.setMyStringPref(getActivity().getApplicationContext(), null, "LinkedinFirstName");
    			Prefs.setMyStringPref(getActivity().getApplicationContext(), null, "LinkedinLastName");
    			connectedStateLabel.setText(getResources().getString(R.string.linkedin_usersettingsfragment_not_logged_in));
    			connectedStateLabel.setCompoundDrawables(null, null, null, null);
    			loginButton.setText(getResources().getString(R.string.com_facebook_loginview_log_in_button));
    	       }
    	   })
    	   .setNegativeButton(getActivity().getResources().getString(R.string.com_facebook_loginview_cancel_action), new DialogInterface.OnClickListener() {
    	       public void onClick(DialogInterface dialog, int id) {
    	            dialog.cancel();
    	       }
    	   });
    	AlertDialog alert = builder.show();
    }
    
    private void retrievePreviousAndSetSession(String user_id, String user_name, String user_last) {
    	try {
    		if(Prefs.getLinkedinUserPref(getActivity().getApplicationContext()) != null && user_name != null && user_last != null)
    		{
    		Bitmap bm;
    		InputStream fos = getActivity().getApplicationContext().openFileInput("linkedin" + user_id + ".png");
    		if(fos != null)
    		{
    		Bitmap b = BitmapFactory.decodeStream(fos);
    		Drawable d = new BitmapDrawable(b);
			d.setBounds(0,0,100,100);
			connectedStateLabel.setCompoundDrawables(null, d, null, null);
    		}
			connectedStateLabel.setVisibility(TextView.VISIBLE);
    		connectedStateLabel.setText(user_name+" "+user_last);
    		loginButton.setText(getResources().getString(R.string.com_facebook_loginview_log_out_button));
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
