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


public class ContactWeb implements KvmSerializable {
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

	public String _homephone;
	public String _homephone_st;
	public String _comphone;
	public String _comphone_st;
	public String _altphone;
	public String _altphone_st;
	public String _website;
	public String _website_st;
	public String _sn_facebook_st;
	public String _sn_linkedin_st;
	public String _sn_googleplus_st;
	public String _sn_skype_st;
	public String _sn_twitter_st;
	public static Class CONTACTWEB_CLASS = ContactWeb.class;

	public ContactWeb(int id, String email, String email_st, String password,
			String simoperator, String cellphone, String cellphone_st,
			String facebook, String linkedin, String googleplus, String skype,
			String twitter, String refreshuser, String userregister,
			String username, String homephone,
			String homephonest, String comphone, String comphonest,
			String altphone, String altphonest, String website,
			String websitest, String facebookst, String linkedinst,
			String googleplusst, String skypest, String twitterst) {
		_id = id;
		_email = email;
		_email_st = email_st;
		_password = password;
		_simoperator = simoperator;
		_cellphone = cellphone;
		_cellphone_st = cellphone_st;
		_sn_facebook = facebook;
		_sn_linkedin = linkedin;
		_sn_googleplus = googleplus;
		_sn_skype = skype;
		_sn_twitter = twitter;
		_last_refresh = refreshuser;
		_register_date = userregister;
		_username = username;
		_homephone = homephone;
		_homephone_st = homephonest;
		_comphone = comphone;
		_comphone_st = comphonest;
		_altphone = altphone;
		_altphone_st = altphonest;
		_website = website;
		_website_st = websitest;
		_sn_facebook_st = facebookst;
		_sn_linkedin_st = linkedinst;
		_sn_googleplus_st = googleplusst;
		_sn_skype_st = skypest;
		_sn_twitter_st = twitterst;
	}
	public ContactWeb() {
	}

    public ContactWeb(SoapObject obj)
    {
    }
	
    public String get_homephone() {
		return _homephone;
	}

	public void set_homephone(String _homephone) {
		if (_homephone.equals("anyType{}")) {
			this._homephone = null;
		} else {
			this._homephone = _homephone;
		}
	}

	public String get_homephone_st() {
		return _homephone_st;
	}

	public void set_homephone_st(String _homephone_st) {
		if (_homephone_st.equals("anyType{}")) {
			this._homephone_st = null;
		} else {
			this._homephone_st = _homephone_st;
		}
	}

	public String get_comphone() {
		return _comphone;
	}

	public void set_comphone(String _comphone) {
		if (_comphone.equals("anyType{}")) {
			this._comphone = null;
		} else {
			this._comphone = _comphone;
		}
	}

	public String get_comphone_st() {
		return _comphone_st;
	}

	public void set_comphone_st(String _comphone_st) {
		if (_comphone_st.equals("anyType{}")) {
			this._comphone_st = null;
		} else {
			this._comphone_st = _comphone_st;
		}
	}

	public String get_altphone() {
		return _altphone;
	}

	public void set_altphone(String _altphone) {
		if (_altphone.equals("anyType{}")) {
			this._altphone = null;
		} else {
			this._altphone = _altphone;
		}
	}

	public String get_altphone_st() {
		return _altphone_st;
	}

	public void set_altphone_st(String _altphone_st) {
		if (_altphone_st.equals("anyType{}")) {
			this._altphone_st = null;
		} else {
			this._altphone_st = _altphone_st;
		}
	}

	public String get_website() {
		return _website;
	}

	public void set_website(String _website) {
		if (_website.equals("anyType{}")) {
			this._website = null;
		} else {
			this._website = _website;
		}
	}

	public String get_website_st() {
		return _website_st;
	}

	public void set_website_st(String _website_st) {
		if (_website_st.equals("anyType{}")) {
			this._website_st = null;
		} else {
			this._website_st = _website_st;
		}
	}

	public String get_sn_facebook_st() {
		return _sn_facebook_st;
	}

	public void set_sn_facebook_st(String _sn_facebook_st) {
		if (_sn_facebook_st.equals("anyType{}")) {
			this._sn_facebook_st = null;
		} else {
			this._sn_facebook_st = _sn_facebook_st;
		}
	}

	public String get_sn_linkedin_st() {
		return _sn_linkedin_st;
	}

	public void set_sn_linkedin_st(String _sn_linkedin_st) {
		if (_sn_linkedin_st.equals("anyType{}")) {
			this._sn_linkedin_st = null;
		} else {
			this._sn_linkedin_st = _sn_linkedin_st;
		}
	}

	public String get_sn_googleplus_st() {
		return _sn_googleplus_st;
	}

	public void set_sn_googleplus_st(String _sn_googleplus_st) {
		if (_sn_googleplus_st.equals("anyType{}")) {
			this._sn_googleplus_st = null;
		} else {
			this._sn_googleplus_st = _sn_googleplus_st;
		}
	}

	public String get_sn_skype_st() {
		return _sn_skype_st;
	}

	public void set_sn_skype_st(String _sn_skype_st) {
		if (_sn_skype_st.equals("anyType{}")) {
			this._sn_skype_st = null;
		} else {
			this._sn_skype_st = _sn_skype_st;
		}
	}

	public String get_sn_twitter_st() {
		return _sn_twitter_st;
	}

	public void set_sn_twitter_st(String _sn_twitter_st) {
		if (_sn_twitter_st.equals("anyType{}")) {
			this._sn_twitter_st = null;
		} else {
			this._sn_twitter_st = _sn_twitter_st;
		}
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
		if (_email_st.equals("anyType{}")) {
			this._email_st = null;
		} else {
			this._email_st = _email_st;
		}
	}

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		if (_username.equals("anyType{}")) {
			this._username = null;
		} else {
			this._username = _username;
		}
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
		if (_simoperator.equals("anyType{}")) {
			this._simoperator = null;
		} else {
			this._simoperator = _simoperator;
		}
	}

	public String get_cellphone() {
		return _cellphone;
	}

	public void set_cellphone(String _cellphone) {
		if (_cellphone.equals("anyType{}")) {
			this._cellphone = null;
		} else {
			this._cellphone = _cellphone;
		}
	}

	public String get_cellphone_st() {
		return _cellphone_st;
	}

	public void set_cellphone_st(String _cellphone_st) {
		if (_cellphone_st.equals("anyType{}")) {
			this._cellphone_st = null;
		} else {
			this._cellphone_st = _cellphone_st;
		}
	}

	public String get_sn_facebook() {
		return _sn_facebook;
	}

	public void set_sn_facebook(String _sn_facebook) {
		if (_sn_facebook == null) {
			this._sn_facebook = null;
		} else if (_sn_facebook.equals("anyType{}")) {
			this._sn_facebook = null;
		} else {
			this._sn_facebook = _sn_facebook;
		}
	}

	public String get_sn_linkedin() {
		return _sn_linkedin;
	}

	public void set_sn_linkedin(String _sn_linkedin) {
		if (_sn_linkedin.equals("anyType{}")) {
			this._sn_linkedin = null;
		} else {
			this._sn_linkedin = _sn_linkedin;
		}
	}

	public String get_sn_googleplus() {
		return _sn_googleplus;
	}

	public void set_sn_googleplus(String _sn_googleplus) {
		if (_sn_googleplus.equals("anyType{}")) {
			this._sn_googleplus = null;
		} else {
			this._sn_googleplus = _sn_googleplus;
		}
	}

	public String get_sn_skype() {
		return _sn_skype;
	}

	public void set_sn_skype(String _sn_skype) {
		if (_sn_skype.equals("anyType{}")) {
			this._sn_skype = null;
		} else {
			this._sn_skype = _sn_skype;
		}
	}

	public String get_sn_twitter() {
		return _sn_twitter;
	}

	public void set_sn_twitter(String _sn_twitter) {
		if (_sn_twitter.equals("anyType{}")) {
			this._sn_twitter = null;
		} else {
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
        case 3:
        {
            object = this._password;
            break;
        }
        case 4:
        {
            object = this._simoperator;
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
        case 15:
        {
            object = this._homephone;
            break;
        }
        case 16:
        {
            object = this._homephone_st;
            break;
        }
        case 17:
        {
            object = this._comphone;
            break;
        }
        case 18:
        {
            object = this._comphone_st;
            break;
        }
        case 19:
        {
            object = this._altphone;
            break;
        }
        case 20:
        {
            object = this._altphone_st;
            break;
        }
        case 21:
        {
            object = this._website;
            break;
        }
        case 22:
        {
            object = this._website_st;
            break;
        }
        case 23:
        {
            object = this._sn_facebook_st;
            break;
        }
        case 24:
        {
            object = this._sn_linkedin_st;
            break;
        }
        case 25:
        {
            object = this._sn_googleplus_st;
            break;
        }
        case 26:
        {
            object = this._sn_skype_st;
            break;
        }
        case 27:
        {
            object = this._sn_twitter_st;
            break;
        }
        }
        return object;
	}
	
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 28;
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
        case 15:
        {
            propertyInfo.name = "UserHomephone";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 16:
        {
            propertyInfo.name = "UserHomephoneStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 17:
        {
            propertyInfo.name = "UserComerphone";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 18:
        {
            propertyInfo.name = "UserComerphoneStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 19:
        {
            propertyInfo.name = "UserAltphone";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 20:
        {
            propertyInfo.name = "UserAltphoneStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 21:
        {
            propertyInfo.name = "UserWebsite";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 22:
        {
            propertyInfo.name = "UserWebsiteStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 23:
        {
            propertyInfo.name = "UserFacebookStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 24:
        {
            propertyInfo.name = "UserLinkedinStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 25:
        {
            propertyInfo.name = "UserGoogleplusStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 26:
        {
            propertyInfo.name = "UserSkypeStatus";
            propertyInfo.type = PropertyInfo.STRING_CLASS;
            break;
        }
        case 27:
        {
            propertyInfo.name = "UserTwitterStatus";
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
        case 15:
        {
            this._homephone = obj.toString();
            break;
        }
        case 16:
        {
            this._homephone_st = obj.toString();
            break;
        }
        case 17:
        {
            this._comphone = obj.toString();
            break;
        }
        case 18:
        {
            this._comphone_st = obj.toString();
            break;
        }
        case 19:
        {
            this._altphone = obj.toString();
            break;
        }
        case 20:
        {
            this._altphone_st = obj.toString();
            break;
        }
        case 21:
        {
            this._website = obj.toString();
            break;
        }
        case 22:
        {
            this._website_st = obj.toString();
            break;
        }
        case 23:
        {
            this._sn_facebook_st = obj.toString();
            break;
        }
        case 24:
        {
            this._sn_linkedin_st = obj.toString();
            break;
        }
        case 25:
        {
            this._sn_googleplus_st = obj.toString();
            break;
        }
        case 26:
        {
            this._sn_skype_st = obj.toString();
            break;
        }
        case 27:
        {
            this._sn_twitter_st= obj.toString();
            break;
        }
        }
	}

}


