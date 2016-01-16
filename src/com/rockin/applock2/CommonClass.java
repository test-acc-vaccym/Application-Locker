package com.rockin.applock2;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

public class CommonClass {
	
	Activity act;
	
	SharedPreferences sp;
	Editor e;
	
	public CommonClass(Activity act1)
	{
		act=act1;
	}
	
	public void writePassword(String pass)
	{	
		sp=act.getSharedPreferences("com.rockin.applock2", Context.MODE_PRIVATE);
		e=sp.edit();
		
		e.putString("stringpassword", pass);
		e.commit();
	}
	
	public int existPassword()
	{
		sp=act.getSharedPreferences("com.rockin.applock2", Context.MODE_PRIVATE);
		
		String counter=sp.getString("counter", null);
		
		if(counter==null)
			return 0;
		else
			return 1;
	}
	
	public boolean checkPassword(String pass)
	{
		sp=act.getSharedPreferences("com.rockin.applock2", Context.MODE_PRIVATE);
		
		String password=sp.getString("stringpassword", null);
		
		if(password.equals(pass))
			return true;
		else
			return false;
	}
	
	public void printToast() throws IOException
	{	
		sp=act.getSharedPreferences("com.rockin.applock2", Context.MODE_PRIVATE);
		e=sp.edit();
		
		e.putString("counter", "1");
		e.commit();
	}
	
	public void deleteFiles()
	{
		sp=act.getSharedPreferences("com.rockin.applock2", Context.MODE_PRIVATE);
		e=sp.edit();
		
		String change=sp.getString("change", null);
		
		if(change!=null)
		{
			File f=Environment.getExternalStorageDirectory();
			String temp=f.getAbsolutePath()+"/gestures";
			
			File f1=new File(temp);
			f1.delete();

	    	e.putString("password", "0");
	    	e.putString("stringpassword", null);
	    	e.putString("counter", null);
			e.putString("numericpassword", null);
			e.putString("change", null);
			e.putString("launched", null);
			e.commit();
		}
	}

}
