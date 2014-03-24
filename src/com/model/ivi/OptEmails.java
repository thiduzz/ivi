package com.model.ivi;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;


public class OptEmails extends Vector implements KvmSerializable {
	public int _id;
	public String _email;
	public String _email_st;
	public String _password;
	public String _simoperator;
	public String _cellphone;
	public String _cellphone_st;
	public String _sn_facebook;
	public String _sn_linkedin;
	public String _sn_googleplus;
	public String _sn_skype;
	public String _sn_twitter;
	public String _last_refresh;
	public String _register_date;
	public String _username;
	public static Class CONTACTWEB_CLASS = OptEmails.class;

	public OptEmails(int id, String email, String email_st, String password, String simoperator,
			String cellphone, String cellphone_st, String facebook, String linkedin,
			String googleplus, String skype, String twitter, String refreshuser,
			String userregister, String username) {
	}
	
	public OptEmails() {
	}

    public OptEmails(SoapObject obj)
    {
    }
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public String get_email_st() {
		return _email_st;
	}

	public void set_email_st(String _email_st) {
		this._email_st = _email_st;
	}
	
	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		if (_username.equals("anyType{}")){ this._username = null;} else{
		this._username = _username;}
	}
	
	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public String get_simoperator() {
		return _simoperator;
	}

	public void set_simoperator(String _simoperator) {
		this._simoperator = _simoperator;
	}

	public String get_cellphone() {
		return _cellphone;
	}

	public void set_cellphone(String _cellphone) {
		if (_cellphone.equals("anyType{}")){ this._cellphone = null;} else{
		this._cellphone = _cellphone;}
	}

	public String get_cellphone_st() {
		return _cellphone_st;
	}

	public void set_cellphone_st(String _cellphone_st) {
		if (_cellphone_st.equals("anyType{}")){ this._cellphone_st = null;} else{
		this._cellphone_st = _cellphone_st;}
	}
	
	public String get_sn_facebook() {
		return _sn_facebook;
	}

	public void set_sn_facebook(String _sn_facebook) {
		if (_sn_facebook == null)
		{
			this._sn_facebook = null;
		}else if (_sn_facebook.equals("anyType{}")){ this._sn_facebook = null;} else{
		this._sn_facebook = _sn_facebook;}
	}

	public String get_sn_linkedin() {
		return _sn_linkedin;
	}

	public void set_sn_linkedin(String _sn_linkedin) {
		if (_sn_linkedin.equals("anyType{}")){ this._sn_linkedin = null; }else{
		this._sn_linkedin = _sn_linkedin;}
	}

	public String get_sn_googleplus() {
		return _sn_googleplus;
	}

	public void set_sn_googleplus(String _sn_googleplus) {
		if (_sn_googleplus.equals("anyType{}")){ this._sn_googleplus = null; }else{
		this._sn_googleplus = _sn_googleplus;}
	}

	public String get_sn_skype() {
		return _sn_skype;
	}

	public void set_sn_skype(String _sn_skype) {
		if (_sn_skype.equals("anyType{}")) {this._sn_skype = null;} else{
		this._sn_skype = _sn_skype;}
	}

	public String get_sn_twitter() {
		return _sn_twitter;
	}

	public void set_sn_twitter(String _sn_twitter) {
		if (_sn_twitter.equals("anyType{}")) {
			this._sn_twitter = null; }
		else
		{
		this._sn_twitter = _sn_twitter;
		}
	}

	public String get_last_refresh() {
		return _last_refresh;
	}

	public void set_last_refresh(String _last_refresh) {
		this._last_refresh = _last_refresh;
	}

	public String get_register_date() {
		return _register_date;
	}

	public void set_register_date(String _register_date) {
		this._register_date = _register_date;
	}

	
	
	@Override
	public Object getProperty(int index) {
		Object object = null;
        switch (index)
        {
        case 0:
        {
            object = this._id;
            break;
        }
        case 1:
        {
            object = this._email;
            break;
        }
        case 2:
        {
            object = this._email_st;
            break;
        }
        case 4:
        {
            object = this._password;
            break;
        }
        case 5:
        {
            object = this._cellphone;
            break;
        }
        case 6:
        {
            object = this._cellphone_st;
            break;
        }
        case 7:
        {
            object = this._sn_facebook;
            break;
        }
        case 8:
        {
            object = this._sn_linkedin;
            break;
        }
        case 9:
        {
            object = this._sn_googleplus;
            break;
        }
        case 10:
        {
            object = this._sn_skype;
            break;
        }
        case 11:
        {
            object = this._sn_twitter;
            break;
        }
        case 12:
        {
            object = this._last_refresh;
            break;
        }
        case 13:
        {
            object = this._register_date;
            break;
        }
        case 14:
        {
            object = this._username;
            break;
        }
        }
        return object;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo propertyInfo) {
		

		switch (index)
        {
        case 0:
        {
            propertyInfo.name = "UserId";
            propertyInfo.type = PropertyInfo.INTEGER_CLASS;
            break;
        }
        case 1:
        {
            propertyInfo.name = "UserEmail";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 2:
        {
            propertyInfo.name = "UserEmailStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 3:
        {
            propertyInfo.name = "UserPassword";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 4:
        {
            propertyInfo.name = "UserSimOperator";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 5:
        {
            propertyInfo.name = "UserCellPhone";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 6:
        {
            propertyInfo.name = "UserCellPhoneStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 7:
        {
            propertyInfo.name = "UserFacebook";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 8:
        {
            propertyInfo.name = "UserLinkedin";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 9:
        {
            propertyInfo.name = "UserGoogleplus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 10:
        {
            propertyInfo.name = "UserSkype";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 11:
        {
            propertyInfo.name = "UserTwitter";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 12:
        {
            propertyInfo.name = "UserLastRefresh";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 13:
        {
            propertyInfo.name = "UserRegisterDate";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 14:
        {
            propertyInfo.name = "UserName";
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
            this._id = Integer.parseInt(obj.toString());
            break;
        }
        case 1:
        {
            this._email = obj.toString();
            break;
        }
        case 2:
        {
            this._email_st = obj.toString();
            break;
        }
        case 3:
        {
            this._password = obj.toString();
            break;
        }
        case 4:
        {
            this._simoperator = obj.toString();
            break;
        }
        case 5:
        {
            this._cellphone = obj.toString();
            break;
        }
        case 6:
        {
            this._cellphone_st = obj.toString();
            break;
        }
        case 7:
        {
            this._sn_facebook = obj.toString();
            break;
        }
        case 8:
        {
            this._sn_linkedin = obj.toString();
            break;
        }
        case 9:
        {
            this._sn_googleplus = obj.toString();
            break;
        }
        case 10:
        {
            this._sn_skype = obj.toString();
            break;
        }
        case 11:
        {
            this._sn_twitter = obj.toString();
            break;
        }
        case 12:
        {
            this._last_refresh = obj.toString();
            break;
        }
        case 13:
        {
            this._register_date = obj.toString();
            break;
        }
        case 14:
        {
            this._username = obj.toString();
            break;
        }
        
        }
	}

}


