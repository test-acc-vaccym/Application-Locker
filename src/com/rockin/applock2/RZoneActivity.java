package com.rockin.applock2;


import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
public class RZoneActivity extends Activity {
	
	private GridviewAdapter mAdapter;
    
	private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;
    
    private GridView gridView;
	private String[] getURL = {

		"http://yeahmobi.go2cloud.org/aff_c?offer_id=15692&aff_id=14788",//11
		"http://yeahmobi.go2cloud.org/aff_c?offer_id=17536&aff_id=14788",//22
		"http://hasoffers.ymtrack.com/aff_c?offer_id=16574&aff_id=14788",//33

"https://play.google.com/store/apps/details?id=com.rockin.balloonbubbletap",//3
"https://play.google.com/store/apps/details?id=com.rockin.BluetoothChat",//4
"https://play.google.com/store/apps/details?id=com.rock.canvasfingerpainting",//5
"https://play.google.com/store/apps/details?id=com.app.balloonshooting",//6
"https://play.google.com/store/apps/details?id=com.sexy.teargirlsclothes",//7
"https://play.google.com/store/apps/details?id=com.rcoki.dilkiaawaz",//8
"https://play.google.com/store/apps/details?id=com.fruit.fruitmatcher",//9
"https://play.google.com/store/apps/details?id=com.rock.impressgirl",//10
"https://play.google.com/store/apps/details?id=com.my.valentinelovebirds",//11
"https://play.google.com/store/apps/details?id=com.flashlight.newflashlight",//12
"https://play.google.com/store/apps/details?id=com.rockin.truthdare",//13
"https://play.google.com/store/apps/details?id=com.myquiz",//14
"https://play.google.com/store/apps/details?id=com.imtux.tictactoe",//15
"https://play.google.com/store/apps/details?id=com.rockin.santagiftcart",//16
"https://play.google.com/store/apps/details?id=com.rockinentser.diary",//17
"https://play.google.com/store/apps/details?id=com.rockin.stopwatch",//18
"https://play.google.com/store/apps/details?id=com.rock.utilitycompass",//19
"https://play.google.com/store/apps/details?id=com.blessing.christmasblessings",//20
"https://play.google.com/store/apps/details?id=com.rockn.chistmasparty",//21
"https://play.google.com/store/apps/details?id=com.rockinentser.myquiz",//22

	};
	@Override

	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.rzone);
		prepareList();

		// prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(this,listCountry, listFlag);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			
				Context context=arg1.getContext();
	        	
				Intent thingyToInstall=new Intent(Intent.ACTION_VIEW);
				thingyToInstall.setDataAndType(Uri.parse(getURL[arg2]), null);
	        	
				
				context.startActivity(thingyToInstall);
			}
		});
        
		
	}
	
	 public void prepareList()
	    {
	          listCountry = new ArrayList<String>();
	          
	          listCountry.add("FlipKart");//11
	          listCountry.add("Airtel");//22
	          listCountry.add("DU Market");//33
	          
	          listCountry.add("Balloon Bubble");//3

	          listCountry.add("Bluetooth Chat");//4
	          listCountry.add("Canvas Painting");//5
	          listCountry.add("Balloon Shooter");//6
	          listCountry.add("Tear Girls Clothes");//7

	          listCountry.add("Dil Ki Aawaz");//8
	          listCountry.add("Fruit Matcher");//9
	          listCountry.add("Impress The Girl");//10
	          
	          listCountry.add("Love Birds");//11
	          listCountry.add("Torch Utility");//12
	          
	          listCountry.add("Truth & Dare");//13
	          listCountry.add("Rockin Quiz");//14
	          listCountry.add("Zero Kaata");//15

	          listCountry.add("Santa Gift Cart");//16
	          listCountry.add("Smart Diary");//17
	          listCountry.add("Stopwatch");//18
	          listCountry.add("Utility Compass");//19
	          
	          listCountry.add("ChristmasBless");//20
	          listCountry.add("Christmas Party");//21
	          listCountry.add("My Quiz");//22
	          listFlag = new ArrayList<Integer>();

//------------------------------------------------------------------------------
	          
	          listFlag.add(R.drawable.rz_flipkart);//11
	          listFlag.add(R.drawable.rz_airtel);//22
	          listFlag.add(R.drawable.rz_dumarket);//33

	          listFlag.add(R.drawable.rz_balloonbubbletap);//3

	          listFlag.add(R.drawable.rz_bluetoothchat);//4
	          listFlag.add(R.drawable.rz_canvasfingerpainting);//5
	          listFlag.add(R.drawable.rz_rockinballoonshoote);//6
	          listFlag.add(R.drawable.rz_teargirl);//7
	          listFlag.add(R.drawable.rz_dilkeawaaz);//8
	          
	          listFlag.add(R.drawable.rz_fruitmatcher);//9
	          listFlag.add(R.drawable.rz_impressthegirl);//10
	          listFlag.add(R.drawable.rz_valentine_love_birds);//11
	          listFlag.add(R.drawable.rz_torchutility);//12
	          listFlag.add(R.drawable.rz_truthndare);//13
	          
	          listFlag.add(R.drawable.rz_quiz);//14
	          listFlag.add(R.drawable.rz_zerokaata);//15
	          listFlag.add(R.drawable.rz_santagiftcart);//16
	          listFlag.add(R.drawable.rz_smartdiary);//17
	          listFlag.add(R.drawable.rz_stopwatch);//18
	          
	          listFlag.add(R.drawable.rz_utilitycompas);//19
	          listFlag.add(R.drawable.rz_christmasblessing);//20
	          listFlag.add(R.drawable.rz_christmasparty);//21
	          listFlag.add(R.drawable.rz_myquizicon);//21
	    }
	 
	 
}
