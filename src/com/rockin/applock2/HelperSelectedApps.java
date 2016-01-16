package com.rockin.applock2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperSelectedApps extends SQLiteOpenHelper{

	static String tbname="Tasks";
	static String dbname="Database2";
	static int ver=1;
	SQLiteDatabase sld;
	Context c;
	String col1="PackageName";
	static int counter=0;
	String cols[]={col1};
	
	public HelperSelectedApps(Context context) {
		super(context, dbname, null, ver);
		c=context;
		sld=getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		if(counter==0)
		{
			db.execSQL("create table "+tbname+"("+col1+" TEXT NOT NULL);");	
		}
		counter++;
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		
	}
	
	public void insert(String packagename)
	{
		sld.execSQL("insert into "+tbname+" values('"+packagename+"');");
	}

	public Cursor display()
	{
		Cursor c=sld.query(tbname,cols, null, null, null, null, null);;
		return c;
	}
	
	public void clearAll()
	{
		sld.execSQL("delete from "+tbname+";");
	}
	
	public void clearData(String s)
	{
		sld.execSQL("delete from "+tbname+" where PackageName='"+s+"';");
	}
	
	public void closeDatabase()
	{
		sld.close();
	}
}
