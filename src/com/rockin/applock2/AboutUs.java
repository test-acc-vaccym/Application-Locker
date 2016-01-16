package com.rockin.applock2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AboutUs extends Activity implements OnClickListener{
	
	ImageButton backallpage,privacypolicy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		backallpage=(ImageButton)findViewById(R.id.backallpage);
		privacypolicy=(ImageButton)findViewById(R.id.privacypolicy);
		
		backallpage.setOnClickListener(this);
		privacypolicy.setOnClickListener(this);
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
		
		Intent i;
		
		switch(v.getId())
		{
		case R.id.backallpage:
			i=new Intent("chooserapplock");
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
			finish();
			break;
		case R.id.privacypolicy:
			i=new Intent(AboutUs.this,PolicyActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
			finish();
			break;
		}
	}
}
