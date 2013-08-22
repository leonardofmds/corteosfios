package com.example.corteosfios;


import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ViewJogo extends View implements Runnable
{
	
	Random rand = new Random();
	Boolean teste = false;
	private Fios[] fios = new Fios[12]; 
	private Fios[] telas = new Fios[3];
	private Fios bomba;
	
	String ultimoFio;
	String[] ordem = new String[12];
	String[] reDraw = new String[12];
	int numeroDoClick;
	Paint paint = new Paint();
	Paint paintCronometro = new Paint();
	int time;
	int maquinaDeEstado;
	int maisFios;
	

	
	
	public void embaralhar()
	{
		int l = 0;
        for (int i = ordem.length - 1; i >= 0; i--)
        {            
           int j = rand.nextInt(ordem.length - l);

            
            String temp = ordem[i];
            ordem[i] = ordem[j];
            ordem[j] = temp;
            l++;
        }
	
	}
		
	public ViewJogo(Context context)
	{	
		super(context);
		paint.setTextSize(30);
		paint.setColor(Color.GREEN);
		paintCronometro.setTextSize(20);
		paintCronometro.setColor(Color.RED);
			
	
		fios[0] = new Fios(context, "Amarelo", 0,300);
		fios[1] = new Fios(context, "Azul", 40,300);
		fios[2] = new Fios(context, "Vermelho", 80,300);
		fios[3] = new Fios(context, "Preto", 120,300);
		fios[4] = new Fios(context, "Verde", 160,300);
		fios[5] = new Fios(context, "Cinza", 200,300);
		fios[6] = new Fios(context, "Laranja", 240,300);
		fios[7] = new Fios(context, "Rosa", 280,300);
		fios[8] = new Fios(context, "Roxo", 320,300);
		fios[9] = new Fios(context, "Marrom", 360,300);
		fios[10] = new Fios(context, "Dourado", 400,300);
		fios[11] = new Fios(context, "Azul Marinho", 440,300);
	
		bomba = new Fios(context, "Bomba",0,95);
		
		//fios[5] = new Fios(context, "Bomba", 50,0);
		
		telas[0] = new Fios(context, "Explodiu", 0,0);
		telas[1] = new Fios(context, "Venceu", 0, 0);
		telas[2] = new Fios(context, "Sequencia", 0, 0);
		
				
		for(int i = 0; i<ordem.length; i++)
		{
			ordem[i] = fios[i].imageName();
			reDraw[i] = fios[i].imageName();
			
		}
		
		//embaralhando os fios
		
		embaralhar();
		
		
		//altura e largura ainda não definidos no construtor, encontrar uma maneira de utilizar getWidth e getHeight
			
			//fios[0] = new Fios(context, "Amarelo", rand.nextInt(this.getWidth()),this.getHeight()/2);
			//fios[1] = new Fios(context, "Azul", rand.nextInt(this.getWidth()),this.getHeight()/2);
			//fios[2] = new Fios(context, "Vermelho", rand.nextInt(this.getWidth()),this.getHeight()/2);
			//fios[3] = new Fios(context, "Preto", rand.nextInt(this.getWidth()),this.getHeight()/2);
			//fios[4] = new Fios(context, "Verde", rand.nextInt(this.getWidth()),this.getHeight()/2);
			//fios[5] = new Fios(context, "Bomba", rand.nextInt(this.getWidth()/2),this.getHeight()/3);
		
		
				
		Thread thread = new Thread(this);
		thread.start();
	}

	

	public boolean onTouchEvent(MotionEvent event)	
	{
		int x = (int) event.getX();
		int y = (int) event.getY();
		Log.i("Fios", "x: "+x +"y: "+ y);
		
		
		for(int i = 0; i<fios.length; i++)
		{
			if(maquinaDeEstado ==0)
			{
			if(x >= fios[i].getPosition().x && x <(fios[i].getPosition().x + fios[i].getImage().getWidth()) 
			&& y >= fios[i].getPosition().y && y <(fios[i].getPosition().y + fios[i].getImage().getHeight()))
				{
			
				Log.i(MainActivity.TAG, "Tocou no fio " + fios[i].imageName());
				
				fios[i].setImage(super.getContext(), fios[i].imageName()+"Cortado");
							
				teste = true;
				
				ultimoFio = fios[i].imageName();
				
				Log.i("Fios", ultimoFio + " --- "+ fios[i].imageName());
				
				if(ordem[numeroDoClick] != ultimoFio)
				{
					Log.i(MainActivity.TAG, "EXPLODIU!!!!");
					//perdeu
					maquinaDeEstado = 1;
									
				}
				numeroDoClick ++;	
				
				if(numeroDoClick == (5+maisFios))
					{
					//venceu
					maquinaDeEstado = 2;
					
					//restaurar as imagens
					//aumentar maisFios
					//zerar numeroDoClick
					
					maisFios++;
					
					
					for(int j = 0; j < fios.length; j++)
					{
						fios[j].setImage(super.getContext(), reDraw[j]);
						
					}
					
					numeroDoClick = 0;
					//ultimoFio = null;
					time = 0;
					maquinaDeEstado = 0;
					
					}
					
				}
			
			}
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public void run() {
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
	
	public void draw(Canvas canvas) {
		super.draw(canvas);		
		
		canvas.drawText("Tela: "+this.getWidth()+ "x"+this.getHeight()+ "  "+time, 20, 20, new Paint());
	
		
		if(teste == true)
		{
		canvas.drawText("TOCOU NO FIO "+ ultimoFio , 1, 600, new Paint());
		teste = false;			
			
		}
		
		for(int i = 0; i< fios.length; i++)
		{
		if(fios[i]!= null)
			
		canvas.drawBitmap(fios[i].getImage(), fios[i].getPosition().x, fios[i].getPosition().y, new Paint());
		
		canvas.drawBitmap(bomba.getImage(), bomba.getPosition().x,bomba.getPosition().y, new Paint());

		}
		if(maquinaDeEstado == 1)
		{
			//perdeu
			
			canvas.drawBitmap(telas[0].getImage(), telas[0].getPosition().x, telas[0].getPosition().y, new Paint());
			
		}
		
		if(maquinaDeEstado == 2)
		{
			//venceu
			
			canvas.drawBitmap(telas[1].getImage(), telas[1].getPosition().x, telas[1].getPosition().y, new Paint());
			
			
				
			
		}
		canvas.drawText(""+time, 300, 175, paintCronometro);
		
		if(time < (80+maisFios*10))
		{
			
			canvas.drawBitmap(telas[2].getImage(), telas[2].getPosition().x, telas[2].getPosition().y, new Paint());
			
			canvas.drawText("A SEQUENCIA DE FIOS A SER CORTADA É:" , 1, 400,paint);
			for(int i = 0; i< ordem.length-7+maisFios; i++)
			{
				canvas.drawText(ordem[i], 1, 450+(i*20), paint);
				
			}
		}
		
		canvas.drawText("GIRE O CELULAR PARA REINICIAR" , 1, 700,paint);
		
		
		}
	
		


	private void update()
	{
		// TODO Auto-generated method stub
		time ++;
		
	}
	
	
	

}
