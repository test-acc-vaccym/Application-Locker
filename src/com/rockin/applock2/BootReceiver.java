package com.rockin.applock2;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;

public class BootReceiver extends BroadcastReceiver{
	
	SharedPreferences sp;
	ArrayList<ApplicationInfo> selectedappslist;
	List<ApplicationInfo> listappinfo;
	HelperSelectedApps hsa;
	
	PackageManager pm;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		try
		{
			sp=context.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			
			String formfilled=sp.getString("formfilled", null);
			selectedappslist=new ArrayList<ApplicationInfo>();
			hsa=new HelperSelectedApps(context);
			
			pm = context.getPackageManager();
			
			
			if(formfilled!=null)
			{
				getPreSelectedAppsList();
				
				new ServiceAppLock(selectedappslist);
				Intent i=new Intent(context,ServiceAppLock.class);
				context.startService(i);
			}
		}catch(Exception e){}	
		
	}
	
	public void getPreSelectedAppsList() throws Exception
	{
		listappinfo = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		Cursor c = hsa.display();
		
		int i = c.getColumnIndex("PackageName");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String pname = c.getString(i);

			for (ApplicationInfo ai : listappinfo) {
				if (pname.equals(ai.packageName)) {
					selectedappslist.add(ai);
				}
			}
		}
	}

}
