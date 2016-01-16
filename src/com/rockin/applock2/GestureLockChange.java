package com.rockin.applock2;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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


public class GestureLockChange extends Activity implements OnGesturePerformedListener,OnClickListener{

	GestureOverlayView gesture;
	GestureLibrary library;
	
	ImageButton backallpage;
	
	SharedPreferences sp;
	Editor e;
	
	CommonClass cc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gestures);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		try{
			gesture=(GestureOverlayView)findViewById(R.id.gesture);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			
			cc=new CommonClass(this);
			
			sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			gesture.addOnGesturePerformedListener(this);
			File f=Environment.getExternalStorageDirectory();
			String temp=f.getAbsolutePath()+"/gestures";
			library=GestureLibraries.fromFile(new File(temp));
			
			library.load();
			
			backallpage.setOnClickListener(this);
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
	
	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		Intent i=new Intent("chooserapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		
		finish();
	}

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		
		try{
			ArrayList<Prediction> pred=library.recognize(gesture);
			
			if(pred.get(0).score>2.0)
			{
				Toast t=Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
				
				t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				t.show();
				
				e.putString("change", "true");
				e.commit();
				
				Intent i=new Intent(GestureLockChange.this, LockSelector.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			
			else
			{
				Toast t=Toast.makeText(this, "Failed", Toast.LENGTH_SHORT);
				
				t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				t.show();
			}
		}catch(Exception e){}
	}

}
