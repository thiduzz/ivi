package com.main.ivi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;

import com.db.ivi.AsyncConnectionsInserts;
import com.db.ivi.AsyncConnectionsSelects;
import com.db.ivi.Prefs;
import com.example.ivi.R;
import com.model.ivi.Contact;

import old.XMLMachine;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

public class MainActivity extends Activity {

	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
	public final Pattern PASSWORD_PATTERN = Pattern.compile("^.{4,8}$");
	public static Menu _menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Prefs.clearMyPreferences(getApplicationContext());
		if (Prefs.getMyUserPref(getApplicationContext()) != null) {
			Intent startNewActivityOpen = new Intent(this, LoggedActivity.class);
			startActivityForResult(startNewActivityOpen, 0);
			this.finish();
		} else {

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_main);
			EditText t1 = (EditText) findViewById(R.id.EditTextEmail);
			EditText textPass = (EditText) findViewById(R.id.EditTextPassword);
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(t1.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(textPass.getWindowToken(), 0);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		_menu = menu;
		return true;
	}

	public void alterToRegister(View v) {

		Button b1 = (Button) findViewById(R.id.button1);
		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);
		if (b1.getText().toString() == getResources().getString(R.string.Login)) {
			t2.setVisibility(TextView.INVISIBLE);
			b1.setText(getResources().getString((R.string.Register)));
			t1.setText(getResources().getString(R.string.LoginText));
		} else {
			t2.setVisibility(TextView.VISIBLE);
			b1.setText(getResources().getString((R.string.Login)));
			t1.setText(getResources().getString(R.string.RegisterText));
		}
	}

	public void forgotMyPass(View v) {
		if (isNetworkAvailable() == false) {
			// Toast t = Toast.makeText(v.getContext(),
			// v.getResources().getString(R.string.NetworkUnavailableMsg),
			// 3000);
			showAlert(R.string.NetworkUnavailableTitle,
					R.string.NetworkUnavailableMsg);
		} else {

			AlertDialog.Builder editalert = new AlertDialog.Builder(this);
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setGravity(Gravity.CENTER_HORIZONTAL);
			layout.setPadding(10, 30, 10, 30);
			EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			input.setId(R.id.my_edit_text_1);
			layout.addView(input);
			editalert.setTitle(getResources().getString(
					R.string.ForgotPasswordDialogTitle));
			editalert.setMessage(getResources().getString(
					R.string.ForgotPasswordDialogMessage));
			editalert.setView(layout);
			editalert.setPositiveButton(
					getResources().getString(
							R.string.ForgotPasswordDialogRecoverBt),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
			editalert.setNegativeButton(
					getResources().getString(
							R.string.ForgotPasswordDialogCancelBt),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.dismiss();
						}
					});
			AlertDialog dialog = editalert.create();
			dialog.show();
			Button pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
			pos.setOnClickListener(new CustomListener(dialog));
		}
	}

	public void defineLoginClick(View v) throws ParserConfigurationException,
			IllegalArgumentException, IllegalStateException, IOException,
			InterruptedException, ExecutionException {
		Button b1 = (Button) findViewById(R.id.button1);
		EditText t1 = (EditText) findViewById(R.id.EditTextEmail);
		EditText t2 = (EditText) findViewById(R.id.EditTextPassword);
		XMLMachine xml = new XMLMachine();
		t1.setError(null);
		boolean error = false;
		if (b1.getText().toString() == getResources().getString(
				R.string.Register)) {

			// Validação padrão e-mail
			if (!checkEmail(t1.getText().toString())) {
				// variavel que impede a geração do xml
				t1.setError(getResources().getString(
						R.string.EmailErrorMessage1));
				t1.requestFocus();
				error = true;
			}
			AsyncConnectionsInserts thread = new AsyncConnectionsInserts(this,
					"registerUser");
			if (isNetworkAvailable() == true) {
				TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext()
						.getSystemService(Context.TELEPHONY_SERVICE);
				thread.execute(t1.getText().toString(),
						t2.getText().toString(),
						telephonyManager.getLine1Number(),
						telephonyManager.getSimOperatorName());

				if (thread.get() == 1) {

				} else {
					if (thread.get() == 2627) {
						if (t1.getError() == null) {
							t1.setError(getResources().getString(
									R.string.EmailErrorMessage2));
							t1.requestFocus();
						} else {
							t1.setError(t1.getError().toString()
									+ "\n"
									+ getResources().getString(
											R.string.EmailErrorMessage2));
							t1.requestFocus();
						}
					}
					error = true;
				}
				// Validação padrão senha
				if (!checkPassword(t2.getText().toString())) {
					// variavel que impede a geração do xml
					t2.setError(getResources().getString(
							R.string.PasswordErrorMessage1));
					if (t1.getError() == null) {
						t2.requestFocus();
					}
					error = true;
				}

			} else {
				// Toast t = Toast.makeText(v.getContext(),
				// v.getResources().getString(R.string.NetworkUnavailableMsg),
				// 3000);
				showAlert(R.string.WebServiceErrorTitle1,
						R.string.WebServiceErrorMsg1);
				error = true;
			}
			if (error == false) {
				thread.cancel(true);
				AsyncConnectionsSelects thread2 = new AsyncConnectionsSelects(this,
						"loginUser");
				thread2.execute(t1.getText().toString(), t2.getText().toString());
				Prefs.setMyUserPref(getApplicationContext(), thread2.get());
				Intent startNewActivityOpen = new Intent(this,
						LoggedActivity.class);
				startActivityForResult(startNewActivityOpen, 0);
				this.finish();
			}else{
			}
		} else {

			if (!checkEmail(t1.getText().toString())) {
				// variavel que impede a geração do xml
				t1.setError(getResources().getString(
						R.string.EmailErrorMessage1));
				t1.requestFocus();
				error = true;
			}
			if (!checkPassword(t2.getText().toString())) {
				// variavel que impede a geração do xml
				t2.setError(getResources().getString(
						R.string.PasswordErrorMessage1));
				if (t1.getError() == null) {
					t2.requestFocus();
				}
				error = true;
			}
			if (isNetworkAvailable() == false) {
				// Toast t = Toast.makeText(v.getContext(),
				// v.getResources().getString(R.string.NetworkUnavailableMsg),
				// 3000);
				showAlert(R.string.WebServiceErrorTitle1,
						R.string.WebServiceErrorMsg1);
				error = true;
			}
		}
		if (error == false
				&& b1.getText().toString() == getResources().getString(
						R.string.Login)) {
			AsyncConnectionsSelects thread = new AsyncConnectionsSelects(this,
					"loginUser");
			thread.execute(t1.getText().toString(), t2.getText().toString());
			if (thread.get() == null) {
				showAlert(R.string.LoginErrorTitle1, R.string.LoginErrorMsg1);
				t1.requestFocus();
				t2.setText("");
			} else {
				// TODO SUBSTITUIR XML POR SHARED PREFERENCES
				Prefs.setMyUserPref(getApplicationContext(),
						(Contact) thread.get());
				Intent startNewActivityOpen = new Intent(this,
						LoggedActivity.class);
				startActivityForResult(startNewActivityOpen, 0);
				this.finish();
			}
		}
	}

	public void showAlert(int declared_title, int declared_message) {
		new AlertDialog.Builder(this)
				.setTitle(getResources().getString(declared_title))
				.setMessage(getResources().getString(declared_message))
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// continua ação
					}
				}).show();
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	private boolean checkPassword(String password) {
		return PASSWORD_PATTERN.matcher(password).matches();
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

}

// CLASSE DIFERENTE - CLICK DO DIALOG DE ESQUECI MINHA SENHA

class CustomListener implements View.OnClickListener {
	private final Dialog dialog;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	public CustomListener(Dialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void onClick(View v) {

		AlertDialog f = (AlertDialog) dialog;
		EditText e1 = (EditText) f.findViewById(R.id.my_edit_text_1);
		e1.setError("");
		if (!checkEmail(e1.getText().toString())) {
			e1.setError(v.getResources().getString(R.string.EmailErrorMessage1));
		} else {
			AsyncConnectionsInserts thread = new AsyncConnectionsInserts(
					"recoverPassword");
			thread.execute(e1.getText().toString());
			try {
				switch (thread.get()) {
				case -1:
					Toast t1 = Toast.makeText(v.getContext(), v.getResources()
							.getString(R.string.UnexpectedMsg), 3000);
					t1.show();
					f.dismiss();
					break;
				case 0:
					e1.setError(v.getResources().getString(
							R.string.EmailErrorMessage3));
					break;
				case 1:
					Toast t2 = Toast.makeText(v.getContext(), v.getResources()
							.getString(R.string.RecoverPassMsg), 3000);
					t2.show();
					f.dismiss();
					break;
				default:
					Toast t3 = Toast.makeText(v.getContext(), v.getResources()
							.getString(R.string.UnexpectedMsg), 3000);
					t3.show();
					f.dismiss();
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
}
