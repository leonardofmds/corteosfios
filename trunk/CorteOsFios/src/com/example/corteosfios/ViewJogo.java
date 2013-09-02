package com.example.corteosfios;


import java.util.Random;

import com.example.corteosfios.R;

import android.content.Context;
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

public class ViewJogo extends View implements Runnable
{
	
	
	
	Random rand = new Random();
	Boolean teste = false;
	private Fios[] fios = new Fios[12]; 
	private Fios[] telas = new Fios[3];
	private Fios bomba, menu, botaoJogar, botaoHonraEpoder, botaoMenu;
	private boolean resize = true;
	
	private boolean resizeElements = true;
	
	float tempo = 20;
	
	String ultimoFio;
	String[] ordem = new String[12];
	String[] reDraw = new String[12];
	
	int numeroDoClick;
	Paint paint = new Paint();
	Paint paintCronometro = new Paint();
	int time;
	int maquinaDeEstado = 3;
	int maisFios;
	
	
	MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.sound_file_1);

	
	
	Vibrator v = (Vibrator) super.getContext().getSystemService(Context.VIBRATOR_SERVICE);

	
	MaquinaDeEstados maquinaEstado = MaquinaDeEstados.Jogo;
	

	
	public void embaralhar(String[] arraysDeString)
	{
		int l = 0;
        for (int i = arraysDeString.length - 1; i >= 0; i--)
        {            
           int j = rand.nextInt(arraysDeString.length - l);

            
            String temp = arraysDeString[i];
            arraysDeString[i] = arraysDeString[j];
            arraysDeString[j] = temp;
            l++;
        }
	
	}
	private void redesenhar()
	{
		for(int j = 0; j < fios.length; j++)
		{
			fios[j].setImage(super.getContext(), reDraw[j]);
			
		}
	}
	
	
	public ViewJogo(Context context)
	{
		
		super(context);
		
		//paint.setTextSize(25);
		//paint.setColor(Color.GREEN);
		//paintCronometro.setTextSize(25);
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
		
		menu = new Fios(context,"Menu",0,0);
		botaoJogar = new Fios(context,"botaoJogar",170,300);
		botaoHonraEpoder = new Fios(context,"botaoHonraEpoder",0,550);
		botaoMenu = new Fios(context, "botaoMenu", 340, 0);
		
	
		
		telas[0] = new Fios(context, "Explodiu", 0,0);
		telas[1] = new Fios(context, "Venceu", 0, 0);
		telas[2] = new Fios(context, "Sequencia", 0, 0);
		
				
		for(int i = 0; i<ordem.length; i++)
		{
			ordem[i] = fios[i].imageName();
			reDraw[i] = fios[i].imageName();
			
			
		}
		
		//embaralhando os fios
		
		embaralhar(ordem);
		
		
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
		
		if(maquinaDeEstado == 1)
		{
			
			//v.vibrate(300);
			
			
			redesenhar();
			
			
			numeroDoClick = 0;
			//ultimoFio = null;
			time = 0;
			ultimoFio = ordem[0];
			maquinaDeEstado = 0;					
		}
		
		
		
		for(int i = 0; i<fios.length; i++)
		{
			if(maquinaDeEstado ==2) //se venceu
			{
				maisFios=0;
				restartFirstActivity();
				//maquinaDeEstado = 3;

				redesenhar();

			}
			
			if(maquinaDeEstado ==3) //menu
			{
			if(x >= botaoJogar.getPosition().x && x <(botaoJogar.getPosition().x + botaoJogar.getImage().getWidth()) 
			&& y >= botaoJogar.getPosition().y && y <(botaoJogar.getPosition().y + botaoJogar.getImage().getHeight()))
				{
					maquinaDeEstado = 0;
					time = 0;
					
				}
			if(x >= botaoHonraEpoder.getPosition().x && x <(botaoHonraEpoder.getPosition().x + botaoHonraEpoder.getImage().getWidth()) 
			&& y >= botaoHonraEpoder.getPosition().y && y <(botaoHonraEpoder.getPosition().y + botaoHonraEpoder.getImage().getHeight()))
						{
				String url = "https://www.facebook.com/honraepoder";
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				super.getContext().startActivity(intent);
					
						}
			
			
			}
			
			if(maquinaDeEstado ==0 && time>80+maisFios*6)
			{
				if(x >= botaoMenu.getPosition().x && x <(botaoMenu.getPosition().x + botaoMenu.getImage().getWidth()) 
				&& y >= botaoMenu.getPosition().y && y <(botaoMenu.getPosition().y + botaoMenu.getImage().getHeight()))
					{
						maquinaDeEstado=3;
						numeroDoClick = 0;
						
						
						redesenhar();
						
					}
				
			if(x >= fios[i].getPosition().x && x <(fios[i].getPosition().x + fios[i].getImage().getWidth()) 
			&& y >= fios[i].getPosition().y && y <(fios[i].getPosition().y + fios[i].getImage().getHeight()))
				{
			
				Log.i(MainActivity.TAG, "Tocou no fio " + fios[i].imageName());
				
				fios[i].setImage(super.getContext(), fios[i].imageName()+"Cortado");
				fios[i].imageResize(Bitmap.createScaledBitmap(fios[i].getImage(), getWidth()/14, getHeight()/2, true));
				
				teste = true;
				
				ultimoFio = fios[i].imageName();
				
				Log.i("Fios", ultimoFio + " --- "+ ordem[numeroDoClick]);
				
				if(ordem[numeroDoClick] != ultimoFio)
				{
					Log.i(MainActivity.TAG, "EXPLODIU!!!!");
					//perdeu
					maquinaDeEstado = 1;
					mp.start();

					break;
									
				}
				numeroDoClick ++;	
				
				if(numeroDoClick ==12)
				{
					maquinaDeEstado = 2;
					break;
				}
				
				if(numeroDoClick == (3+maisFios)) 
					{
					//venceu
										
					//restaurar as imagens
					//aumentar maisFios
					//zerar numeroDoClick
					
					
					
					
					maisFios++;
					
					
					redesenhar();
					
					numeroDoClick = 0;
					//ultimoFio = null;
					time = 0;
					tempo = 20+(maisFios*2);
					maquinaDeEstado = 0;
					
					
					}
					
				}
			
			}
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
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		if(resizeElements == true)
		{
			resizeElements();
			resizeElements = false;
		}
		
		resizeFios();
		
		
		canvas.drawText("Tela: "+this.getWidth()+ "x"+this.getHeight()+ "  "+time, 20, 20, new Paint());
	
		
		if(teste == true)
		{
		canvas.drawText("TOCOU NO FIO "+ ultimoFio , 1, 600, new Paint());
		teste = false;			
			
		}
		
		for(int i = 0; i< fios.length; i++)
		{
		if(fios[i]!= null)
		{
	
		canvas.drawBitmap(fios[i].getImage(), fios[i].getPosition().x, fios[i].getPosition().y, new Paint());

		
		}
		
		canvas.drawBitmap(bomba.getImage(), bomba.getPosition().x,bomba.getPosition().y, new Paint());
		String temp = "00:";
		if(tempo<10)
		{
			temp = "00:0";
		}
		
		canvas.drawText(temp+(int)tempo, getWidth()/1.6f, getHeight()/4.25f, paintCronometro);
		
		}
		if(maquinaDeEstado == 1)
		{
			//perdeu
			
			canvas.drawBitmap(telas[0].getImage(), telas[0].getPosition().x, telas[0].getPosition().y, new Paint());
			canvas.drawText("TOQUE NA TELA PARA TENTAR DE NOVO" , 1, getHeight()/7*6, paintCronometro);
		}
		
		if(maquinaDeEstado == 2)
		{
			//venceu
			
			canvas.drawBitmap(telas[1].getImage(), telas[1].getPosition().x, telas[1].getPosition().y, new Paint());

		}
		if(maquinaDeEstado == 0)
		{
			canvas.drawBitmap(botaoMenu.getImage(),botaoMenu.getPosition().x,botaoMenu.getPosition().y, new Paint());
		}
		
		if(time < (80+maisFios*6))
		{
			
			canvas.drawBitmap(telas[2].getImage(), telas[2].getPosition().x, telas[2].getPosition().y, new Paint());
			
			canvas.drawText("A SEQUENCIA DE FIOS A SER CORTADA É:" , 1, getHeight()/6.5f,paint);
			for(int i = 0; i< ordem.length-9+maisFios; i++)
			{
				
				canvas.drawText(ordem[i], 1, getHeight()/4+(i*20), paint);
				
			}
		}
		
		
		//canvas.drawText("NÃO GIRE A BOMBA" , 1, 700,paint);
		
		if(maquinaDeEstado == 3) //MenuDoJogo
		{
			canvas.drawBitmap(menu.getImage(),menu.getPosition().x,menu.getPosition().y, new Paint());
			canvas.drawBitmap(botaoJogar.getImage(), botaoJogar.getPosition().x,botaoJogar.getPosition().y, new Paint());
			canvas.drawBitmap(botaoHonraEpoder.getImage(), botaoHonraEpoder.getPosition().x,botaoHonraEpoder.getPosition().y, new Paint());

			
		}
		
		
		
		//fios[0].imageResize(BitmapFactory.decodeResource(getResources(), R.drawable.vermelho_cortado));
	}
	
	private void update()
	{
		// TODO Auto-generated method stub


		time++;
		cronometro();
		
	}
	
	private void restartFirstActivity()
	 {
	 Intent i = getContext().getPackageManager()
	 .getLaunchIntentForPackage(getContext().getPackageName() );

	 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
	 super.getContext().startActivity(i);
	 }
	
	private void resizeFios()
	{
		if(resize == true)
		{
			
			for(int i = 0; i<fios.length; i++)
			{	
				fios[i].positionResize(getWidth()/12*i, getHeight()/3);
				fios[i].imageResize(Bitmap.createScaledBitmap(fios[i].getImage(),getWidth()/14 , getHeight()/2, false ));
				
				if(i == fios.length)
				{
					resize = false;
				}
			}
		}
		
		
		
	}
	
	private void cronometro()
	{
		if(maquinaDeEstado == 0)
		{
			tempo-=0.045f;
		
			if(tempo <= 0)
			{
				maquinaDeEstado = 1;
				mp.start();
			
			}
		}
		else
		{
			tempo = 20+maisFios;
		}
		
	}
	
	public void resizeElements()
	{
		paint.setTextSize(getWidth()/19.5f);
		paintCronometro.setTextSize(getWidth()/19.5f);
		
		botaoMenu.imageResize(Bitmap.createScaledBitmap(botaoMenu.getImage(),getWidth()/3 , getHeight()/9, false ));
		botaoMenu.positionResize(getWidth()-botaoMenu.getImage().getWidth(), 0);
		
		botaoHonraEpoder.imageResize(Bitmap.createScaledBitmap(botaoHonraEpoder.getImage(),getWidth() , getHeight()/5, false ));
		botaoHonraEpoder.positionResize(0,getHeight()- (botaoHonraEpoder.getImage().getHeight()*1.5f));
		
		botaoJogar.imageResize(Bitmap.createScaledBitmap(botaoJogar.getImage(),getWidth()/3 , getHeight()/6, false ));
		botaoJogar.positionResize(getWidth()/3, getHeight()/2.5f);
		
		menu.imageResize(Bitmap.createScaledBitmap(menu.getImage(),getWidth() , getHeight(), false ));
		
		bomba.imageResize(Bitmap.createScaledBitmap(bomba.getImage(),getWidth(), getHeight()/4, false ));
		bomba.positionResize(0, getHeight()/7);
		
		telas[0].imageResize(Bitmap.createScaledBitmap(telas[0].getImage(),getWidth() , getHeight(), false ));
		telas[1].imageResize(Bitmap.createScaledBitmap(telas[1].getImage(),getWidth() , getHeight(), false ));
		telas[2].imageResize(Bitmap.createScaledBitmap(telas[2].getImage(),getWidth() , getHeight(), false ));
	}
	

}
