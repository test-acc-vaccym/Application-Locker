package com.rockin.applock2;

import java.net.URI;
import java.util.UUID;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ForgotPassword extends Activity implements OnClickListener{
	
	EditText name,emailid;
	ImageButton submit,backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	String sname,semailid,newpassword;
	
	CommonClass cc;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpassword);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);	
			cc=new CommonClass(this);
			
			sp=this.getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
			e=sp.edit();
			
			sname=sp.getString("name", null);
			semailid=sp.getString("emailid", null);
			
			name=(EditText)findViewById(R.id.name);
			emailid=(EditText)findViewById(R.id.emailid);
			submit=(ImageButton)findViewById(R.id.submit);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			submit.setOnClickListener(this);
			backallpage.setOnClickListener(this);
		}catch(Exception e){}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent("chooserapplock");
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
				String tname=name.getText().toString();
				String temailid=emailid.getText().toString();
				
				if(tname!=null && temailid!=null)
				{
					if(tname.equalsIgnoreCase(sname) && temailid.equalsIgnoreCase(semailid))
					{
						generateRandomPassword();
						sendEmail();
					}
					else
					{
						Toast t=Toast.makeText(this, "Incorrect Details", Toast.LENGTH_SHORT);
					
						t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						t.show();
					}
				}
				else
				{
					Toast t=Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT);
				
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
				}
				break;
			case R.id.forgotpassword:
				Intent i=new Intent("chooserapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				finish();
				break;
			case R.id.backallpage:
				Intent i1=new Intent("chooserapplock");
				i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i1);
				
				finish();
				break;
			}
		}catch(Exception e){}
	}
	
	public void generateRandomPassword() throws Exception
	{
		newpassword=UUID.randomUUID().toString();
		
		if(newpassword.length()>5)
		{
			newpassword=newpassword.substring(0, 5);
		}
		
		cc.writePassword(newpassword);
		
		e.putString("locktype", "string");
		e.commit();
		
	}
	
	public void sendEmail() throws Exception
	{
		String url1 = "http://clients.mappingclasses.com/mailserverapi/?token=f8cfb687237a2643a34fac1f3b851611&email="
				+ semailid + "&password=" + newpassword;
		
		HttpClient client=new DefaultHttpClient();
		
		HttpPost post=new HttpPost();
		post.setURI(URI.create(url1));
		
		client.execute(post);
		Toast t=Toast.makeText(this, "Mail Sent", Toast.LENGTH_SHORT);
		
		t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		t.show();
	}

}
