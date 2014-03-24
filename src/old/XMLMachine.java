package old;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.example.ivi.R;
import com.example.ivi.R.string;
import com.model.ivi.Contact;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;

public class XMLMachine {
/**
	public Contact readXML(EditText t1, View v, Context ac)
			throws XmlPullParserException, IOException {
		int loc = t1.getText().toString().indexOf("@");
		String user = t1.getText().toString().substring(0, loc);
		StringBuffer fileContent = new StringBuffer("");
		File xmlfile = new File(v.getContext().getDir("com.ivi.application",
				ac.MODE_PRIVATE)
				+ user + ".xml");
		FileInputStream fis = new FileInputStream(xmlfile);
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(fis, null);
		parser.nextTag();
		Contact c = new Contact();
		while (parser.nextTag() == XmlPullParser.START_TAG) {
			if (parser.getName().equals("Username")) {
				c._username = parser.nextText();
			} else if (parser.getName().equals(ac.getResources().getString(R.string.CellPhoneOperator))) {
				c._simoperator = parser.nextText();
			} else if (parser.getName().equals(ac.getResources().getString(R.string.CellSimSerial))) {
				c._cellphonesim = parser.nextText();
			} else if (parser.getName().equals("Email")) {
				c._email = parser.nextText();
			} else if (parser.getName().equals("Facebook")) {
				c._sn_facebook = parser.nextText();
			} else if (parser.getName().equals("Linkedin")) {
				c._sn_linkedin = parser.nextText();
			} else if (parser.getName().equals("Orkut")) {
				c._sn_orkut = parser.nextText();
			} else if (parser.getName().equals("Google+")) {
				c._sn_googleplus = parser.nextText();
			} else if (parser.getName().equals("Twitter")) {
				c._sn_twitter = parser.nextText();
			} else if (parser.getName().equals("LastRefresh")) {
				c._last_refresh = parser.nextText();
			}
		}
		return c;
	}
	
	public Contact loadStartXML(Application application)
			throws XmlPullParserException, IOException {
		StringBuffer fileContent = new StringBuffer("");
		File xmlfile = new File(application.getDir("com.ivi.application",application.MODE_PRIVATE).getAbsolutePath());
		FileInputStream fis = new FileInputStream(xmlfile);
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(fis, null);
		parser.nextTag();
		Contact c = new Contact();
		while (parser.nextTag() == XmlPullParser.START_TAG) {
			if (parser.getName().equals("Username")) {
				c._username = parser.nextText();
			} else if (parser.getName().equals(application.getResources().getString(R.string.CellPhoneOperator))) {
				c._simoperator = parser.nextText();
			} else if (parser.getName().equals(application.getResources().getString(R.string.CellSimSerial))) {
				c._cellphonesim = parser.nextText();
			} else if (parser.getName().equals("Email")) {
				c._email = parser.nextText();
			} else if (parser.getName().equals("Facebook")) {
				c._sn_facebook = parser.nextText();
			} else if (parser.getName().equals("Linkedin")) {
				c._sn_linkedin = parser.nextText();
			} else if (parser.getName().equals("Orkut")) {
				c._sn_orkut = parser.nextText();
			} else if (parser.getName().equals("Google+")) {
				c._sn_googleplus = parser.nextText();
			} else if (parser.getName().equals("Twitter")) {
				c._sn_twitter = parser.nextText();
			} else if (parser.getName().equals("LastRefresh")) {
				c._last_refresh = parser.nextText();
			}
		}
		return c;
	}
**/
}
