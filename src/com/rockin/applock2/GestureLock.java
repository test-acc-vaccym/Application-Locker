package com.rockin.applock2;

import java.io.File;

import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.DONUT)
public class GestureLock extends Activity implements OnClickListener{
    private static final float LENGTH_THRESHOLD = 120.0f;

    private Gesture mGesture;
    private View mDoneButton;
    
    SharedPreferences sp;
    Editor e;
    
    ImageButton backallpage;

    CommonClass cc;
    
    ProgressDialog pd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturesnew);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDoneButton = findViewById(R.id.submit);
        
        sp=getSharedPreferences("com.rockin.applock2", MODE_PRIVATE);
        e=sp.edit();
        
        cc=new CommonClass(this);

        GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gesture);
        overlay.addOnGestureListener(new GesturesProcessor());
        
        backallpage=(ImageButton)findViewById(R.id.backallpage);
        
        backallpage.setOnClickListener(this);
        
        pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Loading..");
		pd.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
    	
    	switch(v.getId())
    	{
    	case R.id.backallpage:
    		Intent i=new Intent("lockselectorapplock");
    		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		
    		startActivity(i);
    		finish();
    		break;
    	}
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
            final GestureOverlayView overlay =
                    (GestureOverlayView) findViewById(R.id.gesture);
            overlay.post(new Runnable() {
                public void run() {
                    overlay.setGesture(mGesture);
                }
            });

            mDoneButton.setEnabled(true);
        }
        else
        {
        	mDoneButton.setEnabled(false);
        }
    }

    public void addGesture(View v) {
        if (mGesture != null && mGesture.getLength()>130.0) {

        	pd.show();
        	
        	cc.deleteFiles();
        	
			e.putString("locktype", "gesture");
			e.putString("launched", "true");
			e.commit();
        	
            setResult(RESULT_OK);
            
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
            setResult(RESULT_CANCELED);
        }

        finish();
        
    }
    
    public void cancelGesture(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
    
    private class GesturesProcessor implements GestureOverlayView.OnGestureListener {
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mDoneButton.setEnabled(false);
            mGesture = null;
        }

        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        }

        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            mGesture = overlay.getGesture();
            if (mGesture.getLength() < LENGTH_THRESHOLD) {
                overlay.clear(false);
            }
            mDoneButton.setEnabled(true);
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }
}
