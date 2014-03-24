package com.visual.ivi;

import java.util.List;

import com.example.ivi.R;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.android.Facebook;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.Toast;

public class ShareDialog {
	Context context;

	Activity current;

	public ShareDialog(Context context, Activity cur) {
		this.context = context;
		this.current = cur;
	}

	private void publishFacebookFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", "Facebook SDK for Android");
		params.putString("caption",
				"Build great social apps and get more installs.");
		params.putString(
				"description",
				"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		params.putString("link", "https://developers.facebook.com/android");
		params.putString("picture",
				"https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(current,
				Session.getActiveSession(), params)).setOnCompleteListener(
				new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(current,
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(current.getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(current.getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(current.getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}).build();
		feedDialog.show();
	}

	public void share() {
		Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
		sendIntent.setType("text/plain");
		List activities = context.getPackageManager().queryIntentActivities(
				sendIntent, 0);
		for(int i = 0;i<activities.size();i++)
		{
			String app = activities.get(i).toString();
			if(app.contains("googlequicksearch") || app.contains("android.mms") || app.contains("translate") ||  app.contains("bluetooth") ||  app.contains("dropbox") || app.contains("FileShareClient") ||  app.contains("penmemo") ||  app.contains("facebook.orca"))
			{
				activities.remove(i);
				i--;
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(current);
		builder.setTitle("Share with...");
		final ShareIntentListAdapter adapter = new ShareIntentListAdapter(
				current, R.layout.sharelist_item,
				activities.toArray());
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ResolveInfo info = (ResolveInfo) adapter.getItem(which);
				if (info.activityInfo.packageName.contains("facebook")) {
					publishFacebookFeedDialog();
				} else {
					Intent intent = new Intent(
							android.content.Intent.ACTION_SEND);
					intent.setClassName(info.activityInfo.packageName,
							info.activityInfo.name);
					intent.setType("text/plain");
					intent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							"IvI - Connecting people");
					String shareMessage = current.getApplicationContext()
							.getResources().getString(R.string.ShareMessage);
					intent.putExtra(android.content.Intent.EXTRA_TEXT,
							shareMessage);
					current.startActivity(intent);
				}
			}
		});
		builder.create().show();
	}
}