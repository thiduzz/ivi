package com.db.ivi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EnumSet;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.example.ivi.R;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;
import com.model.ivi.Contact;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class LinkedinTask extends Activity {

	Contact c;
	final String authURL = null;
	private ProgressDialog pdia;
	Intent i;
	public LinkedInRequestToken requestToken;
	private static final String OAUTH_KEY = "XXX";
	private static final String OAUTH_SECRET = "XXXX";
	LinkedInAccessToken accessToken;
	public static final String APP_NAME = "ivi";
	public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static final String OAUTH_CALLBACK_HOST = "callback";
	public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://" + OAUTH_CALLBACK_HOST;
	WebView wv;
	InputMethodManager imm;
	LinkedInOAuthService oAuthService;
	LinkedInApiClientFactory factory;
	LinkedInRequestToken liToken;
	LinkedInApiClient client;
	Dialog alert;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		i = getIntent();
		pdia = new ProgressDialog(this);
		pdia.setMessage(getResources().getString(R.string.com_facebook_loading));
		pdia.show();
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectNetwork().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
		oAuthService = LinkedInOAuthServiceFactory.getInstance()
				.createLinkedInOAuthService(OAUTH_KEY, OAUTH_SECRET);
		factory = LinkedInApiClientFactory.newInstance(OAUTH_KEY, OAUTH_SECRET);
		liToken = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		/**
		 * Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(liToken
		 * .getAuthorizationUrl())); startActivity(i);
		 **/
		ImageButton btnTag = new ImageButton(this);
		Drawable img = (getResources().getDrawable(R.drawable.dialog_close));
		img.setBounds(0, 0, 100, 100);
		LayoutParams params = new LayoutParams(50, 50);
		params.setMargins(10, 20, 0, 0);
		btnTag.setLayoutParams(params);
		btnTag.setImageDrawable(img);

		btnTag.setId(1);
		btnTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pdia.cancel();
				alert.cancel();
				i.putExtra("resultLinkedin", "CANCELED");
				setResult(RESULT_OK, i);
				finish();
			}
		});

		wv = new WebView(this){
			@Override
			public boolean onCheckIsTextEditor() {
				return true; 
			}
			
		};
		RelativeLayout relativeLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, btnTag.getId());
		btnTag.setLayoutParams(lp);
		relativeLayout.addView(btnTag);

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		relativeLayout.setLayoutParams(rlp);
		wv.addView(relativeLayout);
		// wv.addView(btnTag);
		wv.loadUrl(Uri.parse(liToken.getAuthorizationUrl()).toString());
		wv.setWebViewClient(new WebViewClient() {
		
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				Person profile;
				if (url.contains("oauth_problem=user_refused")){
					i.putExtra("resultLinkedin", "CANCELED");
					setResult(RESULT_OK, i);
					alert.cancel();
					pdia.cancel();
					finish();
				}else if (url.contains("x-oauthflow-linkedin://callback?")) {
					Uri uri = Uri.parse(url);
					String verifier = uri.getQueryParameter("oauth_verifier");
					if (verifier != null) {
						accessToken = oAuthService.getOAuthAccessToken(liToken,
								verifier);
						client = factory.createLinkedInApiClient(accessToken);
						profile = client.getProfileForCurrentUser(EnumSet.of(
								ProfileField.ID, ProfileField.FIRST_NAME,
								ProfileField.LAST_NAME,
								ProfileField.PICTURE_URL));
						c = Prefs.getMyUserPref(getApplicationContext());
						c._sn_linkedin = profile.getId();
						Prefs.setMyUserPref(getApplicationContext(), c);
						Prefs.setMyStringPref(getApplicationContext(),
								profile.getPictureUrl(), "LinkedinProfPicUrl");
						Prefs.setMyStringPref(getApplicationContext(),
								profile.getFirstName(), "LinkedinFirstName");
						Prefs.setMyStringPref(getApplicationContext(),
								profile.getLastName(), "LinkedinLastName");
						Prefs.setLinkedinUserPref(getApplicationContext(),
								accessToken);
						i.putExtra("resultLinkedin", "GOOD");
						setResult(RESULT_OK, i);
						alert.cancel();
						pdia.cancel();
						finish();
					}
				}else{
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					startActivity(intent);
				}
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (i.getExtras() == null && url.contains("authorize?oauth_token")) {
					pdia.cancel();
					alert.show();
				}
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				alert.cancel();
				finish();
				i.putExtra("resultLinkedin", "FAILED");
				setResult(RESULT_OK, i);
				finish();
			}

		});
		builder.setView(wv);
		alert = builder.create();
		alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * @Override protected void onNewIntent(Intent intent) {
	 *           if(intent.getData().
	 *           toString().contains("x-oauthflow-linkedin://callback?")) {
	 *           String verifier =
	 *           intent.getData().getQueryParameter("oauth_verifier");
	 *           if(verifier != null) { accessToken =
	 *           oAuthService.getOAuthAccessToken(liToken, verifier); client =
	 *           factory.createLinkedInApiClient(accessToken); Person profile =
	 *           client.getProfileForCurrentUser(EnumSet.of( ProfileField.ID,
	 *           ProfileField.FIRST_NAME, ProfileField.LAST_NAME,
	 *           ProfileField.PICTURE_URL)); this.finish(); } if (accessToken ==
	 *           null) { throw new
	 *           IllegalArgumentException("access token cannot be null."); } } }
	 * 
	 *           protected OAuthConsumer getOAuthConsumer() {
	 *           DefaultOAuthConsumer consumer = new
	 *           DefaultOAuthConsumer(OAUTH_KEY, OAUTH_SECRET);
	 *           consumer.setMessageSigner(new HmacSha1MessageSigner());
	 *           consumer.setSigningStrategy(new
	 *           AuthorizationHeaderSigningStrategy()); return consumer; }
	 **/
}
