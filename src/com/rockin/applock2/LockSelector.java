package com.rockin.applock2;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class LockSelector extends Activity implements OnClickListener{
	
	ImageButton stringlock,patternlock,gesturelock,numericlock,backallpage;
	
	CommonClass cc;
	
	SharedPreferences sp;
	Editor e;
	
	String locktype,launched,formfilled,change;
	int x;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lockselector);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			cc=new CommonClass(this);
			
			stringlock=(ImageButton)findViewById(R.id.stringlock);
			patternlock=(ImageButton)findViewById(R.id.patternlock);
			gesturelock=(ImageButton)findViewById(R.id.gesturelock);
			numericlock=(ImageButton)findViewById(R.id.numericlock);
			
			x=cc.existPassword();
			
			launched=sp.getString("launched", null);
			formfilled=sp.getString("formfilled", null);
			change=sp.getString("change", null);
			
			if(launched!=null && change==null)
			{
				Intent i=new Intent("chooserapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			
			stringlock.setOnClickListener(this);
			patternlock.setOnClickListener(this);
			gesturelock.setOnClickListener(this);
			numericlock.setOnClickListener(this);sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			cc=new CommonClass(this);
			
			stringlock=(ImageButton)findViewById(R.id.stringlock);
			patternlock=(ImageButton)findViewById(R.id.patternlock);
			gesturelock=(ImageButton)findViewById(R.id.gesturelock);
			numericlock=(ImageButton)findViewById(R.id.numericlock);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			x=cc.existPassword();
			
			launched=sp.getString("launched", null);
			formfilled=sp.getString("formfilled", null);
			change=sp.getString("change", null);
			
			if(launched!=null && change==null)
			{
				Intent i=new Intent("chooserapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			
			stringlock.setOnClickListener(this);
			patternlock.setOnClickListener(this);
			gesturelock.setOnClickListener(this);
			numericlock.setOnClickListener(this);
			backallpage.setOnClickListener(this);
		}catch(Exception e){}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		finish();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(formfilled==null)
			finish();
		else
		{
			Intent i=new Intent("chooserapplock");
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
			finish();
		}
	}
	
	public void onClick(View v) {
		
		try{
			Intent i;
			String sender=null;
			
			switch(v.getId())
			{
			case R.id.stringlock:
				
				if(formfilled==null)
					sender="stringlockfirsttimeapplock";
				else
					sender="stringlockapplock";
				break;
			case R.id.patternlock:
				if(formfilled==null)
					sender="formfillingapplock";
				else
					sender="patternlockapplock";
				if(x==0)
					try {
						cc.printToast();
					} catch (IOException e1) {
					}
				break;
			case R.id.gesturelock:
				if(formfilled==null)
					sender="gesturelockfirsttimeapplock";
				else
					sender="gesturelockapplock";
				if(x==0)
					try {
						cc.printToast();
					} catch (IOException e1) {
					}
				break;
			case R.id.numericlock:
				if(formfilled==null)
					sender="numericlockfirsttimeapplock";
				else
					sender="numericlockapplock";
				break;
			case R.id.backallpage:
				if(formfilled==null)
					finish();
				else
					sender="chooserapplock";
				break;
			}
			
			e.commit();
		
			if(sender!=null)
			{
				i=new Intent(sender);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				startActivity(i);
			}
		}catch(Exception e){}
		
	}

}
