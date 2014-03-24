package com.db.ivi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.model.ivi.Contact;
import com.model.ivi.ContactWeb;
import com.model.ivi.OptEmail;

import android.app.ProgressDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateFormat;
import android.widget.Toast;


public class AsyncConnectionsSave extends AsyncTask<Contact, Integer, Integer>{
	public ProgressDialog progress;
    private Context context;

	private String SOAP_ACTION_PREFIX = "http://www.ividomain.somee.com/Service1/";
    private String SOAP_ACTION;
    private String METHOD_NAME;
    private static final String NAMESPACE = "http://www.ividomain.somee.com/Service1";
    private static final String URL = "http://www.ividomain.somee.com/Service1.asmx";

	public int resultData;
    /** REMOTE CONNECT  
	private String SOAP_ACTION_PREFIX = "http://192.168.25.11:14798/Service1/";
    private String SOAP_ACTION;
    private String METHOD_NAME;
    private static final String NAMESPACE = "http://192.168.25.11:14798/Service1";
    private static final String URL = "http://192.168.25.11:14798/Service1.asmx";
       **/ 
    public AsyncConnectionsSave(Context context, String method) {
        this.context = context;
        METHOD_NAME  = method;
        SOAP_ACTION =  SOAP_ACTION_PREFIX + METHOD_NAME;
    }

    public AsyncConnectionsSave(String method) {
        METHOD_NAME  = method;
        SOAP_ACTION =  SOAP_ACTION_PREFIX + METHOD_NAME;
    }
    @Override
    protected void onPreExecute() {
        //Cria novo um ProgressDialogo e exibe
        //progress = new ProgressDialog(context);
        //progress.setMessage("Aguarde...");
        //progress.setIndeterminate(false); 
        //progress.setCancelable(false);
        //progress.show();
    }
 
    @Override
    protected Integer doInBackground(Contact... paramss) {
    	Integer code = 0;
		try {
    	if (METHOD_NAME == "updateUser")
    	{
    		code = updateUser(paramss);
    		return code; 
    	}
		return resultData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return code;
    }

    @Override
    protected void onPostExecute(Integer result) {
        myMethod(result);
    }
    
    private Integer myMethod(Integer myValue){
          return myValue; 
        }

    @Override
    protected void onCancelled ()
    {
    }
    
    @Override
    protected void onProgressUpdate(Integer... values) {
    	
    }
    
    
    public int updateUser(Contact... param) throws IOException, XmlPullParserException
    {
    	ContactWeb cw = convertContactToWebContact(param[0]);   	
        SoapObject requestObject = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("c");
        pi.setValue(cw);
        pi.setType(cw.getClass());
        requestObject.addProperty(pi);
        String opt = "";
    	if(param[0]._optemails.size() > 0)
    	{
    		ArrayList<OptEmail> ar = param[0]._optemails;
            int cont = 1;
            for(OptEmail t : ar){  
        		String st = t.get_OptEmail()+"|"+t.get_OptEmailStats();
        		opt += "::" + st;	
            }  

    	}
        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("opt");
        pi2.setValue(opt);
        pi2.setType(PropertyInfo.STRING_CLASS);
        requestObject.addProperty(pi2);
    	// Create soap envelop .use version 1.1 of soap
        final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // add the outgoing object as the request
        envelope.setOutputSoapObject(requestObject);
        envelope.addMapping(NAMESPACE,"ContactWeb",
                ContactWeb.CONTACTWEB_CLASS);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,10000);
        androidHttpTransport.call(SOAP_ACTION, envelope);
        Object result= (Object)envelope.getResponse();
        //resultData = Integer.parseInt(result.getPropertyAsString("updateUserResult").toString());
        publishProgress(Integer.parseInt(result.toString()));
        resultData = Integer.parseInt(result.toString());
		return resultData;
    }

	private ContactWeb convertContactToWebContact(Contact c) {
		ContactWeb cw = new ContactWeb();
		cw._id = c._id;
		cw._email = c._email;
		cw._email_st = c._email_st;
		cw._password = c._password;
		cw._simoperator = c._simoperator;
		cw._cellphone = c._cellphone;
		cw._cellphone_st = c._cellphone_st;
		cw._sn_facebook = c._sn_facebook;
		cw._sn_linkedin = c._sn_linkedin;
		cw._sn_googleplus = c._sn_googleplus;
		cw._sn_skype = c._sn_skype;
		cw._sn_twitter = c._sn_twitter;
		cw._last_refresh = c._last_refresh;
		cw._register_date = c._register_date;
		cw._username = c._username;
		cw._homephone = c._homephone;
		cw._homephone_st = c._homephone_st;
		cw._comphone = c._comphone;
		cw._comphone_st = c._comphone_st;
		cw._altphone = c._altphone;
		cw._altphone_st = c._altphone_st;
		cw._website = c._website;
		cw._website_st = c._website_st;
		cw._sn_facebook_st = c._sn_facebook_st;
		cw._sn_linkedin_st = c._sn_linkedin_st;
		cw._sn_googleplus_st  = c._sn_googleplus_st;
		cw._sn_skype_st  = c._sn_skype_st;
		cw._sn_twitter_st  = c._sn_twitter_st;
		return cw;
	}
}