package com.main.ivi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import android.animation.LayoutTransition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.db.ivi.AsyncConnectionsInserts;
import com.db.ivi.AsyncConnectionsSave;
import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.facebook.widget.UserSettingsFragment;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.model.ivi.Contact;
import com.model.ivi.ContactWeb;
import com.model.ivi.OptEmail;
import com.visual.ivi.EmailItemControl;
import com.visual.ivi.LinkedinUserSettingsFragment;
import com.visual.ivi.OtherContactsItemControl;
import com.visual.ivi.ShareDialog;

public class ProfileActivity extends FragmentActivity {
	/** Public Variables **/
	public Contact c;
	public EditText t0, t2, t3;
	public TextView button0, button1, button2, button3, button4, button5, button6;
	public LinearLayout panel0, panel1, panel2, panel3, panel4, panel5, panel6;
	public ImageView vName, vPhone, vNewEmail,PhonePriv;
	public RelativeLayout rpanel;
	public ViewGroup viewGroup0;
	public InputMethodManager imm;
	public Activity current;
	public EditText campo;
	public ScrollView scroller;
	/** Private Variables **/
	private UserSettingsFragment userSettingsFragment;
	private LinkedinUserSettingsFragment userLinkedinSettingsFragment;
	private UiLifecycleHelper uiHelper;
	private String access_token;
	/** Final and Pattern Variables **/
	private static final int SELECT_PICTURE = 1;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
	public final Pattern PHONE_PATTERN = Pattern.compile("^.{10,11}$");
	private boolean mInitialized;
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		current = this;
  		Prefs.getPrefs(getApplicationContext()).registerOnSharedPreferenceChangeListener(listener);
		
		t0 = (EditText) findViewById(R.id.editText1);
		t2 = (EditText) findViewById(R.id.editText2);
		t3 = (EditText) findViewById(R.id.newTextEmailAddress);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(t0.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(t2.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(t3.getWindowToken(), 0);
		c = (Contact) Prefs.getMyUserPref(getApplicationContext());
		rpanel = (RelativeLayout) findViewById(R.id.RegisteredEmailsContent);
		campo = (EditText) findViewById(R.id.newTextEmailAddress);
		panel0 = (LinearLayout) findViewById(R.id.panel0);
		panel1 = (LinearLayout) findViewById(R.id.panel1);
		panel2 = (LinearLayout) findViewById(R.id.panel2);
		panel3 = (LinearLayout) findViewById(R.id.panel3);
		panel4 = (LinearLayout) findViewById(R.id.panel4);
		panel5 = (LinearLayout) findViewById(R.id.panel5);
		panel6 = (LinearLayout) findViewById(R.id.panel6);
		button0 = (TextView) findViewById(R.id.button0);
		button1 = (TextView) findViewById(R.id.button1);
		button2 = (TextView) findViewById(R.id.button2);
		button3 = (TextView) findViewById(R.id.button3);
		button4 = (TextView) findViewById(R.id.button4);
		button5 = (TextView) findViewById(R.id.button5);
		button6 = (TextView) findViewById(R.id.button6);
		scroller = (ScrollView)findViewById(R.id.maincontent);
		ImageView fullprofPic = (ImageView) findViewById(R.id.imageViewFull);
		ImageView profPic = (ImageView) findViewById(R.id.imageView1);
		profPic.setOnClickListener(profilePicChooserClick);
		profPic.setOnLongClickListener(profilePicViewerClick);
		recoverProfilePic(profPic, fullprofPic);
		vName = (ImageView) findViewById(R.id.imageViewEditName);
		vPhone = (ImageView) findViewById(R.id.imageViewEditPhone);
		ImageView saveb = (ImageView) findViewById(R.id.SaveButton);
		ImageView shareb = (ImageView) findViewById(R.id.ShareButton);
		saveb.setOnClickListener(saveClickListener);
		shareb.setOnClickListener(shareClickListener);
		PhonePriv = (ImageView) findViewById(R.id.imagePrivacyCellPhone);
		setCellPhoneNumber(this);
		PhonePriv.setOnClickListener(changePrivacyListener);
		setUserName(this, c.get_username());
		LayoutTransition l1 = new LayoutTransition();
		l1.enableTransitionType(LayoutTransition.CHANGING);
		l1.getDuration(25);
		LayoutTransition l2 = new LayoutTransition();
		l2.enableTransitionType(LayoutTransition.CHANGING);
		l2.getDuration(100);
		viewGroup0 = (ViewGroup) findViewById(R.id.panelContent);
		viewGroup0.setLayoutTransition(l2);
		vName.setOnClickListener(changeViewTypeListener);
		vPhone.setOnClickListener(changeViewTypeListener);
		t0.setOnEditorActionListener(clickBotaoDone);
		t2.setOnEditorActionListener(clickBotaoDone);
		t3.setOnEditorActionListener(clickBotaoDone);
		button0.setOnClickListener(buttonClickListener);
		button1.setOnClickListener(buttonClickListener);
		button2.setOnClickListener(buttonClickListener);
		button3.setOnClickListener(buttonClickListener);
		button4.setOnClickListener(buttonClickListener);
		button5.setOnClickListener(buttonClickListener);
		button6.setOnClickListener(buttonClickListener);
		/** Set Other Contacts Panel**/
		setOtherContactsPanel(this);
		/** Set Email Panel Content and behavior **/
		setEmailPanel(this);
		/** Set Facebook Panel Content and behavior **/
		setFacebookPanel(this);
		/** Set Linkedin Panel Content and behavior **/
		setLinkedinPanel(this);
		/** Set Initial state of view **/
		closeOpenedLayouts(getApplicationContext());
		setSocialNetworkIcons(current);
		final View activityRootView = findViewById(R.id.ActivityContainer);		
	}

	private void setTwitterPanel(Activity act) {
		/**
	   
	        "https://api.twitter.com/oauth/request_token",
	        "http://api.twitter.com/oauth/access_token",
	        "http://api.twitter.com/oauth/authorize?force_login=true");
**/

	}

	
	OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		@Override
		  public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		    if(key == "linkedin_access")
		    {
		    	setSocialNetworkIcons(current);
		    }
		  }
		};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//132325
		if (requestCode == 129742) // FB Request Code - será q é fixo?
		{
			userSettingsFragment
					.onActivityResult(requestCode, resultCode, data);
			setSocialNetworkIcons(current);
			super.onActivityResult(requestCode, resultCode, data);
		}
		if (requestCode == 1253) // Linkedin Request code - será q é fixo?
		{
			if(data != null){		
				if(Prefs.getMyStringPref(getApplicationContext(), "LinkedinProfPicUrl") != null)
	    		{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Bitmap bm = retrieveProfPicture(Prefs.getMyStringPref(getApplicationContext(), "LinkedinProfPicUrl"));
				bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] b = baos.toByteArray();
				saveImageToInternalStorage(bm, Prefs.getMyUserPref(getApplicationContext()).get_sn_linkedin());
				//Prefs.setMyProfilePicUriPref(getApplicationContext(),Base64.encodeToString(b, Base64.DEFAULT));				
				//ImageView full = (ImageView) findViewById(R.id.imageViewFull);
				//ImageView pic = (ImageView) findViewById(R.id.imageView1);
				//full.setImageBitmap(bm);
				//pic.setImageBitmap(bm);
	    		}
				userLinkedinSettingsFragment.onActivityResult(requestCode, resultCode, data);
		}
		}
		if (requestCode == SELECT_PICTURE && data == null && resultCode==200)
		{
			ImageView fullprofPic = (ImageView) findViewById(R.id.imageViewFull);
			ImageView profPic = (ImageView) findViewById(R.id.imageView1);
			recycleProfileImages(fullprofPic, profPic);
			Bitmap bm = Prefs.getMyProfilePicUriPref(getApplicationContext());
			profPic.setImageBitmap(bm);
			fullprofPic.setImageBitmap(bm);
		}
		if (requestCode == SELECT_PICTURE && data != null
				&& data.getData() != null) {
			ImageView fullprofPic = (ImageView) findViewById(R.id.imageViewFull);
			ImageView profPic = (ImageView) findViewById(R.id.imageView1);
			Uri _uri = data.getData();
			if (_uri != null) {
				Bitmap bm = ShrinkBitmap(_uri, 500, 500, fullprofPic, profPic);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] b = baos.toByteArray();
				

				String image = Base64.encodeToString(b, Base64.DEFAULT);
				c = Prefs.getMyUserPref(getApplicationContext());
				Prefs.setMyProfilePicUriPref(getApplicationContext(),
						image);
				c._image_binary = image;
				Prefs.setMyUserPref(getApplicationContext(), c);
				profPic.setImageBitmap(bm);
				profPic.setScaleType(ScaleType.CENTER_CROP);
				fullprofPic.setImageBitmap(bm);

			}
			super.onActivityResult(requestCode, resultCode, data);
		}

	}

		public Bitmap retrieveProfPicture(String url) {
			try {
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = false;
				HttpURLConnection.setFollowRedirects(true);
				URL MyProfilePicURL = new URL(url);
				Bitmap bm = BitmapFactory.decodeStream(MyProfilePicURL.openConnection()
						.getInputStream(), null, o);
				return bm;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	    
		public boolean saveImageToInternalStorage(Bitmap image,
				String user_id) {
			try {
				FileOutputStream fos = openFileOutput("linkedin" + user_id + ".png",
						Context.MODE_PRIVATE);
				image.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.close();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
	/** Panels Initialization methods **/
	
	private void setOtherContactsPanel(Activity act) {
		RelativeLayout content = (RelativeLayout) findViewById(R.id.OtherContactsContent);
		content.addView(new OtherContactsItemControl(getApplicationContext(), "home", 1, button0));
		RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param1.addRule(RelativeLayout.BELOW, 1);
		content.addView(new OtherContactsItemControl(getApplicationContext(), "com", 2, button0), param1);
		RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param2.addRule(RelativeLayout.BELOW, 2);
		content.addView(new OtherContactsItemControl(getApplicationContext(), "alt", 3, button0), param2);
		RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param3.addRule(RelativeLayout.BELOW, 3);
		content.addView(new OtherContactsItemControl(getApplicationContext(), "web", 4, button0), param3);
	}
	
	private void setLinkedinPanel(Activity act) {
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		userLinkedinSettingsFragment = (LinkedinUserSettingsFragment) fragmentManager.findFragmentById(R.id.login_linkedin_fragment);

        if (userLinkedinSettingsFragment == null) {
        	userLinkedinSettingsFragment = new LinkedinUserSettingsFragment();
			android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(R.id.login_linkedin_fragment, userLinkedinSettingsFragment);
			ft.commit();
        }
}
	
	
	private void setFacebookPanel(Activity act) {
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		userSettingsFragment = (UserSettingsFragment) fragmentManager
				.findFragmentById(R.id.login_fragment);
		userSettingsFragment
				.setSessionStatusCallback(new Session.StatusCallback() {
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (state.toString() == "CLOSED") {
							c = Prefs.getMyUserPref(getApplicationContext());
							c.set_sn_facebook(null);
							Prefs.setMyUserPref(getApplicationContext(), c);
						}
						onSessionStateChange(session, state, exception);
						setSocialNetworkIcons(current);
					}

					private void onSessionStateChange(Session session,
							SessionState state, Exception exception) {
						if (session != null && session.isOpened()) {
							// Get the user's data.
							makeMeRequest(session);
						}
					}

					private void makeMeRequest(final Session session) {
						Request request = Request.newMeRequest(session,
								new Request.GraphUserCallback() {
									@Override
									public void onCompleted(GraphUser user,
											Response response) {
										// If the response is successful
										if (session == Session
												.getActiveSession()) {
											if (user != null) {
												c = Prefs
														.getMyUserPref(getApplicationContext());
												c.set_sn_facebook(user.getId());
												Prefs.setMyUserPref(
														getApplicationContext(),
														c);
												setSocialNetworkIcons(current);
											}
										}
										if (response.getError() != null) {
											// Handle errors, will do so later.
										}
									}
								});
						request.executeAsync();
						Prefs.setMyStringPref(getApplicationContext(), session.getAccessToken(), "access_token");
					}
				});
	}

	private void setEmailPanel(Activity v) {
		vNewEmail = (ImageView) v.findViewById(R.id.imageAdd);
		Drawable image = v.getApplicationContext().getResources()
				.getDrawable(R.drawable.add);
		image.setBounds(0, 0, 50, 50);
		vNewEmail.setImageDrawable(image);
		EmailItemControl email = new EmailItemControl(getApplicationContext());
		email.setId(1);
		int prevId = email.getId();
		c = (Contact) Prefs.getMyUserPref(getApplicationContext());
		email.setAddress(c._email);
		email.setAddressPrivacy(c._email_st);
		email.setEmailDeleteVisibility(false);
		vNewEmail.setOnClickListener(addEmailButtonListener);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		rpanel.addView(email, rpanel.getChildCount(), params);
		if (c.get_opt_email() != null) {
			if (c.get_opt_email().size() > 0) {
				for (int i = 0; i < c.get_opt_email().size(); i++) {
					EmailItemControl temp = new EmailItemControl(
							getApplicationContext());
					temp.setId(prevId + 1);
					temp.setAddress(c.get_opt_email().get(i).get_OptEmail());
					RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
					params2.addRule(RelativeLayout.BELOW, prevId);
					temp.setAddressPrivacy(c.get_opt_email().get(i)
							.get_OptEmailStats());
					temp.setEmailDeleteVisibility(true);
					prevId = temp.getId();
					rpanel.addView(temp, rpanel.getChildCount(), params2);
				}
			}
		} else {
			ArrayList<OptEmail> opt = new ArrayList<OptEmail>();
			c.set_opt_email(opt);
			Prefs.setMyUserPref(getApplicationContext(), c);
		}
	}

	/** Public method to set View at it's initial state **/

	public void closeOpenedLayouts(Context context) {
		Drawable imageDown = getApplicationContext().getResources()
				.getDrawable(R.drawable.close);
		imageDown.setBounds(0, 0, 22, 30);
		// text1
		LayoutParams p = (LayoutParams) panel1.getLayoutParams();
		p.height = 0;
		Drawable[] compoundDrawables = button1.getCompoundDrawables();
		Drawable snCompoundDrawable = compoundDrawables[0];
		EditText d = (EditText) findViewById(R.id.newTextEmailAddress);
		d.setError(null);
		button1.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
		// tex0
		LayoutParams p0 = (LayoutParams) panel0.getLayoutParams();
		p0.height = 0;
		compoundDrawables = button0.getCompoundDrawables();
		snCompoundDrawable = compoundDrawables[0];
		button0.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
		// text2
		LayoutParams p1 = (LayoutParams) panel2.getLayoutParams();
		p1.height = 0;
		compoundDrawables = button2.getCompoundDrawables();
		snCompoundDrawable = compoundDrawables[0];
		button2.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
		// text3
		LayoutParams p2 = (LayoutParams) panel3.getLayoutParams();
		p2.height = 0;
		compoundDrawables = button3.getCompoundDrawables();
		snCompoundDrawable = compoundDrawables[0];
		button3.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
		// text4
		LayoutParams p3 = (LayoutParams) panel4.getLayoutParams();
		p3.height = 0;
		compoundDrawables = button4.getCompoundDrawables();
		snCompoundDrawable = compoundDrawables[0];
		button4.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
		// text5
		LayoutParams p4 = (LayoutParams) panel5.getLayoutParams();
		p4.height = 0;
		compoundDrawables = button5.getCompoundDrawables();
		snCompoundDrawable = compoundDrawables[0];
		button5.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
		// text6
		LayoutParams p5 = (LayoutParams) panel6.getLayoutParams();
		p5.height = 0;
		compoundDrawables = button6.getCompoundDrawables();
		snCompoundDrawable = compoundDrawables[0];
		button6.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
	}

	/** Deals with the Username image switcher (accept and edit icon) **/

	private void setUserName(Activity v, String get_username) {
		if (!(get_username == null)) {
			ViewSwitcher vsName = (ViewSwitcher) findViewById(R.id.name_switcher);
			TextView nameText = (TextView) findViewById(R.id.textView1);
			nameText.setText(get_username);
			vsName.showNext();
			Drawable img = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.edit_icon);
			img.setBounds(0, 0, 50, 50);
			vName.setImageDrawable(img);
		} else {
			Drawable img = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.accept);
			img.setBounds(0, 0, 50, 50);
			vName.setImageDrawable(img);
		}	
	}

	/** Deals with the Cellphone image switcher (accept and edit icon) 
	 * @param get_cellphone_st 
	 * @param phonePv **/

	private void setCellPhoneNumber(Activity v) {

		if (!(c.get_cellphone() == null)) {
			ViewSwitcher vsPhone = (ViewSwitcher) findViewById(R.id.number_switcher);
			TextView phoneText = (TextView) findViewById(R.id.textView2);
			phoneText.setText(c.get_cellphone());
			vsPhone.showNext();
			Drawable img = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.edit_icon);
			img.setBounds(0, 0, 50, 50);
			vPhone.setImageDrawable(img);
			if(c.get_cellphone_st() != null)
			{
			PhonePriv.setVisibility(ImageView.VISIBLE);
			setPrivacy(c.get_cellphone_st(), PhonePriv);
			}
		} else {
			setOperatorIcon(v, c.get_simoperator());
			Drawable img = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.accept);
			img.setBounds(0, 0, 50, 50);
			vPhone.setImageDrawable(img);
			PhonePriv.setVisibility(ImageView.INVISIBLE);
		}
	}

	public void setPrivacy(String pv, ImageView v) {
		if(v.getId() == PhonePriv.getId()){
			if (pv.equals("pub")) {
				Drawable image = getApplicationContext().getResources().getDrawable(
						R.drawable.unlocked);
				image.setBounds(0, 0, 50, 50);
				v.setImageDrawable(image);

			} 
			if (pv.equals("pri")){
				Drawable image = getApplicationContext().getResources().getDrawable(
						R.drawable.locked);
				image.setBounds(0, 0, 50, 50);
				v.setImageDrawable(image);
			}
		}
	
	};
	
	public OnClickListener changePrivacyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Contact c = Prefs.getMyUserPref(getApplicationContext());
			if(c.get_cellphone_st().equals("pub"))
			{
				c.set_cellphone_st("pri");
				Prefs.setMyUserPref(getApplicationContext(), c);
				setPrivacy("pri",(ImageView) v);
			}else{
				c.set_cellphone_st("pub");
				Prefs.setMyUserPref(getApplicationContext(), c);
				setPrivacy("pub",(ImageView) v);
			}
		}
		};
	
	/**
	 * Set the number operator, executed only in devices with the
	 * .getLine1Number() method working
	 **/
	public void setOperatorIcon(Activity v, String operator) {
		EditText e2 = (EditText) v.findViewById(R.id.editText2);
		TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getLine1Number().length() != 0) {
			if (operator.contains("TIM")) {
				Drawable img = v.getApplicationContext().getResources()
						.getDrawable(R.drawable.icon_tim);
				img.setBounds(0, 0, 40, 12);
				e2.setCompoundDrawables(img, null, null, null);
			} else if (operator.contains("Claro")) {
				Drawable img = v.getApplicationContext().getResources()
						.getDrawable(R.drawable.icon_claro);
				img.setBounds(0, 0, 40, 11);
				e2.setCompoundDrawables(img, null, null, null);
			} else if (operator.contains("Claro")) {
				Drawable img = v.getApplicationContext().getResources()
						.getDrawable(R.drawable.icon_vivo);
				img.setBounds(0, 0, 40, 11);
				e2.setCompoundDrawables(img, null, null, null);
			} else if (operator.contains("Vivo")) {
				Drawable img = v.getApplicationContext().getResources()
						.getDrawable(R.drawable.icon_oi);
				img.setBounds(0, 0, 25, 16);
				e2.setCompoundDrawables(img, null, null, null);
			} else {
				e2.setCompoundDrawables(null, null, null, null);
			}
		} else {
			e2.setCompoundDrawables(null, null, null, null);
		}
	}

	/** Change the active network sign in the buttons **/
	public void setSocialNetworkIcons(Activity v) {
		c = Prefs.getMyUserPref(getApplicationContext());
		Drawable imageDown = getApplicationContext().getResources()
				.getDrawable(R.drawable.close);
		imageDown.setBounds(0, 0, 22, 30);
		// PHONES
		if (c._homephone == null && c._comphone == null && c._altphone == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.othercontacts_2);
			image.setBounds(0, 0, 50, 50);
			button0.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.othercontacts);
			image.setBounds(0, 0, 50, 50);
			button0.setCompoundDrawables(image, null, imageDown, null);
		}
		
		
		// EMAIL
		if (c._email == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.email_2);
			image.setBounds(0, 0, 50, 50);
			button1.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.email);
			image.setBounds(0, 0, 50, 50);
			button1.setCompoundDrawables(image, null, imageDown, null);
		}

		// FACEBOOK
		if (c._sn_facebook == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.facebook_2);
			image.setBounds(0, 0, 50, 50);
			button2.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.facebook);
			image.setBounds(0, 0, 50, 50);
			button2.setCompoundDrawables(image, null, imageDown, null);
		}

		// LINKEDIN
		if (c._sn_linkedin == null && Prefs.getLinkedinUserPref(current) == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.linkedin_2);
			image.setBounds(0, 0, 50, 50);
			button3.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.linkedin);
			image.setBounds(0, 0, 50, 50);
			button3.setCompoundDrawables(image, null, imageDown, null);
		}

		// GOOGLE+
		if (c._sn_googleplus == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.gplus2_2);
			image.setBounds(0, 0, 50, 50);
			button4.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.gplus2);
			image.setBounds(0, 0, 50, 50);
			button4.setCompoundDrawables(image, null, imageDown, null);
		}

		// TWITTER
		if (c._sn_twitter == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.twitter_2);
			image.setBounds(0, 0, 50, 50);
			button5.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.twitter);
			image.setBounds(0, 0, 50, 50);
			button5.setCompoundDrawables(image, null, imageDown, null);
		}

		// SKYPE
		if (c._sn_skype == null) {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.skype_2);
			image.setBounds(0, 0, 50, 50);
			button6.setCompoundDrawables(image, null, imageDown, null);
		} else {
			Drawable image = v.getApplicationContext().getResources()
					.getDrawable(R.drawable.skype);
			image.setBounds(0, 0, 50, 50);
			button6.setCompoundDrawables(image, null, imageDown, null);
		}
	}

	/**
	 * Listener section clickBotaoDone - Identify the Done button click in
	 * SoftInputWindow - Keyboard and triggers the correspondent button to
	 * perfom the action addEmailButtonListener - Corresponds to the Add email
	 * button - All the data validation is made inside this listener
	 * profilePicChooserClick - Triggers the Options of sources the user can
	 * choose for his profile pic (simple click) profilePicViewerClick - Shows
	 * the full screen sized profile pic (long click)
	 * changeFullProfPicVisibilityClick - Close the full screen sized profile
	 * pic when clicking over the image buttonClickListener - Click that
	 * open/closes the accordion changeViewTypeListener - Do all the magic with
	 * the Phone and Username field transformation and persistence
	 * **/

	public OnEditorActionListener clickBotaoDone = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				if (v.getId() == R.id.editText1) {
					imm.hideSoftInputFromWindow(t0.getWindowToken(), 0);
					vName.performClick();
					return true;
				}
				if (v.getId() == R.id.editText2) {
					imm.hideSoftInputFromWindow(t2.getWindowToken(), 0);
					vPhone.performClick();
					/**downloadUserProfilePic();**/
					return true;
				}
				if (v.getId() == R.id.newTextEmailAddress) {
					imm.hideSoftInputFromWindow(t3.getWindowToken(), 0);
					vNewEmail.performClick();
					return true;
				}
			}
			return false;
		}
	};

	public OnClickListener addEmailButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int registered = rpanel.getChildCount();
			if (!checkEmail(campo.getText().toString())) {
				campo.setError(v.getContext().getResources()
						.getString(R.string.EmailErrorMessage1));
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			} else if (registered >= 5) {
				campo.setError(v.getContext().getResources()
						.getString(R.string.EmailErrorMessage4));
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			} else if (alreadyRegisteredEmail() == true) {
				campo.setError(v.getContext().getResources()
						.getString(R.string.EmailErrorMessage2));
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			} else {
				campo.setError(null);
				c = Prefs.getMyUserPref(getApplicationContext());
				c.addOptEmail(campo.getText().toString());
				EmailItemControl email = new EmailItemControl(
						getApplicationContext());
				email.setAddress(campo.getText().toString());
				email.setAddressPrivacy("pub");
				email.setEmailDeleteVisibility(true);
				EmailItemControl prev = (EmailItemControl) rpanel
						.getChildAt(rpanel.getChildCount() - 1);
				email.setId(prev.getId() + 1);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.BELOW, prev.getId());
				Prefs.setMyUserPref(getApplicationContext(), c);
				imm.hideSoftInputFromWindow(campo.getWindowToken(), 0);
				campo.setText(null);
				View ft = (View) findViewById(R.id.footer);
				ft.setVisibility(View.VISIBLE);
				rpanel.addView(email, params);
				v.requestLayout();
			}
		}

		private boolean alreadyRegisteredEmail() {
			for (int i = 1; i <= rpanel.getChildCount(); i++) {
				EmailItemControl p = (EmailItemControl) rpanel
						.getChildAt(i - 1);
				if (p.getAddress().equals(campo.getText().toString())) {
					return true;
				}
			}
			return false;
		}

		private boolean checkEmail(String email) {
			return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
		}
	};

	public OnClickListener profilePicChooserClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(isNetworkAvailable())
			{
			Intent pickIntent = new Intent();
			pickIntent.setType("image/*");
			pickIntent.setAction(Intent.ACTION_GET_CONTENT);
			Intent picFacebook = new Intent(current, com.db.ivi.FacebookProfileTask.class );
			Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			String pickTitle = getResources().getString(
					R.string.SelectProfilePicText).toString();
			Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					new Intent[] { takePhotoIntent, picFacebook });
			startActivityForResult(chooserIntent, SELECT_PICTURE);
			}else{
				Intent pickIntent = new Intent();
				pickIntent.setType("image/*");
				pickIntent.setAction(Intent.ACTION_GET_CONTENT);
				Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				String pickTitle = getResources().getString(
						R.string.SelectProfilePicText).toString();
				Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
						new Intent[] { takePhotoIntent});
				startActivityForResult(chooserIntent, SELECT_PICTURE);
			}
		}
		
		private boolean isNetworkAvailable() {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null;
		}

	};

	public OnLongClickListener profilePicViewerClick = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View view) {
			final Dialog dialog = new Dialog(current);
			dialog.setContentView(R.layout.fullprofilepic_dialog);
			dialog.setTitle("Profile Picture");
			
			ImageView image = (ImageView) dialog.findViewById(R.id.imageViewFull);
			if(Prefs.getMyProfilePicUriPref(getApplicationContext()) != null)
			{
			image.setImageBitmap(Prefs.getMyProfilePicUriPref(getApplicationContext()));
			}
			// if button is clicked, close the custom dialog
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
 
			dialog.show();
			return false;
		}
	};

	public OnClickListener buttonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			closeOpenedLayouts(v);
			TextView ClickedButton = (TextView) v;
			Drawable imageUp = getApplicationContext().getResources()
					.getDrawable(R.drawable.open);
			Drawable imageDown = getApplicationContext().getResources()
					.getDrawable(R.drawable.close);
			imageUp.setBounds(0, 0, 22, 30);
			imageDown.setBounds(0, 0, 22, 30);
			// Left, top, right, bottom drawables.
			Drawable[] compoundDrawables = ((TextView) v)
					.getCompoundDrawables();
			Drawable snCompoundDrawable = compoundDrawables[0];
			if (ClickedButton.getId() == button0.getId()) {

				if (panel0.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel0
							.getLayoutParams();
					params.height = 0;
					button0.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {
					panel0.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					button0.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
					scroller.scrollBy(0, button0.getTop());
				}
			}else if (ClickedButton.getId() == button1.getId()) {
				if (panel1.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel1
							.getLayoutParams();
					params.height = 0;
					button1.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {
					panel1.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					button1.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
					scroller.scrollBy(0, button0.getBottom());
				}
			} else if (ClickedButton.getId() == button2.getId()) {

				if (panel2.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel2
							.getLayoutParams();
					params.height = 0;
					button2.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {
					panel2.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					button2.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
					scroller.scrollBy(0, button1.getBottom());
				}
			} else if (ClickedButton.getId() == button3.getId()) {
				if (panel3.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel3
							.getLayoutParams();
					params.height = 0;
					button3.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {

					panel3.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					button3.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
					scroller.scrollBy(0, button6.getTop());
				}
			} else if (ClickedButton.getId() == button4.getId()) {
				if (panel4.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel4
							.getLayoutParams();
					params.height = 0;
					button4.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {
					LayoutParams p = (LayoutParams) panel4.getLayoutParams();
					p.height = 200;
					button4.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
				}
			} else if (ClickedButton.getId() == button5.getId()) {
				if (panel5.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel5
							.getLayoutParams();
					params.height = 0;
					button5.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {
					LayoutParams p = (LayoutParams) panel5.getLayoutParams();
					p.height = 200;
					button5.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
				}
			} else if (ClickedButton.getId() == button6.getId()) {
				if (panel6.getHeight() != 0) {
					LayoutParams params = (LayoutParams) panel6
							.getLayoutParams();
					params.height = 0;
					button6.setCompoundDrawables(snCompoundDrawable, null,
							imageDown, null);
				} else {
					LayoutParams p = (LayoutParams) panel6.getLayoutParams();
					p.height = 200;
					button6.setCompoundDrawables(snCompoundDrawable, null,
							imageUp, null);
				}
			}
			View ft = (View) findViewById(R.id.footer);
			ft.setVisibility(View.VISIBLE);
			v.requestLayout();
		}

		public void closeOpenedLayouts(View v) {
			Drawable imageDown = getApplicationContext().getResources()
					.getDrawable(R.drawable.close);
			imageDown.setBounds(0, 0, 22, 30);
			// text1
			LayoutParams p = (LayoutParams) panel1.getLayoutParams();
			p.height = 0;
			Drawable[] compoundDrawables = button1.getCompoundDrawables();
			Drawable snCompoundDrawable = compoundDrawables[0];
			EditText d = (EditText) ((Activity) v.getContext())
					.findViewById(R.id.newTextEmailAddress);
			d.setError(null);
			button1.setCompoundDrawables(snCompoundDrawable, null, imageDown,
					null);
			// tex0
			LayoutParams p0 = (LayoutParams) panel0.getLayoutParams();
			p0.height = 0;
			compoundDrawables = button0.getCompoundDrawables();
			snCompoundDrawable = compoundDrawables[0];
			button0.setCompoundDrawables(snCompoundDrawable, null, imageDown, null);
			// text2
			LayoutParams p1 = (LayoutParams) panel2.getLayoutParams();
			p1.height = 0;
			compoundDrawables = button2.getCompoundDrawables();
			snCompoundDrawable = compoundDrawables[0];
			button2.setCompoundDrawables(snCompoundDrawable, null, imageDown,
					null);
			// text3
			LayoutParams p2 = (LayoutParams) panel3.getLayoutParams();
			p2.height = 0;
			compoundDrawables = button3.getCompoundDrawables();
			snCompoundDrawable = compoundDrawables[0];
			button3.setCompoundDrawables(snCompoundDrawable, null, imageDown,
					null);
			// text4
			LayoutParams p3 = (LayoutParams) panel4.getLayoutParams();
			p3.height = 0;
			compoundDrawables = button4.getCompoundDrawables();
			snCompoundDrawable = compoundDrawables[0];
			button4.setCompoundDrawables(snCompoundDrawable, null, imageDown,
					null);
			// text5
			LayoutParams p4 = (LayoutParams) panel5.getLayoutParams();
			p4.height = 0;
			compoundDrawables = button5.getCompoundDrawables();
			snCompoundDrawable = compoundDrawables[0];
			button5.setCompoundDrawables(snCompoundDrawable, null, imageDown,
					null);
			// text6
			LayoutParams p5 = (LayoutParams) panel6.getLayoutParams();
			p5.height = 0;
			compoundDrawables = button6.getCompoundDrawables();
			snCompoundDrawable = compoundDrawables[0];
			button6.setCompoundDrawables(snCompoundDrawable, null, imageDown,
					null);
		}
	};

	public OnClickListener changeViewTypeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ImageView ClickedButton = (ImageView) v;
			ViewSwitcher vs;
			if (ClickedButton.getId() == vName.getId()) {
				vs = (ViewSwitcher) findViewById(R.id.name_switcher);
				// Name do Edit -> Text
				if (vs.getDisplayedChild() == 0) {
					EditText edit = (EditText) findViewById(R.id.editText1);
					if (edit.getText().toString().length() > 0) {
						edit.setError(null);
						imm.hideSoftInputFromWindow(t0.getWindowToken(), 0);
						TextView txt = (TextView) findViewById(R.id.textView1);
						txt.setText(edit.getText().toString());
						c = Prefs.getMyUserPref(getApplicationContext());
						c.set_username(edit.getText().toString());
						Prefs.setMyUserPref(getApplicationContext(), c);
						Drawable img = getApplicationContext().getResources()
								.getDrawable(R.drawable.edit_icon);
						img.setBounds(0, 0, 50, 50);
						vName.setImageDrawable(img);
						vs.showNext();
					} else {
						edit.setError(v.getResources().getString(
								R.string.UserNameErrorMessage1));
					}
				} else {
					// Name do Text -> Edit
					TextView txt = (TextView) findViewById(R.id.textView1);
					t0.setText(txt.getText().toString());
					Drawable img = getApplicationContext().getResources()
							.getDrawable(R.drawable.accept);
					img.setBounds(0, 0, 50, 50);
					vName.setImageDrawable(img);
					vs.showPrevious();
					t0.setFocusableInTouchMode(true);
					t0.requestFocus();
					imm.showSoftInput(t0, 0);
				}
				// ////////PHONE
			} else if (ClickedButton.getId() == vPhone.getId()) {
				vs = (ViewSwitcher) findViewById(R.id.number_switcher);
				if (vs.getDisplayedChild() == 0) {
					// Phone do Edit -> Text
					EditText edit = (EditText) findViewById(R.id.editText2);
					if (validatePhone(edit.getText().toString())) {
						edit.setError(null);
						imm.hideSoftInputFromWindow(t2.getWindowToken(), 0);
						TextView txt = (TextView) findViewById(R.id.textView2);
						txt.setText(edit.getText().toString());
						c = Prefs.getMyUserPref(getApplicationContext());
						c.set_cellphone(edit.getText().toString());
						c.set_cellphone_st("pub");
						Prefs.setMyUserPref(getApplicationContext(), c);
						setPrivacy("pub", PhonePriv);
						PhonePriv.setVisibility(ImageView.VISIBLE);
						
						Drawable img = getApplicationContext().getResources()
								.getDrawable(R.drawable.edit_icon);
						img.setBounds(0, 0, 50, 50);
						vPhone.setImageDrawable(img);
						vs.showNext();
					} else {
						edit.setError(v.getResources().getString(
								R.string.PhoneErrorMessage1));
					}
				} else {
					// Phone do Text -> Edit
					TextView txt = (TextView) findViewById(R.id.textView2);
					t2.setText(txt.getText().toString());
					Drawable img = getApplicationContext().getResources()
							.getDrawable(R.drawable.accept);
					img.setBounds(0, 0, 50, 50);

					PhonePriv.setVisibility(ImageView.INVISIBLE);
					vPhone.setImageDrawable(img);
					vs.showPrevious();
					t2.setFocusableInTouchMode(true);
					t2.requestFocus();
					imm.showSoftInput(t2, 0);
				}
			}
		}

		public boolean validatePhone(String val) {
			return PHONE_PATTERN.matcher(val).matches();
		}
	};

	
	public OnClickListener saveClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(isNetworkAvailable())
			{
				AsyncConnectionsSave thread = new AsyncConnectionsSave(current,	"updateUser");
				thread.execute(Prefs.getMyUserPref(getApplicationContext()));
				try {
					int i = thread.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.NetworkUnavailableMsg),Toast.LENGTH_LONG).show();
			}
		}
	};

	public OnClickListener shareClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ShareDialog sd = new ShareDialog(getApplicationContext(), current);
			sd.share();
		}
	};
	
	/**
	 * Profile Picture Methods
	 * 
	 * ShrinkBitmap - Downsize the image picked by the user and rotate according
	 * to the orientation of the pic RecoverProfilePic - Retrieve image and set
	 * initial state of the profile picture
	 * 
	 * **/

	private Bitmap ShrinkBitmap(Uri _uri, int width, int height,
			ImageView fullprofPic, ImageView profPic) {
		/** Reduce image quality to lower the memory usage **/
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = false;
		bmpFactoryOptions.inScaled = true;
		bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap _bitmapScaled = null;
		Bitmap _bitmapPreScale;
		/** Recycle the current profile pic so that it doesn't run out of memory **/
		recycleProfileImages(fullprofPic, profPic);
		try {
			/** Procedure to set image as Bitmap **/
			_bitmapPreScale = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(_uri), null, bmpFactoryOptions);
			int oldWidth = _bitmapPreScale.getWidth();
			int oldHeight = _bitmapPreScale.getHeight();
			int newWidth = width;
			int newHeight = height;
			int degree = 0;
			try {
				/**
				 * First method to get orientation - MediaStore info (some
				 * images don't store)
				 **/
				Cursor mediaCursor = getContentResolver()
						.query(_uri,
								new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
								null, null, null);
				if (mediaCursor != null) {
					mediaCursor.moveToFirst();
					degree = mediaCursor.getInt(0);
					mediaCursor.close();
				}
				/**
				 * Second method to get orientation - ExifInterface info (some
				 * images don't store)
				 **/
				if (degree == 0) {
					ExifInterface exif;
					int orientation;
					exif = new ExifInterface(_uri.getPath());
					orientation = (int) exif.getAttributeDouble(
							ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_NORMAL);
					switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;
					case 0:
						degree = 0;
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			/** Scaling calculation **/
			float scaleWidth = ((float) newWidth) / oldWidth;
			float scaleHeight = ((float) newHeight) / oldHeight;
			float scale = (scaleWidth <= scaleHeight) ? scaleWidth
					: scaleHeight;
			/** Setting matrix with rotation and resize **/
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			if (degree != 0) {
				matrix.postRotate(degree);
			}
			_bitmapScaled = Bitmap.createBitmap(_bitmapPreScale, 0, 0,
					oldWidth, oldHeight, matrix, true);
			_bitmapPreScale = null;
			return _bitmapScaled;
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		return _bitmapScaled;
	}

	/** Retrieving ProfilePic out of SharedPreferences method **/
	private void recoverProfilePic(ImageView profPic, ImageView fullprofPic) {
		if (Prefs.getMyProfilePicUriPref(getApplicationContext()) != null) {
			profPic.setImageBitmap(Prefs
					.getMyProfilePicUriPref(getApplicationContext()));
			profPic.setScaleType(ScaleType.CENTER_CROP);
			fullprofPic.setImageBitmap(Prefs
					.getMyProfilePicUriPref(getApplicationContext()));
		}
	}

	/** Free profilepicture and full profilepicture memory **/
	public void recycleProfileImages(ImageView fullprofPic, ImageView profPic) {
		Drawable drawable = fullprofPic.getDrawable();
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			bitmap.recycle();
		}
		Drawable drawable2 = profPic.getDrawable();
		if (drawable2 instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable2;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			bitmap.recycle();
		}
	}

	
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}
