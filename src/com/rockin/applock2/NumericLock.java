package com.rockin.applock2;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

public class NumericLock extends Activity implements OnClickListener{
	
	EditText password;
	ImageButton submit,backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	CommonClass cc;
	
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numericlock);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=getSharedPreferences("com.rockin.applock2", Context.MODE_PRIVATE);
			e=sp.edit();
			
			pd=new ProgressDialog(this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Loading..");
			pd.setCancelable(false);
			
			cc=new CommonClass(this);
			
			password=(EditText)findViewById(R.id.password);
			submit=(ImageButton)findViewById(R.id.submit);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			Bundle b1=getIntent().getExtras();
			
			if(b1!=null)
				backallpage.setEnabled(false);
			
			backallpage.setOnClickListener(this);
			submit.setOnClickListener(this);
		}catch(Exception e){}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onClick(View v) {
		
		try{
			switch(v.getId())
			{
			case R.id.submit:
				
				cc.deleteFiles();
				
				int x=cc.existPassword();
				String s=password.getText().toString();
				
				if(x==0)
				{
					pd.show();
					
					e.putString("locktype", "numeric");
					e.putString("launched", "true");
					e.commit();
					
					try {
						cc.printToast();
					} catch (IOException e) {
					}
					
					e.putString("numericpassword", s);
					e.commit();
					
					Intent i=new Intent("installedappsapplock");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else
				{
					String temp=sp.getString("numericpassword", null);
					
					if(temp.equals(s))
					{
						Bundle b=getIntent().getExtras();
						
						Toast t=Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
						
						t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						t.show();
						
						if(b==null)
						{
							pd.show();
							
							Intent i=new Intent("installedappsapplock");
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
							finish();
						}
						else
						{
							finish();
						}
						
					}
					else
					{
						Toast t=Toast.makeText(this, "Failed", Toast.LENGTH_SHORT);
						
						t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						t.show();
					}
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
	public void onBackPressed() {
		super.onBackPressed();
		
		try{
			Bundle b=getIntent().getExtras();
			
			String i=null,fs=null;
			
			if(b!=null)
			{
				i=b.getString("index");
				fs=b.getString("fromservice");
				
			}
			
			if(i!=null && fs.equals("true"))
			{
				int index=Integer.parseInt(i);
				ServiceAppLock.selectappstrack.set(index, "0");
			}
			
			String change=sp.getString("change", null);
			
			if(change!=null)
			{
				e.putString("change", null);
				e.commit();
			}
			
			if(b==null)
			{
				Intent i1=new Intent("lockselectorapplock");
				i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i1);
			}
			
			finish();
		}catch(Exception e){}
	}

}
