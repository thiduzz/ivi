package com.db.ivi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.model.ivi.Contact;
import com.model.ivi.OptEmail;

import android.app.ProgressDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Xml;
import android.widget.Toast;


public class AsyncConnectionsSelects extends AsyncTask<String, Contact, Contact>{
	private ProgressDialog progress;
    private Context context;

	private String SOAP_ACTION_PREFIX = "http://www.ividomain.somee.com/Service1/";
    private String SOAP_ACTION;
    private String METHOD_NAME;
    private static final String NAMESPACE = "http://www.ividomain.somee.com/Service1";
    private static final String URL = "http://www.ividomain.somee.com/Service1.asmx";

	public Contact c;
    /** REMOTE CONNECT  
	private String SOAP_ACTION_PREFIX = "http://192.168.25.11:14798/Service1/";
    private String SOAP_ACTION;
    private String METHOD_NAME;
    private static final String NAMESPACE = "http://192.168.25.11:14798/Service1";
    private static final String URL = "http://192.168.25.11:14798/Service1.asmx";
       **/ 
    public AsyncConnectionsSelects(Context context, String method) {
        this.context = context;
        METHOD_NAME  = method;
        SOAP_ACTION =  SOAP_ACTION_PREFIX + METHOD_NAME;
    }

    @Override
    protected void onPreExecute() {
        this.c = new Contact();
    }
 
    @Override
    protected Contact doInBackground(String... paramss) {
    	Contact code = new Contact();
    	if (METHOD_NAME == "loginUser")
    	{
    		
			try {
				code = webServiceMethodLoginUser(paramss);
				return code;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return c;	
    }

    @Override
    protected void onPostExecute(Contact result) {
        myMethod(result);
    }
    
    private Contact myMethod(Contact myContact){
        //handle value 
          return myContact; 
        }

    @Override
    protected void onCancelled ()
    {
       // progress.dismiss();
    }
    
    @Override
    protected void onProgressUpdate(Contact... values) {
    	
    }

    public Contact webServiceMethodLoginUser(String... paramss) throws IOException, XmlPullParserException
    {
    	try{
             SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
             request.addProperty("useremail", paramss[0]);
             request.addProperty("userpass", paramss[1]);
             SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
             envelope.dotNet=true;
             envelope.setOutputSoapObject(request);
             HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,10000);
             androidHttpTransport.call(SOAP_ACTION, envelope);
             SoapObject result=(SoapObject)envelope.getResponse();
             if(result != null)
             {
             c.set_id(Integer.parseInt(result.getProperty("UserId").toString()));
             c.set_email(result.getProperty("UserEmail").toString());
             c.set_email_st(result.getProperty("UserEmailStatus").toString());
             c.set_username(result.getProperty("UserName").toString());
             c.set_password(result.getProperty("UserPassword").toString());
             c.set_simoperator(result.getProperty("UserSimOperator").toString());
             c.set_cellphone(result.getProperty("UserCellPhone").toString());
             c.set_cellphone_st(result.getProperty("UserCellPhoneStatus").toString());
             c.set_sn_facebook(result.getProperty("UserFacebook").toString());
             c.set_sn_linkedin(result.getProperty("UserLinkedin").toString());
             c.set_sn_googleplus(result.getProperty("UserGoogleplus").toString());
             c.set_sn_skype(result.getProperty("UserSkype").toString());
             c.set_sn_twitter(result.getProperty("UserTwitter").toString());
             c.set_last_refresh(result.getProperty("UserLastRefresh").toString().substring(0, 10));
             c.set_register_date(result.getProperty("UserRegisterDate").toString().substring(0, 10));
             c.set_homephone(result.getPropertyAsString("UserHomephone").toString());      
             c.set_homephone_st(result.getPropertyAsString("UserHomephoneStatus").toString());
             c.set_comphone(result.getPropertyAsString("UserComerphone").toString());      
             c.set_comphone_st(result.getPropertyAsString("UserComerphoneStatus").toString());        
             c.set_altphone(result.getPropertyAsString("UserAltphone").toString());      
             c.set_altphone_st(result.getPropertyAsString("UserAltphoneStatus").toString());        
             c.set_website(result.getPropertyAsString("UserWebsite").toString());      
             c.set_website_st(result.getPropertyAsString("UserWebsiteStatus").toString());
             c.set_sn_facebook_st(result.getProperty("UserFacebookStatus").toString());
             c.set_sn_linkedin_st(result.getProperty("UserLinkedinStatus").toString());
             c.set_sn_googleplus_st(result.getProperty("UserGoogleplusStatus").toString());
             c.set_sn_skype_st(result.getProperty("UserSkypeStatus").toString());
             c.set_sn_twitter_st(result.getProperty("UserTwitterStatus").toString());
             ArrayList<OptEmail> opt = new ArrayList<OptEmail>();
             if(result.hasProperty("UserOptEmail"))
             {
             SoapObject obj2 =(SoapObject) result.getProperty(15);
             for(int i=0;i<obj2.getPropertyCount();i++)
             {	
            	 SoapObject obj3 = (SoapObject) obj2.getProperty(i);
                	 String email = (String) obj3.getProperty("OptUserEmail").toString();
                	 String emailP = (String) obj3.getProperty("OptUserEmailStatus").toString();
                	 OptEmail o = new OptEmail();
                	 o.set_OptEmail(email);
                	 o.set_OptEmailStats(emailP);
                	 opt.add(o);

             }
             c.set_opt_email((ArrayList<OptEmail>)opt);
             }
             publishProgress(c);
             return c;
             }else{
            	 c = null;
                 publishProgress(c);
            	 return c;
             }
             }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return c;
    	}
    	
    
    }

   
    
}