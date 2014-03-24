package old;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.model.ivi.Contact;
import com.visual.ivi.EmailItemControl;

public class FacebookConnector extends FragmentActivity{

	private static String APP_ID = "243137495823972";
	private Facebook facebook;
	private AsyncFacebookRunner mAsyncRunner;
	String access_token;
	long access_expires;
	Activity current;
	Contact c;

	public FacebookConnector() {
		facebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		facebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	@SuppressWarnings("deprecation")
	public void loginToFacebook() {
		if (getToken() != null) {
			setToken(getToken());
		}
		if (getExpire() != 0) {
			setExpire(getExpire());
		}
		if (!facebook.isSessionValid()) {
			facebook.authorize(current, new String[] { "email",
					"publish_stream" }, new DialogListener() {
				public void onCancel() {
					// Function to handle cancel event
				}

				@Override
				public void onComplete(Bundle values) {
					setToken(facebook.getAccessToken());
					setExpire(facebook.getAccessExpires());
					c = getUser();
					c.set_sn_facebook(facebook.getAccessToken());
					refreshUser(c);
				}

				@Override
				public void onFacebookError(FacebookError fberror) {
				}

				@Override
				public void onError(DialogError e) {
				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	public void logoutFromFacebook() {
		if (facebook.isSessionValid()) {
			mAsyncRunner.logout(this, new RequestListener() {
				@Override
				public void onComplete(String response, Object state) {
					Log.d("Logout from Facebook", response);
					if (Boolean.parseBoolean(response) == true) {
						setExpire(0);
						setToken(null);
						c = getUser();
						c.set_sn_facebook(null);
						refreshUser(c);
					}
				}

				@Override
				public void onIOException(IOException e, Object state) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e,
						Object state) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onMalformedURLException(MalformedURLException e,
						Object state) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFacebookError(FacebookError e, Object state) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					JSONObject profile = new JSONObject(json);
					final String name = profile.getString("name");
					final String email = profile.getString("email");
					final String xx = profile.getString("id");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"Id" + xx + "\nName: " + name + "\nEmail: "
											+ email, Toast.LENGTH_LONG).show();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	public OnClickListener facebookLoginButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Button btLogin = (Button) v.findViewById(R.id.btn_fblogin);
			if (btLogin.getText().toString() == "Logoff") {
				logoutFromFacebook();
			} else {
				loginToFacebook();
			}
		}
	};

	public Facebook getFacebook() {
		return this.facebook;
	}

	public AsyncFacebookRunner getFacebookRunner() {
		return this.mAsyncRunner;
	}

	public void setActivity(Activity act) {
		this.current = act;
	}

	public OnClickListener getFacebookClickListener() {
		return facebookLoginButtonListener;
	}
	
	public void setLoginButtonText(View v, String value)
	{
		
	}
	
	public String getLoginButtonText(View v)
	{
		Button btLogin = (Button) v.findViewById(R.id.btn_fblogin);
		return btLogin.getText().toString();
	}

	public void setLoginButtonText(Activity act, String value) {
		Button btLogin = (Button) act.findViewById(R.id.btn_fblogin);
		btLogin.setText(value);	
	}
	
	public void setToken(String value)
	{
		Prefs.setMyStringPref(getApplicationContext(), value, "access_token");
		facebook.setAccessToken(value);
	}
	public String getToken()
	{
		if(Prefs.getMyStringPref(getApplicationContext(),
			"access_token") != null && facebook.getAccessToken() != null)
		{
		return Prefs.getMyStringPref(getApplicationContext(),"access_token");
		}else{
			setToken(null);
			return null;
		}
		
	}
	public void setExpire(long value)
	{
		Prefs.setMyLongPref(getApplicationContext(), value, "access_expires");
		facebook.setAccessExpires(value);
	}
	
	public long getExpire()
	{
		if(Prefs.getMyLongPref(getApplicationContext(),
				"access_expires") != 0 && facebook.getAccessExpires() != 0)
			{
			return Prefs.getMyLongPref(getApplicationContext(),
					"access_expires");
			}else{
				setExpire(0);
				return 0;
			}
	}
	
	public Contact getUser()
	{
		return Prefs.getMyUserPref(getApplicationContext());
	}
	
	public void refreshUser(Contact c)
	{
		Prefs.setMyUserPref(getApplicationContext(), c);
	}
	
	public boolean IsSessionValid()
	{
		return facebook.isSessionValid();
	}
	
}
