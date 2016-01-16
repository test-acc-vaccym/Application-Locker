package com.rockin.applock2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class SplashScreen extends Activity{
	
	String form;
	
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		sp=getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
		
		Thread t=new Thread(){
			@Override
			public void run() {
				super.run();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				
				
				form=sp.getString("formfilled", null);
				if(form!=null)
				{
					Intent i=new Intent("chooserapplock");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else
				{
					Intent i=new Intent("lockselectorapplock");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
			}
		};
		
		t.start();
		
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
