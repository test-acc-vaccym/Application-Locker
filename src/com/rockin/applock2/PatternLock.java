package com.rockin.applock2;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class PatternLock extends Activity {
	
	final int REQ_CREATE_PATTERN=1;
	private static final int REQ_ENTER_PATTERN = 2;
	
	Editor e;
	SharedPreferences sp;
	
	CommonClass cc;
	
	String temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
		e=sp.edit();
		
		cc=new CommonClass(this);
		
		String pass=sp.getString("password", "0");
		String change=sp.getString("change", null);
		
		if(change!=null)
		{
			temp=pass;
			pass="0";
		}
		
		if(pass.equals("0"))
		{
			Intent i=new Intent(LockPatternActivity.ACTION_CREATE_PATTERN, null,this, LockPatternActivity.class);
			
			startActivityForResult(i, REQ_CREATE_PATTERN);
		}
		else
		{
			Intent intent = new Intent(LockPatternActivity.ACTION_COMPARE_PATTERN, null,this, LockPatternActivity.class);
			
			char[] savedPattern=pass.toCharArray();
			
			intent.putExtra(LockPatternActivity.EXTRA_PATTERN, savedPattern);
			startActivityForResult(intent, REQ_ENTER_PATTERN);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode)
		{
		case REQ_CREATE_PATTERN:
			if(resultCode==RESULT_OK)
			{
				char[] pattern=data.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN);
				
				String s=new String(pattern);
				
				cc.deleteFiles();
				
				e.putString("locktype", "pattern");
				e.putString("launched", "true");	
				e.putString("password", s);
				e.commit();
				
				Bundle b1=getIntent().getExtras();
				
				if(b1==null)
				{
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
				e.putString("formfilled", null);
				e.commit();
			}
			break;
		case REQ_ENTER_PATTERN: {
	        switch (resultCode) {
	        case RESULT_OK:
	        	Bundle b1=getIntent().getExtras();
	        	
				if(b1==null)
				{
					Intent i=new Intent("installedappsapplock");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else
				{
					finish();
				}
	            break;
	        case RESULT_CANCELED:
	        	break;
	        case LockPatternActivity.RESULT_FAILED:
	        	break;
	        }
		}
		}

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
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
	}
}
