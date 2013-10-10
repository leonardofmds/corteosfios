package com.honraepoder.corteosfios;

import com.honraepoder.corteosfios.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity
{
	
	 public void onBackPressed()
	 {
		   int pid = android.os.Process.myPid();
		   android.os.Process.killProcess(pid);
	 }
	
	public static final String TAG = "Fios";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		
		super.onCreate(savedInstanceState);
		
		View ViewInstance = new ViewJogo(this);
		
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
