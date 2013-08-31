package com.example.corteosfios;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.Log;

public class Fios
{
	private PointF position;
	private Bitmap imagem;
	private String nomeDaImagem;
		
	public Fios( Context context, String nomeDaImagem, float positionX, float positionY)
	{
		this.position = new PointF(positionX,positionY);
		
		this.nomeDaImagem = nomeDaImagem;
		
		try {
			InputStream is = context.getAssets().open(nomeDaImagem+".png");
			this.imagem = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Carregar Imagem", e.toString());
		}
		
	}
	
	public Bitmap getImage()
	{
		return this.imagem;
		
	}
	
	public void setImage(Context context, String nomeDaImagem)
	{ 
		try {
			InputStream is = context.getAssets().open(nomeDaImagem+".png");
			this.imagem = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Carregar Imagem", e.toString());
		}
		
	}
	
	public PointF getPosition()
	{
	return this.position;
	}
	
	public String imageName()
	{
		return this.nomeDaImagem;
	}
	
	public void imageResize(Bitmap bitmap)
	{
		imagem = bitmap;
	}
	
	public void positionResize(float positionX,float positionY)
	{
		this.position = new PointF(positionX,positionY);
		
	}
}
