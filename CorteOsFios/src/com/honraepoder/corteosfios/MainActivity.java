package com.honraepoder.corteosfios;

import com.honraepoder.corteosfios.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity
{
	
	
	public static boolean sair = false;
	
	 public void onBackPressed()
	 {
		  if(sair == true)
		   {
		   int pid = android.os.Process.myPid();
		   android.os.Process.killProcess(pid);		 
		   }
		   
		   sair = true;
		 
		
	 }
		 
	
	 
	
	public static final String TAG = "Fios";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		
		super.onCreate(savedInstanceState);
		
		View ViewInstance = new ViewLanguage(this);
		
		setContentView(ViewInstance);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

}
