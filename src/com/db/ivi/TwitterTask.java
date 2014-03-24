package com.db.ivi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha1MessageSigner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ivi.R;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.constant.LinkedInApiUrls;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterTask extends Activity {

	Contact c;
	final String authURL = null;
    private ProgressDialog pdia;
	public LinkedInRequestToken requestToken;
	private static final String OAUTH_KEY = "XXXXX";
	private static final String OAUTH_SECRET = "XXXXX";
	LinkedInAccessToken accessToken;
	public static final String APP_NAME = "ivi";
	public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static final String OAUTH_CALLBACK_HOST = "callback";
	public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://" + OAUTH_CALLBACK_HOST;

	LinkedInOAuthService oAuthService;
	LinkedInApiClientFactory factory;
	LinkedInRequestToken liToken;
	LinkedInApiClient client;
	Dialog alert;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pdia = new ProgressDialog(this);
        pdia.setMessage(getResources().getString(R.string.com_facebook_loading));
        pdia.show();  
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectNetwork().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
		oAuthService = LinkedInOAuthServiceFactory
				.getInstance().createLinkedInOAuthService(OAUTH_KEY, OAUTH_SECRET);
		factory = LinkedInApiClientFactory
				.newInstance(OAUTH_KEY, OAUTH_SECRET);
		liToken = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		/**
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(liToken
				.getAuthorizationUrl()));
		startActivity(i);
		**/
        ImageButton btnTag = new ImageButton(this);
        Drawable img = (getResources().getDrawable(R.drawable.dialog_close));
        img.setBounds(0, 0, 100, 100);
        LayoutParams params = new LayoutParams(50,50);
        params.setMargins(10, 20, 0, 0);
        btnTag.setLayoutParams(params);
        btnTag.setImageDrawable(img);
        
        btnTag.setId(1);
        btnTag.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v)
    		{
    			pdia.cancel();
            	alert.cancel();
            	finish();
    		}
    		});
              
        WebView wv = new WebView(this);
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
        //wv.addView(btnTag);
        wv.loadUrl(Uri.parse(liToken
				.getAuthorizationUrl()).toString());

        wv.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
            	Person profile;
            	if(url.contains("x-oauthflow-linkedin://callback?"))
        		{
                    Uri uri = Uri.parse(url);
                    String verifier = uri.getQueryParameter("oauth_verifier");
        		if(verifier != null)
        		{
        		accessToken = oAuthService.getOAuthAccessToken(liToken, verifier);
        		client = factory.createLinkedInApiClient(accessToken);
        		 profile = client.getProfileForCurrentUser(EnumSet.of(
        				ProfileField.ID, ProfileField.FIRST_NAME,
        				ProfileField.LAST_NAME, ProfileField.PICTURE_URL));
        		 	alert.cancel();
        		 	finish();
        		}
        		}
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pdia.cancel();
                alert.show();
         }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
    		 	alert.cancel();
    		 	finish();
                Toast.makeText(getApplicationContext(), "Oh no! " + description,
                        Toast.LENGTH_SHORT).show();
            }
            
        });

        builder.setView(wv);
        alert = builder.create();  

	}
/**
	@Override
	protected void onNewIntent(Intent intent) {
		if(intent.getData().toString().contains("x-oauthflow-linkedin://callback?"))
		{
		String verifier = intent.getData().getQueryParameter("oauth_verifier");
		if(verifier != null)
		{
		accessToken = oAuthService.getOAuthAccessToken(liToken, verifier);
		client = factory.createLinkedInApiClient(accessToken);
		Person profile = client.getProfileForCurrentUser(EnumSet.of(
				ProfileField.ID, ProfileField.FIRST_NAME,
				ProfileField.LAST_NAME, ProfileField.PICTURE_URL));
		this.finish();
		}
		if (accessToken == null) {
			throw new IllegalArgumentException("access token cannot be null.");
		}
		}
	}

	protected OAuthConsumer getOAuthConsumer() {
		DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(OAUTH_KEY,
				OAUTH_SECRET);
		consumer.setMessageSigner(new HmacSha1MessageSigner());
		consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
		return consumer;
	}
**/
}
