package com.model.ivi;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Contact implements Serializable {
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
	public ArrayList<OptEmail> _optemails;

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
	
	public String _image_binary;

	public Contact(int id, String email, String email_st, String password,
			String simoperator, String cellphone, String cellphone_st,
			String facebook, String linkedin, String googleplus, String skype,
			String twitter, String refreshuser, String userregister,
			String username, ArrayList<OptEmail> optemails, String homephone,
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
		_optemails = optemails;
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

	public Contact(int id, String email, String simoperator, String cellphone, String facebook, 
			String linkedin, String googleplus, String skype,
			String twitter, String username, ArrayList<OptEmail> optemails, String homephone,
			String comphone, String altphone,String website, String img) {
		_id = id;
		_email = email;
		_simoperator = simoperator;
		_cellphone = cellphone;
		_sn_facebook = facebook;
		_sn_linkedin = linkedin;
		_sn_googleplus = googleplus;
		_sn_skype = skype;
		_sn_twitter = twitter;
		_username = username;
		_optemails = optemails;
		_homephone = homephone;
		_comphone = comphone;
		_altphone = altphone;
		_website = website;
		_image_binary = img;
	}
	
	public String get_imagebinary()
	{
		return _image_binary;
	}
	
	public void set_imagebinary(String img) {
		this._image_binary = img;
	}
	
	public ArrayList<OptEmail> get_optemails() {
		return _optemails;
	}

	public void set_optemails(ArrayList<OptEmail> _optemails) {
		this._optemails = _optemails;
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

	public Contact() {
		ArrayList<OptEmail> _optemails = new ArrayList<OptEmail>();
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

	public void set_opt_email(ArrayList<OptEmail> vector) {
		_optemails = vector;
	}

	public ArrayList<OptEmail> get_opt_email() {
		return _optemails;
	}

	public void addOptEmail(String email) {
		_optemails.add(new OptEmail(email, "pub"));
	}

	public OptEmail getOptEmail(String email) {
		for (OptEmail s : _optemails) {
			if (s.get_OptEmail().equals(email)) {
				return s;
			}
		}
		return null;
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
		if (_sn_linkedin == null) {
			this._sn_linkedin = null;
		}else if (_sn_linkedin.equals("anyType{}")) {
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

}
