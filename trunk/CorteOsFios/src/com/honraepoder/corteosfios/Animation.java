package com.honraepoder.corteosfios;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.Log;



public class Animation
{
	private int numAnim;
	private Bitmap imagem;
	private PointF position;
	
	
	public void imageResize(Bitmap bitmap)
	{
		imagem = bitmap;
	}
	
	public Animation(Context context)
	{
		this.position = new PointF(0,0);		
		
		try {
			InputStream is = context.getAssets().open("explosao"+ numAnim +".png");
			this.imagem = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Carregar Imagem", e.toString());
		}
		
		
		
	}
	
}
