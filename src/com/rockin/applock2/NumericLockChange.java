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

public class NumericLockChange extends Activity implements OnClickListener{
	
	EditText password;
	ImageButton submit,backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	CommonClass cc;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numericlock);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			password=(EditText)findViewById(R.id.password);
			submit=(ImageButton)findViewById(R.id.submit);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			cc=new CommonClass(this);
			
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

	public void onClick(View v) {
		
		try{
			switch(v.getId())
			{
			case R.id.submit:
				
				String s=password.getText().toString();
				
				String temp=sp.getString("numericpassword", null);
				
				if(temp.equals(s))
				{
					e.putString("change", "true");
					e.commit();
					
					Toast t=Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();	
					
					Intent i=new Intent(NumericLockChange.this,LockSelector.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else
				{
					Toast t=Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
					
					password.setText("");
				}
				break;
			case R.id.backallpage:
				Intent i=new Intent("chooserapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				finish();
			}
		}catch(Exception e){}
	}
}
