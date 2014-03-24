package old;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ContactInfoEmailListAdapter extends BaseAdapter {

	private static final String TAG = ContactInfoEmailListAdapter.class.getSimpleName();
	ArrayList<OptEmail> emailArray;
	//List<java.util.Map.Entry<String,String>> phoneArray;
	Bitmap friendImage;
	Activity current;

	public ContactInfoEmailListAdapter(ArrayList<OptEmail> l, Activity ct) {
		emailArray = l;
		this.current = ct;
	}
	
	@Override
	public int getCount() {
		return emailArray.size(); // total number of elements in the list
	}

	@Override
	public Object getItem(int i) {
		return emailArray.get(i); // single item in the list
	}

	@Override
	public long getItemId(int i) {
		return i; // index number
	}

	@Override
	public View getView(int index, View view, final ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.pendingcontacts_item, parent,
					false);
		}

        final OptEmail dataModel = emailArray.get(index);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		/**
		 * TextView textView = (TextView)
		 * view.findViewById(R.id.tv_string_data);
		 * textView.setText(dataModel.getName());
		 * 
		 * Button button = (Button) view.findViewById(R.id.btn_number_data);
		 * button.setText("" + dataModel.getAnInt());
		 * 
		 * textView = (TextView) view.findViewById(R.id.tv_double_data);
		 * textView.setText("" + dataModel.getaDouble());
		 * 
		 * // button click listener // this chunk of code will run, if user
		 * click the button // because, we set the click listener on the button
		 * only
		 * 
		 * button.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View view) { Log.d(TAG, "string: " +
		 *           dataModel.getName()); Log.d(TAG, "int: " +
		 *           dataModel.getAnInt()); Log.d(TAG, "double: " +
		 *           dataModel.getaDouble()); Log.d(TAG, "otherData: " +
		 *           dataModel.getOtherData());
		 * 
		 *           Toast.makeText(parent.getContext(), "button clicked: " +
		 *           dataModel.getAnInt(), Toast.LENGTH_SHORT).show(); } });
		 * 
		 * 
		 *           // if you commented out the above chunk of code and //
		 *           activate this chunk of code // then if user click on any
		 *           view inside the list view (except button) // this chunk of
		 *           code will execute // because we set the click listener on
		 *           the whole view
		 **/

		return view;
	}
}