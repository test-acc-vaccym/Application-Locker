package com.rockin.applock2;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class GestureChecker extends Activity implements OnGesturePerformedListener,OnClickListener{

	GestureOverlayView gesture;
	GestureLibrary library;
	
	SharedPreferences sp;
	Editor e;
	
	ImageButton backallpage;
	
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gestures);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			gesture=(GestureOverlayView)findViewById(R.id.gesture);
			
			sp=getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
			e=sp.edit();
			
			gesture.addOnGesturePerformedListener(this);
			File f=Environment.getExternalStorageDirectory();
			String temp=f.getAbsolutePath()+"/gestures";
			library=GestureLibraries.fromFile(new File(temp));
			
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			Bundle b1=getIntent().getExtras();
			
			if(b1!=null)
				backallpage.setEnabled(false);
			
			backallpage.setOnClickListener(this);
			
			library.load();
			
			pd=new ProgressDialog(this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Loading..");
			pd.setCancelable(false);
		}catch(Exception e){}
	}
	
	@Override
	public void onClick(View v) {
		
		try{
			switch(v.getId())
			{
			case R.id.backallpage:
				Intent i=new Intent("chooserapplock");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				finish();
				break;
			}
		}catch(Exception e){}
	}

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		
		try{
			ArrayList<Prediction> pred=library.recognize(gesture);
			
			if(pred.get(0).score>2.0)
			{
				Toast t=Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
				
				t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				t.show();
				
				Bundle b=getIntent().getExtras();
				
				if(b!=null)
				{
					finish();
				}
				else
				{
					pd.show();
					Intent i=new Intent("installedappsapplock");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
			}
			
			else
			{
				Toast t=Toast.makeText(this, "Failed", Toast.LENGTH_SHORT);
				
				t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				t.show();
			}
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
