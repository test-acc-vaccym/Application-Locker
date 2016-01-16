package com.rockin.applock2;

import android.app.Activity;
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

public class StringLockChange extends Activity implements OnClickListener{
	
	EditText password;
	ImageButton submit,backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	CommonClass cc;
	
	boolean b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stringlock);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			password=(EditText)findViewById(R.id.password);
			submit=(ImageButton)findViewById(R.id.submit);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			cc=new CommonClass(this);
			
			backallpage.setOnClickListener(this);
			submit.setOnClickListener(this);
		}catch(Exception e){}
	}

	public void onClick(View v) {
		
		try{
			switch(v.getId())
			{
			case R.id.submit:
				check();
				
				if(b==true)
				{
					Toast t=Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
					
					e.putString("change", "true");
		        	e.commit();
		        	
		        	Intent i=new Intent(this,LockSelector.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        	startActivity(i);
		        	finish();
				}
				else
				{
					Toast t1=Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT);
					
					t1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t1.show();
					
					password.setText("");
				}
				break;
			case R.id.backallpage:
				Intent i=new Intent("chooserapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				finish();
				break;
			}
		}catch(Exception e){	
		}
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		Intent i=new Intent("chooserapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		
		finish();
	}
	
	public void check() throws Exception
	{
		String s=password.getText().toString();
		b=cc.checkPassword(s);
	}
}
