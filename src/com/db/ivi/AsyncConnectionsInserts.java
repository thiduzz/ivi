package com.db.ivi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.model.ivi.ContactWeb;

import android.app.ProgressDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateFormat;
import android.widget.Toast;


public class AsyncConnectionsInserts extends AsyncTask<String, Integer, Integer>{
	private ProgressDialog progress;
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
    public AsyncConnectionsInserts(Context context, String method) {
        this.context = context;
        METHOD_NAME  = method;
        SOAP_ACTION =  SOAP_ACTION_PREFIX + METHOD_NAME;
    }

    public AsyncConnectionsInserts(String method) {
        METHOD_NAME  = method;
        SOAP_ACTION =  SOAP_ACTION_PREFIX + METHOD_NAME;
    }
    @Override
    protected void onPreExecute() {
        //Cria novo um ProgressDialogo e exibe
        //progress = new ProgressDialog(context);
        //progress.setMessage("Aguarde...");
        //progress.show();
    }
 
    @Override
    protected Integer doInBackground(String... paramss) {
    	if (METHOD_NAME == "registerUser")
    	{
    		Integer code = webServiceMethodRegisterUser(paramss);
    		return code; 
    	}
    	if (METHOD_NAME == "recoverPassword")
    	{
    		Integer code = webServiceMethodRecoverPassword(paramss);
    		return code; 
    	}
		return resultData;
    	
    }

    @Override
    protected void onPostExecute(Integer result) {
        myMethod(result);
    }
    
    private Integer myMethod(Integer myValue){
        //handle value 
          return myValue; 
        }

    @Override
    protected void onCancelled ()
    {
       // progress.dismiss();
    }
    
    @Override
    protected void onProgressUpdate(Integer... values) {
    	
    }
    
    public Integer webServiceMethodRegisterUser(String... paramss)
    {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        
            /**
            c._email = paramss[0];
            c._password = paramss[1];
            c._cellphonesim = paramss[2];
            c._simoperator = paramss[3];
            PropertyInfo pi = new PropertyInfo();
            pi.setName("contact");
            pi.setValue(c);
            pi.setType(c.getClass());
            request.addProperty(pi);
            **/

            request.addProperty("useremail", paramss[0]);
            request.addProperty("userpass", paramss[1]);
            request.addProperty("usercellphone", paramss[2]);
            request.addProperty("usercellphoneoper", paramss[3]);
            //request.addProperty("useremail", paramss[0]);
            //request.addProperty("userpass", paramss[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 10000); 
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject result=(SoapObject)envelope.bodyIn;
            resultData = Integer.parseInt(result.getPropertyAsString("registerUserResult").toString());
            publishProgress(resultData);
           
            return resultData;
        } catch (Exception e) {
    		return resultData;
    		
        	
        }
    }
    
    public Integer webServiceMethodRecoverPassword(String... paramss)
    {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("useremail", paramss[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,10000);           
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject result=(SoapObject)envelope.bodyIn;
            resultData = Integer.parseInt(result.getPropertyAsString("recoverPasswordResult").toString());
            publishProgress(resultData);
            return resultData;
        } catch (Exception e) {
    		return -1;
        }
    }

    
}