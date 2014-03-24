package com.db.ivi;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.ivi.R;
import com.model.ivi.Contact;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

public class TaskDownloader extends AsyncTask<Void, Void, Bitmap>
{
    public Activity activity;
	private String access_token;
	public Contact c;
	byte[] b;
    public TaskDownloader(Activity a)
    {
        activity = a;
        pdia = new ProgressDialog(a);
        this.execute();
    }

    private ProgressDialog pdia;

    @Override
    protected void onPreExecute(){ 
       super.onPreExecute();
            pdia.setMessage(activity.getResources().getString(R.string.com_facebook_loading));
            pdia.show();    
    }
    
    @Override
	public Bitmap doInBackground(Void... params) {
		Bitmap fbAvatarBitmap = null;
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = false;
			access_token = Prefs.getMyStringPref(activity.getApplicationContext(), "access_token");
			
			c = Prefs.getMyUserPref(activity.getApplicationContext());
			if (c.get_sn_facebook() != null && access_token != null) {
				HttpURLConnection.setFollowRedirects(true);
				URL MyProfilePicURL = new URL(
						"https://graph.facebook.com/me/picture?height=500&width=500&method=GET&access_token="+ access_token);
				fbAvatarBitmap = BitmapFactory
						.decodeStream(MyProfilePicURL.openConnection()
								.getInputStream(), null, o);
				saveImageToInternalStorage(fbAvatarBitmap,
						c.get_sn_facebook());
				
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
		String image = Base64.encodeToString(b, Base64.DEFAULT);
		c = Prefs.getMyUserPref(activity.getApplicationContext());
		Prefs.setMyProfilePicUriPref(activity.getApplicationContext(),
				image);
		c._image_binary = image;
		Prefs.setMyUserPref(activity.getApplicationContext(), c);
		pdia.dismiss();
		this.cancel(true);
		activity.setResult(200);
		activity.finish();
		}else{
		}
	}

	public boolean saveImageToInternalStorage(Bitmap image,
			String user_id) {
		try {
			FileOutputStream fos = this.activity.openFileOutput(user_id + ".png",
					Context.MODE_PRIVATE);
			image.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}