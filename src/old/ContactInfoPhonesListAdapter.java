package old;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.ivi.R;
import com.model.ivi.Contact;
import com.model.ivi.OptEmail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ContactInfoPhonesListAdapter extends ArrayAdapter<BasicNameValuePair> {
	
	private static final String TAG = ContactInfoPhonesListAdapter.class.getSimpleName();
	ArrayList<BasicNameValuePair> phoneArray;
	Bitmap friendImage;
	Context current;
	
	public ContactInfoPhonesListAdapter(Context context,
			int textViewResourceId, List<BasicNameValuePair> objects) {
		super(context, textViewResourceId, objects);
		phoneArray= (ArrayList<BasicNameValuePair>) objects;
		this.current = context;
		// TODO Auto-generated constructor stub
	}
/**
	public ContactInfoPhonesListAdapter(ArrayList<BasicNameValuePair> l, Activity ct, ViewGroup d) {
		phoneArray= l;
		phoneArray.trimToSize();
		this.current = ct;
	}
**/	
	@Override
	public int getCount() {
		return phoneArray.size(); // total number of elements in the list
	}

	@Override
	public BasicNameValuePair getItem(int i) {
		return phoneArray.get(i); // single item in the list
	}

	@Override
	public long getItemId(int i) {
		return i; // index number
	}

	@Override
	public View getView(int index, View view, final ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.contacts_phone_item, parent,
					false);
		}
        BasicNameValuePair dataModel = phoneArray.get(index);
        ImageView pic = (ImageView) view.findViewById(R.id.imageType);
        if(dataModel.getName() =="cellphone")
        {
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.cellphone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(dataModel.getName() =="homephone"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.homephone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        	
        }else if(dataModel.getName() =="comphone"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.comphone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }else if(dataModel.getName() =="altphone"){
			Drawable img = current.getApplicationContext().getResources()
					.getDrawable(R.drawable.altphone);
			img.setBounds(0, 0, 50, 50);
			pic.setImageDrawable(img);
        }
        TextView value = (TextView) view.findViewById(R.id.contactValueItem);
        value.setText(dataModel.getValue().toString());
		view.setOnClickListener(new View.OnClickListener() {
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
		return view;
	}
}