package com.visual.ivi;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactInfoItemControl extends RelativeLayout {

	BasicNameValuePair info;
	OptEmail email;
	Context current;
	String sn;
	private int id;
	
	public ContactInfoItemControl(Context context, BasicNameValuePair p) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.contacts_phone_item, this, true);
		this.current = context;
		this.info = p;
		loadViewPhone();
	}

	public ContactInfoItemControl(Context context, OptEmail o) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.contacts_phone_item, this, true);
		this.current = context;
		this.email = o;
		loadViewEmail();
	}
	
	public ContactInfoItemControl(Context context, String s) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.contacts_phone_item, this, true);
		this.current = context;
		this.sn = s;
		loadViewSn();
	}
	
	public ContactInfoItemControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.contacts_phone_item, this, true);
		loadViewPhone();
		loadViewEmail();
	}
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int i) {
		id = i;
	}
	
	private void loadViewPhone() {
		ImageView pic = (ImageView) findViewById(R.id.imageType);
        if(info.getName() =="cellphone")
        {
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.cellphone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(info.getName() =="homephone"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.homephone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        	
        }else if(info.getName() =="comphone"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.comphone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(info.getName() =="altphone"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.altphone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }
        TextView value = (TextView) findViewById(R.id.contactValueItem);
        value.setText(info.getValue().toString());
		this.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
		        CheckBox check = (CheckBox) view.findViewById(R.id.addCheck);
		        if(check.isChecked())
		        {
		        	check.setChecked(false);
		        }else{
		        	check.setChecked(true);
		        }
			}
		});
		
		
	}
	
	private void loadViewEmail() {
		ImageView pic = (ImageView) findViewById(R.id.imageType);
		pic.setVisibility(ImageView.INVISIBLE);
        TextView value = (TextView) findViewById(R.id.contactValueItem);
        value.setText(email.get_OptEmail());
		this.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
		        CheckBox check = (CheckBox) view.findViewById(R.id.addCheck);
		        if(check.isChecked())
		        {
		        	check.setChecked(false);
		        }else{
		        	check.setChecked(true);
		        }
			}
		});
		
		
	}
	

	private void loadViewSn() {
		ImageView pic = (ImageView) findViewById(R.id.imageType);
        TextView value = (TextView) findViewById(R.id.contactValueItem);
        value.setText(sn.toString());
        if(sn =="Facebook")
        {
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.facebook);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(sn =="LinkedIn"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.linkedin);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        	
        }else if(sn =="Google+"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.gplus2);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(sn =="Twitter"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.twitter);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(sn =="Skype"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.skype);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }
		this.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
		        CheckBox check = (CheckBox) view.findViewById(R.id.addCheck);
		        if(check.isChecked())
		        {
		        	check.setChecked(false);
		        }else{
		        	check.setChecked(true);
		        }
			}
		});
		
		
	}
	
}
