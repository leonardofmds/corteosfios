package com.honraepoder.corteosfios;


import java.util.Random;

import com.honraepoder.corteosfios.R;

import com.honraepoder.corteosfios.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ViewLanguage extends View implements Runnable
{
	
	private Fios language1, language2;
	
	private boolean resize = true;
	
	Context context;
	
	public ViewLanguage(Context context)
	{
		
		super(context);
		
		this.context = context;
							
		language1 = new Fios(context, "English", 0, 0);
		language2 = new Fios(context, "Portuguese", 0, 0);
		
		
		Thread thread = new Thread(this);
		thread.start();
	}

	public boolean onTouchEvent(MotionEvent event)	
	{
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		Log.i("Fios", "x: "+x +"y: "+ y);
		
		
		if(x >= language1.getPosition().x && x <(language1.getPosition().x + language1.getImage().getWidth()) 
				&& y >= language1.getPosition().y && y <(language1.getPosition().y + language1.getImage().getHeight()))
				{
					Activity act = (Activity) context;
						act.setContentView(new ViewEnglish(act));	
				}
				
				if(x >= language2.getPosition().x && x <(language2.getPosition().x + language2.getImage().getWidth()) 
				&& y >= language2.getPosition().y && y <(language2.getPosition().y + language2.getImage().getHeight()))
				{
					Activity act = (Activity) context;
				     act.setContentView(new ViewPortuguese(act));	
				}	
		
	
		
		return super.onTouchEvent(event);
	}

	@Override
	public void run() 
	{
		
		while (true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				Log.e("jogo", "interrupcao do run()");
			}
			
			update();
			postInvalidate();
		}

	}
	
	public void draw(Canvas canvas) 
	{
		super.draw(canvas);
	
		
		canvas.drawBitmap(language1.getImage(), language1.getPosition().x,language1.getPosition().y, new Paint());
		canvas.drawBitmap(language2.getImage(), language2.getPosition().x,language2.getPosition().y, new Paint());
		
		

		if(resize == true)
		{
			language1.imageResize(Bitmap.createScaledBitmap(language1.getImage(), getWidth(), (int) (getHeight()/6.3f ), false));
			language1.positionResize(0, getHeight()/2 -language1.getImage().getHeight()/2- language1.getImage().getHeight());
			
			language2.imageResize(Bitmap.createScaledBitmap(language2.getImage(), getWidth(), (int) (getHeight()/6.3f ), false));
			language2.positionResize(0, getHeight()/2 +language2.getImage().getHeight()/2- language2.getImage().getHeight());
			
			resize = false;
			
		}
		
		

		
		
	
	}
	
	private void update()
	{
		// TODO Auto-generated method stub
		
		
	}
	
	public void switchActivity(Activity to, Class<?> from)
    {
            Intent intent = new Intent(to, from);
            to.startActivity(intent);       
   }
	

}
