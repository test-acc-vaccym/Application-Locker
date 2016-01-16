package com.rockin.applock2;

import android.app.Activity;
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

public class FormFilling extends Activity implements OnClickListener{
	
	EditText name,emailid;
	ImageButton submit,backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	String n,id;
	
	String form;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formfilling);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=this.getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
			e=sp.edit();
			
			name=(EditText)findViewById(R.id.name);
			emailid=(EditText)findViewById(R.id.emailid);
			submit=(ImageButton)findViewById(R.id.submit);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			backallpage.setOnClickListener(this);
			submit.setOnClickListener(this);
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
				n=name.getText().toString();
				id=emailid.getText().toString();
				
				if(checkAllStrings())
				{
					e.putString("name", n);
					e.putString("emailid", id);
					e.putString("formfilled", "true");
					
					e.commit();
					
					Toast t=Toast.makeText(this, "Details Submitted", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
					
					Intent i=new Intent("patternlockapplock");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					
					finish();
				}
				else
				{
					/*Toast t=Toast.makeText(this, "Enter correct details", Toast.LENGTH_SHORT);
					
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
	
	public boolean checkAllStrings()
	{
		if(n.trim().length()!=0 && id.trim().length()!=0)
		{
			if(id.contains("@"))
		
				return true;
			
		}
		if(n.trim().length()==0 && (id.trim().length()==0 || id.contains("@")))
		{
			Toast t=Toast.makeText(this, "Enter correct details", Toast.LENGTH_SHORT);
			
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.show();
			return false;
		}
		else if(n.trim().length()==0)
		{
			Toast t=Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT);
			
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.show();
			return false;
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
