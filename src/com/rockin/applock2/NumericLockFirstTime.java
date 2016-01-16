package com.rockin.applock2;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NumericLockFirstTime extends Activity implements OnClickListener{
	EditText name,emailid,password,confirmpassword;
	ImageButton submit,backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	CommonClass cc;
	
	ProgressDialog pd;
	
	String sname=null,semailid=null,spass=null,sconfirm=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numericlockfirsttime);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
			e=sp.edit();
			
			pd=new ProgressDialog(this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Loading..");
			pd.setCancelable(false);
			
			cc=new CommonClass(this);
			
			name=(EditText)findViewById(R.id.name);
			emailid=(EditText)findViewById(R.id.emailid);
			password=(EditText)findViewById(R.id.password);
			confirmpassword=(EditText)findViewById(R.id.confirmpassword);
			submit=(ImageButton)findViewById(R.id.submit);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			submit.setOnClickListener(this);
			backallpage.setOnClickListener(this);
		}catch(Exception e){}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent("lockselectorapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(i);
		
		finish();
	}
	
	@Override
	public void onClick(View v) {
		
		try{
			switch(v.getId())
			{
			case R.id.submit:
				getStringValues();
				
				if(checkAllStrings())
				{
					int x=cc.existPassword();
					
					if(x==0)
					{
						if(spass.equals(sconfirm))
						{
							pd.show();
							
							e.putString("formfilled", "true");
							e.putString("name", sname);
							e.putString("emailid", semailid);
							e.putString("numericpassword", spass);
							e.putString("locktype", "numeric");
							e.putString("launched", "true");
							e.commit();
							
							try {
								cc.printToast();
							} catch (IOException e) {
							}
							
							Intent i=new Intent("installedappsapplock");
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
							finish();
						}
						else
						{
							Toast t=Toast.makeText(this, "Passwords Dont Match", Toast.LENGTH_SHORT);
							
							t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
							t.show();
							
							password.setText("");
							confirmpassword.setText("");
						}
					}
				}
				else
				{
					/*Toast t=Toast.makeText(this, "Enter correct Details", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();*/
				}
				
				break;
			case R.id.backallpage:
				Intent i=new Intent("lockselectorapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				startActivity(i);
				
				finish();
				
				break;
			}
		}catch(Exception e){}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	public void getStringValues() throws Exception
	{
		sname=name.getText().toString();
		semailid=emailid.getText().toString();
		spass=password.getText().toString();
		sconfirm=confirmpassword.getText().toString();
	}
	
	public boolean checkAllStrings() throws Exception
	{
		/*if(sname.trim().length()!=0 && semailid.trim().length()!=0)
		{
			if(semailid.contains("@"))
		
				return true;
			
		}*/
		if(sname.trim().length()==0 && (semailid.trim().length()==0 || !semailid.contains("@")))
		{
			Toast t=Toast.makeText(this, "Enter correct details", Toast.LENGTH_SHORT);
			
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.show();
			return false;
		}
		else if(sname.trim().length()==0)
		{
			Toast t=Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT);
			
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.show();
			return false;
		}
		else if(sname.trim().length()!=0 && semailid.trim().length()!=0 && spass.trim().length()!=0 && sconfirm.trim().length()!=0)
		{
			if(semailid.contains("@"))
		
				{
					
					return true;
				}
			else
			{
				Toast t=Toast.makeText(this, "Enter correct email", Toast.LENGTH_SHORT);
				
				t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				t.show();
				return false;
			}
		}
		else
		{
			Toast t=Toast.makeText(this, "Enter correct email", Toast.LENGTH_SHORT);
			
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.show();
			return false;
		}
		
			
	}
	
}
