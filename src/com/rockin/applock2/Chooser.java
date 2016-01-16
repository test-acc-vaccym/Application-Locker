package com.rockin.applock2;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Chooser extends Activity implements OnClickListener{
	
	final int REQ_CREATE_PATTERN=1;
	private static final int REQ_ENTER_PATTERN = 2;
	
	ImageButton changepassword,forgotpassword,changelist,exit,instructions,aboutus,rzone;
	
	String locktype;
	
	SharedPreferences sp;
	Editor e;
	
	CommonClass cc;
	
	boolean exitOnPause=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooser);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			locktype=sp.getString("locktype", "0");
			
			cc=new CommonClass(this);
			
			changepassword=(ImageButton)findViewById(R.id.changepassword);
			forgotpassword=(ImageButton)findViewById(R.id.forgotpassword);
			changelist=(ImageButton)findViewById(R.id.changeapplist);
			exit=(ImageButton)findViewById(R.id.exit);
			instructions=(ImageButton)findViewById(R.id.instructions);
			aboutus=(ImageButton)findViewById(R.id.aboutus);
			rzone=(ImageButton)findViewById(R.id.rzone);
			
			changepassword.setOnClickListener(this);
			forgotpassword.setOnClickListener(this);
			changelist.setOnClickListener(this);
			exit.setOnClickListener(this);
			instructions.setOnClickListener(this);
			aboutus.setOnClickListener(this);
			rzone.setOnClickListener(this);
		}catch(Exception e){}
	}
	

	public void onClick(View v) {
		
		try{
			switch(v.getId())
			{
			case R.id.changepassword:
				if(locktype.equals("pattern"))
				{
					changePatternLock();
				}
				else if(locktype.equals("string"))
				{
					changeStringLock();
				}
				else if(locktype.equals("gesture"))
				{
					changeGestureLock();
				}
				else if(locktype.equals("numeric"))
				{
					changeNumericLock();
				}
				break;
			case R.id.forgotpassword:
				
				ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
				
				if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
				{
					Intent i1=new Intent("forgotpasswordapplock");
					i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i1);
					finish();
				}
				else
				{
					Toast t=Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT);
				
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
				}
				
				break;
			case R.id.changeapplist:
				
				e.putString("change", null);
				e.commit();
				
				String sender=null;
				
				if(locktype.equals("string"))
					sender="stringlockapplock";
				else if(locktype.equals("pattern"))
					sender="patternlockapplock";
				else if(locktype.equals("gesture"))
					sender="gesturecheckerapplock";
				else if(locktype.equals("numeric"))
					sender="numericlockapplock";
				
				Intent i=new Intent(sender);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				finish();
				break;
			case R.id.instructions:
				Intent i1=new Intent("instructionsapplock");
				i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i1);
				
				finish();
				break;
			case R.id.aboutus:
				Intent i2=new Intent("aboutusapplock");
				i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i2);
				
				finish();
				break;
			case R.id.exit:
				finish();
				break;
			case R.id.rzone:
				Intent i3=new Intent(Chooser.this,RZoneActivity.class);
				i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i3);
				//finish();
			}
		}catch(Exception e){}
	}
	
	public void changeStringLock() throws Exception
	{
		Intent i=new Intent("stringlockchangeapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
	
	public void changeGestureLock() throws Exception
	{
		Intent i=new Intent("gesturelockchangeapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
	
	public void changeNumericLock()
	{
		Intent i=new Intent("numericlockchangeapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
	
	public void changePatternLock() throws Exception
	{	
		String pass=sp.getString("password", "0");
		
		Intent intent = new Intent(LockPatternActivity.ACTION_COMPARE_PATTERN, null,this, LockPatternActivity.class);
		
		char[] savedPattern=pass.toCharArray();
		
		exitOnPause=false;
		
		intent.putExtra(LockPatternActivity.EXTRA_PATTERN, savedPattern);
		startActivityForResult(intent, REQ_ENTER_PATTERN);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode)
		{
		case REQ_ENTER_PATTERN: {
	        switch (resultCode) {
	        case RESULT_OK:
	        	
	        	e.putString("change", "true");
	        	e.commit();
	        	
	        	Intent i=new Intent(this,LockSelector.class);
	        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(i);
	        	
	        	finish();
	            break;
	        case RESULT_CANCELED:
	        	finish();
	            break;
	        case LockPatternActivity.RESULT_FAILED:
	        	break;
	        }
		}
	}
}
	@Override
	protected void onPause() {
		super.onPause();
		
		if(exitOnPause==true)
			finish();
	}
	
}
