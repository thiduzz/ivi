package com.db.ivi;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.ivi.Contact;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.util.Base64;

public class Prefs {
    private static String MY_USER_PREF = "myuserpref";
    private static String MY_STRING_PREF = "mystringpref";
    private static String MY_INT_PREF = "myintpref";

    public static SharedPreferences getPrefs(Context context) {
    	
        return context.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
    }
    
    public static String getMyStringPref(Context context, String name) {
        return getPrefs(context).getString(name, null);
    }
    
    public static Bitmap getMyProfilePicUriPref(Context context) {
    	try{
    	String previouslyEncodedImage = getPrefs(context).getString("profile_pic",null);
    	if( !previouslyEncodedImage.equalsIgnoreCase("") ){
    	    byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
    	    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
    	    return bitmap;
    	}
    	}catch(NullPointerException e)
    	{
    		return null;
    	}
		return null;
    }
    
    public static long getMyLongPref(Context context, String name) {
        return getPrefs(context).getLong(name, 0);
    }
    
    public static Contact getMyUserPref(Context context)
    {
    	Gson gson = new Gson();
    	String json = getPrefs(context).getString(MY_USER_PREF, null);    
    	return gson.fromJson(json, Contact.class);
    }
    
    public static Contact getSelectedUserPref(Context context)
    {
    	Gson gson = new Gson();
    	String json = getPrefs(context).getString("selected_user", null);    
    	return gson.fromJson(json, Contact.class);
    }
    
    public static LinkedInAccessToken getLinkedinUserPref(Context context)
    {
    	Gson gson = new Gson();
    	String json = getPrefs(context).getString("linkedin_access", null);    
    	return gson.fromJson(json, LinkedInAccessToken.class);
    }
    
    public static ArrayList<Contact> getPendingFriends(Context context)
    {
    	Gson gson = new Gson();
    	String json = getPrefs(context).getString("pending_friends", null);   
    	Type listType = new TypeToken<ArrayList<Contact>>(){}.getType();
    	return gson.fromJson(json, listType);
    }
    
    public static void setMyProfilePicUriPref(Context context, String value) {
        getPrefs(context).edit().putString("profile_pic", value).commit();
    }
    
    public static PointF getMyCoordinates(Context context) {
        return new PointF(getPrefs(context).getFloat("x_pos", 0), getPrefs(context).getFloat("y_pos", 0));
    }
    
    public static void setMyCoordinates(Context context, float x, float y) {
        getPrefs(context).edit().putFloat("x_pos", x).commit();
        getPrefs(context).edit().putFloat("y_pos", y).commit();
    }
    
    public static void setMyStringPref(Context context, String value, String name) {
        // perform validation etc..
        getPrefs(context).edit().putString(name, value).commit();
    }

    public static void setMyLongPref(Context context,long value, String name) {
        // perform validation etc..
        getPrefs(context).edit().putLong(name, value).commit();
    }
    
    public static void setMyUserPref(Context context, Contact c)
    {

        Editor prefsEditor =  getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(c);
        prefsEditor.putString(MY_USER_PREF, json);
        prefsEditor.commit();
    }
    
    public static void setSelectedUserPref(Context context, Contact c)
    {

        Editor prefsEditor =  getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(c);
        prefsEditor.putString("selected_user", json);
        prefsEditor.commit();
    }
    
    public static void setLinkedinUserPref(Context context, LinkedInAccessToken l)
    {

        Editor prefsEditor =  getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(l);
        prefsEditor.putString("linkedin_access", json);
        prefsEditor.commit();
    }
    
    public static void setPendingFriends(Context context, ArrayList<Contact> c)
    {
        Editor prefsEditor =  getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(c.toArray());
        prefsEditor.putString("pending_friends", json);
        prefsEditor.commit();
    }
    
    public static boolean clearMyPreferences(Context context)
    {
        Editor prefsEditor =  getPrefs(context).edit();
        prefsEditor.clear();
        prefsEditor.commit();
         return true;
    }
    

    
    
}