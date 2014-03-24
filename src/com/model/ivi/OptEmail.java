package com.model.ivi;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

////////////////////////////CLASSE OPTMAIL

public class OptEmail implements KvmSerializable
{
String _email;
String _email_st;
public static Class OPTEMAIL_CLASS = OptEmail.class;

public OptEmail(String email, String email_st)
{
_email = email;
_email_st = email_st;
}
public OptEmail()
{

}

public String get_OptEmail() {
return _email;
}

public void set_OptEmail(String _email) {
this._email = _email;
}

public String get_OptEmailStats() {
return _email_st;
}

public void set_OptEmailStats(String _email_st) {
this._email_st = _email_st;
}
@Override
public Object getProperty(int index) {
	Object object = null;
    switch (index)
    {
    case 0:
    {
        object = this._email;
        break;
    }
    case 1:
    {
        object = this._email_st;
        break;
    }    
    }
    return object;
}
@Override
public int getPropertyCount() {
	// TODO Auto-generated method stub
	return 2;
}
@Override
public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo propertyInfo) {
	switch (index)
    {
    case 0:
    {
        propertyInfo.name = "Email";
        propertyInfo.type = PropertyInfo.STRING_CLASS;
        break;
    }
    case 1:
    {
        propertyInfo.name = "EmailStatus";
        propertyInfo.type = PropertyInfo.STRING_CLASS;
        break;
    }
    }
}
@Override
public void setProperty(int index, Object obj) {
	switch (index)
    {
    case 0:
    {
        this._email = obj.toString();
        break;
    }
    case 1:
    {
        this._email_st = obj.toString();
        break;
    }
	
    }
    }
}