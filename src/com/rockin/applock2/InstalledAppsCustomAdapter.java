package com.rockin.applock2;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class InstalledAppsCustomAdapter extends ArrayAdapter<InstalledAppsView>{

	LayoutInflater inflater;
	PackageManager pm;
	
	public InstalledAppsCustomAdapter(Context context, List<InstalledAppsView> objects) {
		super(context, R.layout.installedappslistview, R.id.appname, objects);
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pm=context.getPackageManager();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		InstalledAppsView installed=(InstalledAppsView) this.getItem(position);
		
		TextView appname;
		CheckBox appselected;
		ImageView appicon;
		
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.installedappslistview, null);
			appname=(TextView)convertView.findViewById(R.id.appname);
			appselected=(CheckBox)convertView.findViewById(R.id.appselected);
			appicon=(ImageView)convertView.findViewById(R.id.appicon);
			
			convertView.setTag(new InstalledAppsViewHolder(appname, appselected,appicon));
			
			appselected.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CheckBox temp=(CheckBox) v;
					InstalledAppsView temp1=(InstalledAppsView) temp.getTag();
					temp1.setChecked(temp.isChecked());

					int pos=InstalledApps.installedapps.indexOf(temp1.getName());
					
					if(temp.isChecked())
					{
						InstalledApps.selectedappslist.add(InstalledApps.listappinfo.get(pos));
					} else {
						InstalledApps.selectedappslist.remove(InstalledApps.listappinfo.get(pos));
					}
				}
			});
		}
		else
		{
			InstalledAppsViewHolder temp=(InstalledAppsViewHolder) convertView.getTag();
			appname=temp.getTextView();
			appselected=temp.getCheckBox();
			appicon=temp.getImageView();
		}
		
		appselected.setTag(installed);
		
		if(InstalledApps.preselectedappindex.contains(position))
		{
			appselected.setChecked(true);
		}
		else
		{
			appselected.setChecked( installed.isChecked() );
		}  
	      
	    appname.setText(installed.getName()); 
	    appicon.setBackgroundDrawable(InstalledApps.listappinfo.get(position).loadIcon(pm));
		
		return convertView;
	}

}
