package com.visual.ivi;

import java.util.ArrayList;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;
import com.model.ivi.OptEmail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EmailItemControl extends RelativeLayout {
	private ImageView EmailPrivacy;
	private ImageView EmailRemove;
	private TextView EmailAddress;
	private String privacy;
	private int id;

	public EmailItemControl(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.email_item, this, true);
		loadViews();
	}

	public EmailItemControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.email_item, this, true);
		loadViews();
	}

	private void loadViews() {
		EmailPrivacy = (ImageView) findViewById(R.id.imagePrivacy);
		EmailRemove = (ImageView) findViewById(R.id.imageDelete);
		EmailAddress = (TextView) findViewById(R.id.editText3);
		id = 0;
		Drawable image = getContext().getResources().getDrawable(
				R.drawable.delete);
		image.setBounds(0, 0, 50, 50);
		EmailRemove.setImageDrawable(image);
		EmailRemove.setOnClickListener(removeEmailButtonListener);
		EmailPrivacy.setOnClickListener(changePrivacyStatusListener);
		Contact c = (Contact) Prefs.getMyUserPref(super.getContext());
	}

	public void destroy() {

		// CALCULA MUDANCA VISUAL
		ViewGroup v = (ViewGroup) super.getParent();
		int childCount = v.getChildCount();
		int prev = 0;
		for (int i = 0; i < childCount; i++) {
			if (i == 0 || v.getChildAt(i).getId() < this.getId()) {
				prev = v.getChildAt(i).getId();
			} else {
				if (v.getChildAt(i).equals(this)) {
					View temp = v.getChildAt(i);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) temp
							.getLayoutParams();
					params.getRules()[RelativeLayout.BELOW] = 0;
					temp.setLayoutParams(params);
				} else {

					View temp = v.getChildAt(i);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) temp
							.getLayoutParams();
					params.getRules()[RelativeLayout.BELOW] = 0;
					params.addRule(RelativeLayout.BELOW, prev);
					temp.setLayoutParams(params);
					temp.setId(prev + 1);
					prev = temp.getId();

				}
			}

		}
		// CALCULA MUDANCA NO CONTACT
		Contact c = Prefs.getMyUserPref(super.getContext()
				.getApplicationContext());
		ArrayList<OptEmail> arr = c.get_opt_email();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).get_OptEmail().equals(this.getAddress())) {
				c.get_opt_email().remove(i);
				Prefs.setMyUserPref(super.getContext().getApplicationContext(),
						c);
			}
		}
		// ATUALIZA O VISUAL
		v.removeView(this);
	}

	public void changePrivacy() {
		Contact c = (Contact) Prefs.getMyUserPref(super.getContext());
		if (getId() == 1) {
			if (c.get_email_st().equals("pub")) {
				c.set_email_st("pri");
				setAddressPrivacy("pri");
				Prefs.setMyUserPref(super.getContext().getApplicationContext(),c);
			} else if (c.get_email_st().equals("pri")) {
				c.set_email_st("pub");
				setAddressPrivacy("pub");
				Prefs.setMyUserPref(super.getContext().getApplicationContext(),c);
			}
		}else{
			int i = 0;
			for(int o=0;o<c.get_opt_email().size();o++)
			{
				if (c.get_opt_email().get(o).get_OptEmail().equals(getAddress()))
				{
					i = o;
				}
			}
			if (c.get_opt_email().get(i).get_OptEmailStats().equals("pub")) {
				c.get_opt_email().get(i).set_OptEmailStats("pri");
				setAddressPrivacy("pri");
				Prefs.setMyUserPref(super.getContext().getApplicationContext(),c);
			} else 	if (c.get_opt_email().get(i).get_OptEmailStats().equals("pri")) {
				c.get_opt_email().get(i).set_OptEmailStats("pub");
				setAddressPrivacy("pub");
				Prefs.setMyUserPref(super.getContext().getApplicationContext(),	c);
			}
		}
	}



	public String getAddress() {
		return EmailAddress.getText().toString();
	}

	public String getAddressPrivacy() {
		return privacy.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}
	
	public void setAddress(String address) {
		EmailAddress.setText(address);
	}
	
	public void setAddressPrivacy(String pv) {
		if (pv.equals("pub")) {
			Drawable image = getContext().getResources().getDrawable(
					R.drawable.unlocked);
			image.setBounds(0, 0, 50, 50);
			EmailPrivacy.setImageDrawable(image);

		} 
		if (pv.equals("pri")){
			Drawable image = getContext().getResources().getDrawable(
					R.drawable.locked);
			image.setBounds(0, 0, 50, 50);
			EmailPrivacy.setImageDrawable(image);
		}
		privacy = pv;
	}
	
	public void setEmailDeleteVisibility(boolean x) {
		if (x == true) {
			EmailRemove.setVisibility(ImageView.VISIBLE);
		} else {
			EmailRemove.setVisibility(ImageView.INVISIBLE);
		}
	}

	public OnClickListener changePrivacyStatusListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			changePrivacy();
		}

	};
	
	public OnClickListener removeEmailButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			destroy();
		}

	};
}
