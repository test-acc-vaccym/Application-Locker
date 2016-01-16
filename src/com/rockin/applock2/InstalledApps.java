package com.rockin.applock2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class InstalledApps extends Activity implements OnClickListener {

	ListView installedappslist;

	static ArrayList<String> installedapps;
	static List<ApplicationInfo> listappinfo;
	static ArrayList<ApplicationInfo> selectedappslist;
	static ArrayList<Integer> preselectedappindex;
	
	ImageButton ok,backallpage;

	HelperSelectedApps hsa;
	ArrayAdapter<String> aa;

	PackageManager pm;

	LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installedapps);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try{
			hsa = new HelperSelectedApps(this);

			inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

			installedappslist = (ListView) findViewById(R.id.installedappslist);
			ok=(ImageButton)findViewById(R.id.ok);
			backallpage=(ImageButton)findViewById(R.id.backallpage);
			installedappslist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

			pm = getPackageManager();

			listappinfo = new ArrayList<ApplicationInfo>();
			installedapps = new ArrayList<String>();
			selectedappslist = new ArrayList<ApplicationInfo>();

			ok.setOnClickListener(this);
			backallpage.setOnClickListener(this);
		}catch(Exception e){}
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
	protected void onResume() {
		super.onResume();
		
			getInstalledAppsList();	
			getPreSelectedAppsIndex();
	}
	
	@Override
	public void onClick(View v) {
		
		try{
			
			switch(v.getId())
			{
			case R.id.ok:
					hsa.clearAll();
				
					for (ApplicationInfo ai : selectedappslist) {
						hsa.insert(ai.packageName);
					}

					new ServiceAppLock(selectedappslist);

					Intent i = new Intent(this, ServiceAppLock.class);
					stopService(i);

					startService(i);
					
					Toast t=Toast.makeText(this, "Apps Locked", Toast.LENGTH_SHORT);
					
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
					
					Thread thread=new Thread()
					{
						@Override
						public void run() {
							super.run();
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
							}
							finish();
						}
					};
					thread.start();
				break;
			case R.id.backallpage:
				Intent i1=new Intent("chooserapplock");
				i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				startActivity(i1);
				
				finish();
				break;
			}
		}catch(Exception e){}
	}

	public void getPreSelectedAppsIndex(){
		Cursor c = hsa.display();
		preselectedappindex=new ArrayList<Integer>();

		int i = c.getColumnIndex("PackageName");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String pname = c.getString(i);

			for (ApplicationInfo ai : listappinfo) {
				if (pname.equals(ai.packageName)) {
					int x = listappinfo.indexOf(ai);
					preselectedappindex.add(x);

					selectedappslist.add(ai);
				}
			}
		}
	}
	
	public void getInstalledAppsList(){
		listappinfo = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		
		for(ApplicationInfo ai:listappinfo)
		{
			String s=ai.loadLabel(pm).toString();
			installedapps.add(s);
		}
		
		ArrayList<InstalledAppsView> installedappsview=new ArrayList<InstalledAppsView>();
		
		for(String s:installedapps)
		{
			installedappsview.add(new InstalledAppsView(s));
		}
		
		InstalledAppsCustomAdapter aa=new InstalledAppsCustomAdapter(this, installedappsview);
		installedappslist.setAdapter(aa);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
		
	}

}
