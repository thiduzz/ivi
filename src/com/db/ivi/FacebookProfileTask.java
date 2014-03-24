package com.db.ivi;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.ivi.R;
import com.example.ivi.R.layout;
import com.example.ivi.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class FacebookProfileTask extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_profile);
		//downloadUserProfilePic(this);
		TaskDownloader myTask = new TaskDownloader(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.facebook_profile_task, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	public final void downloadUserProfilePic(Activity act) {
		final AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {

			
			@Override
			public Bitmap doInBackground(Void... params) {
				Bitmap fbAvatarBitmap = null;
				try {
					BitmapFactory.Options o = new BitmapFactory.Options();
					o.inJustDecodeBounds = false;
					access_token = Prefs.getMyStringPref(getApplicationContext(), "access_token");
					
					c = Prefs.getMyUserPref(getApplicationContext());
					if (c.get_sn_facebook() != null && access_token != null) {
						HttpURLConnection.setFollowRedirects(true);
						URL MyProfilePicURL = new URL(
								"https://graph.facebook.com/me/picture?height=500&width=500&method=GET&access_token="+ access_token);
						fbAvatarBitmap = BitmapFactory
								.decodeStream(MyProfilePicURL.openConnection()
										.getInputStream(), null, o);
						saveImageToInternalStorage(fbAvatarBitmap,
								c.get_sn_facebook());
						// TESTE
						
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return fbAvatarBitmap;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				if(result != null)
				{
				//ImageView profPic = (ImageView) findViewById(R.id.imageView1);
				//ImageView fullprofPic = (ImageView) findViewById(R.id.imageViewFull);
				//recycleProfileImages(profPic, fullprofPic);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				result.compress(Bitmap.CompressFormat.PNG, 100, baos);
				b = baos.toByteArray();
				Prefs.setMyProfilePicUriPref(getApplicationContext(),
						Base64.encodeToString(b, Base64.DEFAULT));
				this.cancel(true);
				}else{
				}
			}

			public boolean saveImageToInternalStorage(Bitmap image,
					String user_id) {
				try {
					FileOutputStream fos = openFileOutput(user_id + ".png",
							Context.MODE_PRIVATE);
					image.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.close();
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		};
		task.execute();
		this.setResult(200);
		this.finish();
		
	}
	**/
}
