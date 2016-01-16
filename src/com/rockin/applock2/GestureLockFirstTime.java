package com.rockin.applock2;

import java.io.File;

import android.annotation.SuppressLint;
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
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class GestureLockFirstTime extends Activity implements OnClickListener{
	
	EditText name,emailid;
	ImageButton submit,backallpage;
	GestureOverlayView overlay;
	
	SharedPreferences sp;
	Editor e;
	
	private Gesture mGesture;
	
	ProgressDialog pd;
	
	String sname,semailid;
	
	private static final float LENGTH_THRESHOLD = 120.0f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturesfirsttime);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		sp=getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
		e=sp.edit();
		
		name=(EditText)findViewById(R.id.name);
		emailid=(EditText)findViewById(R.id.emailid);
		submit=(ImageButton)findViewById(R.id.submit);
		backallpage=(ImageButton)findViewById(R.id.backallpage);
		
		submit.setOnClickListener(this);
		backallpage.setOnClickListener(this);
		
		pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Loading..");
		pd.setCancelable(false);
		
		overlay= (GestureOverlayView) findViewById(R.id.gesture);
        overlay.addOnGestureListener(new GesturesProcessor());
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent("lockselectorapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(i);
		
		finish();
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        if (mGesture != null && mGesture.getLength()>130.0) {
            outState.putParcelable("gesture", mGesture);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        
        mGesture = savedInstanceState.getParcelable("gesture");
        if (mGesture != null && mGesture.getLength()>130.0) {

            overlay.post(new Runnable() {
                public void run() {
                    overlay.setGesture(mGesture);
                }
            });

            submit.setEnabled(true);
        }
        else
        {
        	submit.setEnabled(false);
        }
    }
	
	private class GesturesProcessor implements GestureOverlayView.OnGestureListener {
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            submit.setEnabled(false);
            mGesture = null;
        }

        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        }

        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            mGesture = overlay.getGesture();
            if (mGesture.getLength() < LENGTH_THRESHOLD) {
               overlay.clear(false);
            }
            submit.setEnabled(true);
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.submit:
			
			getStringValues();
			
			if(checkAllStrings())
			{
				if (mGesture != null && mGesture.getLength()>130.0) {
					
					pd.show();

		            setResult(RESULT_OK);
		            
		            e.putString("formfilled", "true");
					e.putString("name", sname);
					e.putString("emailid", semailid);
					e.putString("locktype", "gesture");
					e.putString("launched", "true");
					e.commit();
		            
		            File mStoreFile1 = new File(Environment.getExternalStorageDirectory(), "gestures");
		            mStoreFile1.delete();
		            File mStoreFile = new File(Environment.getExternalStorageDirectory(), "gestures");
		            GestureLibrary sStore = GestureLibraries.fromFile(mStoreFile);
		            final GestureLibrary store = sStore;
		            store.addGesture("My gesture", mGesture);
		            store.save();
		            
		            Toast t=Toast.makeText(this, "Gesture Recorded", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show(); 
		            
					Intent i=new Intent("installedappsapplock");
		            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(i);
		        } else {
		        	Toast t=Toast.makeText(this, "Draw Gesture", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
		        }
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
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	public void getStringValues()
	{
		sname=name.getText().toString();
		semailid=emailid.getText().toString();
	}
	
	public boolean checkAllStrings()
	{
		if(sname.trim().length()!=0 && semailid.trim().length()!=0)
		{
			if(semailid.contains("@"))
		
				return true;
			
		}
		if(sname.trim().length()==0 && (semailid.trim().length()==0 || semailid.contains("@")))
		{
			Toast t=Toast.makeText(this, "Enter correct details", Toast.LENGTH_SHORT);
			
			t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			t.show();
			return false;
		}
		else if(sname.trim().length()==0)
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
