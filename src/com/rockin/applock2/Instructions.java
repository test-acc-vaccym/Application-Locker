package com.rockin.applock2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Instructions extends Activity implements OnClickListener{
	
	ImageButton backallpage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		backallpage=(ImageButton)findViewById(R.id.backallpage);
		backallpage.setOnClickListener(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent("chooserapplock");
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		
		finish();
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.backallpage:
			Intent i=new Intent("chooserapplock");
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
			finish();
			break;
		}
	}
}
