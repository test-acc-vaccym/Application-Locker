package com.rockin.applock2;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;

public class ServiceAppLock extends Service{
	
	static ArrayList<ApplicationInfo> selectedappslist;
	static ArrayList<String> selectappstrack;
	
	HelperSelectedApps hsa;
	
	ArrayList<String> runningappnames;
	
	ActivityManager am;
	PackageManager pm;
	
	List<RunningTaskInfo> runningtasks;
	
	SharedPreferences sp;
	Editor e;
	
	int error=0;
	
	String locktype;
	
	public ServiceAppLock()
	{
	}
	
	public ServiceAppLock(ArrayList<ApplicationInfo> selectedappslist1)
	{
		selectedappslist=new ArrayList<ApplicationInfo>();
		
		selectedappslist=selectedappslist1;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Intent i=new Intent(this,ServiceAppLock.class);
		startService(i);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		try{
			sp=this.getSharedPreferences("com.rockin.applock2",Context.MODE_PRIVATE);
			e=sp.edit();
			
			hsa=new HelperSelectedApps(this);
			
			am=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
			pm=getPackageManager();
			
			selectappstrack=new ArrayList<String>();
			
			getSelectedAppsList();
			
			for(int i=0;i<selectedappslist.size();i++)
			{
				selectappstrack.add("0");
			}
			
			Timer timer=new Timer();
			
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run(){
					
					try{
						locktype=sp.getString("locktype", "0");
						
						runningtasks=new ArrayList<RunningTaskInfo>();
						runningappnames=new ArrayList<String>();
						runningtasks=am.getRunningTasks(Integer.MAX_VALUE);
						
						getRunningAppNames();
						isSelectedAppRunning();
					}catch(Exception e){
						error=1;
					}
				}
			}, 0, 400);
		}catch(Exception e){
			
			error=1;
			
			if(error==1)
			{
				error=0;
				Intent i=new Intent(this,ServiceAppLock.class);
				stopService(i);
				startService(i);
			}
			
		}
		
		//return super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}
	
	public void getSelectedAppsList() throws Exception
	{
		selectedappslist=new ArrayList<ApplicationInfo>();
		
		Cursor c=hsa.display();
		
		List<ApplicationInfo> listappinfo=new ArrayList<ApplicationInfo>();
		PackageManager pm=getPackageManager();
		
		listappinfo=pm.getInstalledApplications(PackageManager.GET_META_DATA);
		
		int i=c.getColumnIndex("PackageName");
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			String pname=c.getString(i);
			
			for(ApplicationInfo ai:listappinfo)
			{
				if(pname.equals(ai.packageName))
				{
					selectedappslist.add(ai);
				}
			}
		}
	}
	
	public void getRunningAppNames() throws Exception
	{
		for(RunningTaskInfo r:runningtasks)
		{
			String s=r.topActivity.getPackageName();
			ApplicationInfo ai=pm.getApplicationInfo(s, 0);
			
			runningappnames.add(ai.loadLabel(pm).toString());
		}
	}
	
	public void isSelectedAppRunning() throws Exception
	{
		for(ApplicationInfo ai:selectedappslist)
		{
			int index=selectedappslist.indexOf(ai);
			
			if(runningappnames.contains(ai.loadLabel(pm).toString()) && ai.packageName.equals(runningtasks.get(0).topActivity.getPackageName()))
			{
				if(selectappstrack.get(index).equals("0"))
				{
					selectappstrack.set(index, "1");
					
					String sender=null;
					
					if(locktype.equals("string"))
						sender="stringlockapplock";
					else if(locktype.equals("pattern"))
						sender="patternlockapplock";
					else if(locktype.equals("gesture"))
						sender="gesturecheckerapplock";
					else if(locktype.equals("numeric"))
						sender="numericlockapplock";
					
					Intent i=new Intent(sender);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					Bundle b1=new Bundle();
					b1.putString("fromservice", "true");
					b1.putString("index", String.valueOf(index));
					
					i.putExtras(b1);
					
					startActivity(i);
				}
			}
			else if(!runningtasks.get(0).topActivity.getPackageName().equals("com.rockin.applock2"))
				selectappstrack.set(index, "0");
		}
	}

}
