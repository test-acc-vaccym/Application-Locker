package com.rockin.applock2;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class InstalledAppsViewHolder {
	
	CheckBox checkbox;
	TextView textView ;  
	ImageView imageView;
    public InstalledAppsViewHolder() {}  
    public InstalledAppsViewHolder( TextView textView, CheckBox checkbox,ImageView imageView ) {  
      this.checkbox = checkbox;  
      this.textView = textView ;  
      this.imageView=imageView;
    }  
    public CheckBox getCheckBox() {  
      return checkbox;  
    }  
    public void setCheckBox(CheckBox checkbox) {  
      this.checkbox = checkbox;  
    }  
    public TextView getTextView() {  
      return textView;  
    }  
    public void setTextView(TextView textView) {  
      this.textView = textView;  
    } 
    public void setImageView(ImageView imageView)
    {
    	this.imageView=imageView;
    }
    public ImageView getImageView()
    {
    	return imageView;
    }
    
}
