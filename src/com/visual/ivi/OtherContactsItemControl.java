package com.visual.ivi;

import java.util.regex.Pattern;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.TextView.OnEditorActionListener;

public class OtherContactsItemControl extends RelativeLayout {
	private ImageView privacy;
	private ImageView edit;
	private ImageView typeIcon;
	private ViewSwitcher switcher;
	private EditText valueEdit;
	private TextView valueView;
	private String type, priv;
	private int id;
	public InputMethodManager imm;
	public TextView bt;
	public final Pattern PHONE_PATTERN = Pattern.compile("^.{10,11}$");
	/**Validacao URL**/
	//URLUtil.isValidUrl(url)
	
	
	public OtherContactsItemControl(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.othercontacts_item, this, true);
		imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		loadView();
		restoreValues();
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int i) {
		id = i;
	}
	
	public OtherContactsItemControl(Context context, String type, int id, TextView bt) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.othercontacts_item, this, true);
		imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		this.type = type;
		this.id = id;
		this.bt = bt;
		loadView();
		setType();
		restoreValues();
		loadListeners();
	}

	public OtherContactsItemControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.othercontacts_item, this, true);
		imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		loadView();
		restoreValues();
		loadListeners();
	}
	
	private void loadView() {
		switcher = (ViewSwitcher) findViewById(R.id.item_switcher);
		valueEdit = (EditText) findViewById(R.id.editTextSwitch); 
		valueView = (TextView) findViewById(R.id.textViewSwitch);
		privacy = (ImageView) findViewById(R.id.imagePrivacySwitch);
		edit = (ImageView) findViewById(R.id.imageViewEditSwitch);
		typeIcon = (ImageView) findViewById(R.id.imageViewType);
	}

	private void loadListeners()
	{
		privacy.setOnClickListener(changePrivacyStatusListener);
		edit.setOnClickListener(changeEditSwitcherListener);
		valueEdit.setOnEditorActionListener(clickBotaoDone);
		valueEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		      @Override
		      public void onFocusChange(View v, boolean hasFocus) {
		  		if(type.equals("web"))
				{
		    	  if(valueEdit.getText().length() == 0)
		    	  {
		    		  valueEdit.setText("http://");
		    	  }
				}
		  		if(!hasFocus)
		  		{
					imm.hideSoftInputFromWindow(getWindowToken(), 0);
		  		}
		      }
		    });
	
	valueEdit.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {


      }

		@Override
		public void afterTextChanged(Editable arg0) {
	        if(valueEdit.getText().length() == 0)
	        {
				privacy.setVisibility(View.INVISIBLE);
				setValuePrefAccordingToType(null);
				setPrivPrefAccordingToType(null);
	        }
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1,
				int arg2, int arg3) {
		} 

  });
	}
	private void setType() {
		if(type.equals("home"))
		{
			valueEdit.setInputType(InputType.TYPE_CLASS_PHONE);
			valueEdit.setHint(R.string.PhoneNumber);
			Drawable img = super.getContext().getResources()
					.getDrawable(R.drawable.homephone);
			img.setBounds(0, 0, 50, 50);
			typeIcon.setImageDrawable(img);
			
		}else if(type.equals("com"))
		{
			valueEdit.setInputType(InputType.TYPE_CLASS_PHONE);
			valueEdit.setHint(R.string.PhoneNumber);
			Drawable img = super.getContext().getResources()
					.getDrawable(R.drawable.comphone);
			img.setBounds(0, 0, 50, 50);
			typeIcon.setImageDrawable(img);
		}else if(type.equals("alt"))
		{
			valueEdit.setInputType(InputType.TYPE_CLASS_PHONE);
			valueEdit.setHint(R.string.PhoneNumber);
			Drawable img = super.getContext().getResources()
					.getDrawable(R.drawable.altphone);
			img.setBounds(0, 0, 50, 50);
			typeIcon.setImageDrawable(img);
		}else if(type.equals("web"))
		{
			valueEdit.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
			valueEdit.setHint(R.string.Website);			
			Drawable img = super.getContext().getResources()
					.getDrawable(R.drawable.website);
			img.setBounds(0, 0, 50, 50);
			typeIcon.setImageDrawable(img);
		}
	}
	
	public void restoreValues()
	{
		String value = getValuePrefAccordingToType();
		String valuest = getPrivPrefAccordingToType();
		if(value != null)
		{
			valueView.setText(value);
			switcher.showNext();
			Drawable img = super.getContext().getResources()
					.getDrawable(R.drawable.edit_icon);
			img.setBounds(0, 0, 50, 50);
			edit.setImageDrawable(img);
			if(valuest != null)
			{
			privacy.setVisibility(View.VISIBLE);
			setPrivacy(getPrivPrefAccordingToType());
			}else{
				privacy.setVisibility(View.VISIBLE);
				setPrivacy("pub");
			}
		}else{
			Drawable img = super.getContext().getResources()
					.getDrawable(R.drawable.accept);
			img.setBounds(0, 0, 50, 50);
			edit.setImageDrawable(img);
			privacy.setVisibility(View.INVISIBLE);
		}
	}
	
	public void SwitcherSwitch()
	{
		if (switcher.getDisplayedChild() == 0) {
			//Edit -> Text
			if (valueEdit.getText().toString().length() == 0 || validation(valueEdit.getText().toString())) {
				valueEdit.setError(null);
				imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
				if (valueEdit.getText().toString().length() != 0 && valueEdit.getText().toString() != "http://")
				{
				valueView.setText(valueEdit.getText());
				setValuePrefAccordingToType(valueEdit.getText().toString());
				Drawable img = super.getResources()
						.getDrawable(R.drawable.edit_icon);
				img.setBounds(0, 0, 50, 50);
				edit.setImageDrawable(img);
				switcher.showNext();
				}
				if(getPrivPrefAccordingToType() != null)
				{
				setPrivacy(getPrivPrefAccordingToType());
				privacy.setVisibility(View.VISIBLE);
				}else{
					if (valueEdit.getText().toString().length() != 0)
					{
					setPrivPrefAccordingToType("pub");
					setPrivacy(getPrivPrefAccordingToType());
					privacy.setVisibility(View.VISIBLE);
					}
				}
				setOtherContactsIcon();
				
			} else {
				setError();
				setOtherContactsIcon();
			}
		} else {
			//Text -> Edit
			valueEdit.setText(valueView.getText().toString());
			Drawable img = super.getResources()
					.getDrawable(R.drawable.accept);
			img.setBounds(0, 0, 50, 50);
			edit.setImageDrawable(img);
			switcher.showPrevious();
			valueEdit.setFocusableInTouchMode(true);
			edit.setImageDrawable(img);
			privacy.setVisibility(View.INVISIBLE);
			valueEdit.requestFocus();
			imm.showSoftInput(valueEdit, 0);
			setOtherContactsIcon();
		}
	}
	
	public boolean validation(String value)
	{
		if(type.equals("home") || type.equals("com") || type.equals("alt") )
		{
			return validatePhone(value);
		}else if(type.equals("web"))
		{
			return URLUtil.isValidUrl(value);
		}
		return false;
	}
	
	public void setError()
	{
		if(type.equals("home") || type.equals("com") || type.equals("alt") )
		{
			valueEdit.setError(super.getResources().getString(
					R.string.PhoneErrorMessage1));
		}else if(type.equals("web"))
		{
			valueEdit.setError(super.getResources().getString(
					R.string.WebsiteErrorMessage1));
		}
	}
	
	public void setOtherContactsIcon()
	{
		Contact c = Prefs.getMyUserPref(super.getContext());
		Drawable imageDown = super.getContext().getResources()
				.getDrawable(R.drawable.close);
		imageDown.setBounds(0, 0, 22, 30);
		if (c._homephone == null && c._comphone == null && c._altphone == null) {
			Drawable image = super.getContext().getResources()
					.getDrawable(R.drawable.othercontacts_2);
			image.setBounds(0, 0, 50, 50);
			bt.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = super.getContext().getResources()
					.getDrawable(R.drawable.othercontacts);
			image.setBounds(0, 0, 50, 50);
			bt.setCompoundDrawables(image, null, imageDown, null);
		}
	}
	
	public OnEditorActionListener clickBotaoDone = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				if (v.getId() == R.id.editTextSwitch) {
					imm.hideSoftInputFromWindow(valueEdit.getWindowToken(), 0);
					if(valueEdit.getText().length() != 0)
					{
					edit.performClick();
					}
					return true;
				}
			}
			return false;
		}
	};
	

	
	public OnClickListener changeEditSwitcherListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			SwitcherSwitch();
		}
	};
	public OnClickListener changePrivacyStatusListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			changePrivacy();
		}
	};
	
	public void setPrivacy(String pv) {
		if (pv.equals("pub")) {
			Drawable image = getContext().getResources().getDrawable(
					R.drawable.unlocked);
			image.setBounds(0, 0, 50, 50);
			privacy.setImageDrawable(image);

		} 
		if (pv.equals("pri")){
			Drawable image = getContext().getResources().getDrawable(
					R.drawable.locked);
			image.setBounds(0, 0, 50, 50);
			privacy.setImageDrawable(image);
		}
		priv= pv;
	}

	public void changePrivacy() {
		Contact c = Prefs.getMyUserPref(super.getContext());
		if (priv.equals("pub"))
		{
			setPrivacy("pri");
			setPrivPrefAccordingToType("pri");
		}else{
			setPrivacy("pub");
			setPrivPrefAccordingToType("pub");	
		}
	}
	
	public void setPrivPrefAccordingToType(String pv)
	{
		Contact c = Prefs.getMyUserPref(super.getContext());
		if(type.equals("home"))
		{
			c._homephone_st = pv;
		}
		if(type.equals("com"))
		{
			c._comphone_st = pv;
		}
		if(type.equals("alt"))
		{
			c._altphone_st = pv;
		}
		if(type.equals("web"))
		{
			c._website_st = pv;
		}
		Prefs.setMyUserPref(super.getContext(), c);
	}
	
	public void setValuePrefAccordingToType(String pv)
	{
		Contact c = Prefs.getMyUserPref(super.getContext());
		if(type.equals("home"))
		{
			c._homephone = pv;
		}
		if(type.equals("com"))
		{
			c._comphone = pv;
		}
		if(type.equals("alt"))
		{
			c._altphone = pv;
		}
		if(type.equals("web"))
		{
			c._website = pv;
		}
		setOtherContactsIcon();
		Prefs.setMyUserPref(super.getContext(), c);
	}
	
	public String getPrivPrefAccordingToType()
	{
		Contact c = Prefs.getMyUserPref(super.getContext());
		if(type.equals("home"))
		{
			return c._homephone_st;
		}
		if(type.equals("com"))
		{
			return c._comphone_st;
		}
		if(type.equals("alt"))
		{
			return c._altphone_st;
		}
		if(type.equals("web"))
		{
			return c._website_st;
		}
		return null;
	}
	
	public String getValuePrefAccordingToType()
	{
		Contact c = Prefs.getMyUserPref(super.getContext());
		if(type.equals("home"))
		{
			return c._homephone;
		}
		if(type.equals("com"))
		{
			return c._comphone;
		}
		if(type.equals("alt"))
		{
			return c._altphone;
		}
		if(type.equals("web"))
		{
			return c._website;
		}
		return null;
	}
	
	public boolean validatePhone(String val) {
		return PHONE_PATTERN.matcher(val).matches();
	}
}

