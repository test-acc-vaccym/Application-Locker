package com.rockin.applock2;

public class InstalledAppsView {
	 private String name = "" ;  
	    private boolean checked = false ;  
	    public InstalledAppsView() {}   
	    public InstalledAppsView( String name ) {  
	      this.name = name ;  
	    }  
	    public InstalledAppsView( String name, boolean checked ) {  
	      this.name = name ;  
	      this.checked = checked ;  
	    }  
	    public String getName() {  
	      return name;  
	    }  
	    public void setName(String name) {  
	      this.name = name;  
	    }  
	    public boolean isChecked() {  
	      return checked;  
	    }  
	    public void setChecked(boolean checked) {  
	      this.checked = checked;  
	    }  
	    public String toString() {  
	      return name ;   
	    }  
	    public void toggleChecked() {  
	      checked = !checked ;  
	    }
}
