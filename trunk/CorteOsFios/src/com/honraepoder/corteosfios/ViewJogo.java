package com.honraepoder.corteosfios;


import java.util.Random;

import com.honraepoder.corteosfios.R;

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

	private Fios[] fios = new Fios[12]; 

	
	private Fios[] sequencia = new Fios[12]; 
	private Fios[] telas = new Fios[3];
	private Fios bomba, menu, botaoJogar, botaoHonraEpoder, botaoCreditos, botaoMenu, backgroundJogo, creditos, coliderFacebook, coliderHonraEpoder;
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
		
	int totalDeMortes;
	float tempoGasto;
	
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
			fios[j].setCortou(false);
			
			
		}
		resizeFios();
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
		
		sequencia[0] = new Fios(context, "Amarelo", 0,300);
		sequencia[1] = new Fios(context, "Azul", 40,300);
		sequencia[2] = new Fios(context, "Vermelho", 80,300);
		sequencia[3] = new Fios(context, "Preto", 120,300);
		sequencia[4] = new Fios(context, "Verde", 160,300);
		sequencia[5] = new Fios(context, "Cinza", 200,300);
		sequencia[6] = new Fios(context, "Laranja", 240,300);
		sequencia[7] = new Fios(context, "Rosa", 280,300);
		sequencia[8] = new Fios(context, "Roxo", 320,300);
		sequencia[9] = new Fios(context, "Marrom", 360,300);
		sequencia[10] = new Fios(context, "Dourado", 400,300);
		sequencia[11] = new Fios(context, "Azul Marinho", 440,300);
	
		bomba = new Fios(context, "Bomba",0,0);
		
		coliderFacebook = new Fios(context, "Facebook",0,0);
		coliderHonraEpoder = new Fios(context, "HonraEpoder",0,0);
		
		menu = new Fios(context,"Menu",0,0);
		botaoJogar = new Fios(context,"botaoJogar",170,300);
		botaoHonraEpoder = new Fios(context,"botaoHonraEpoder",0,550);
		botaoMenu = new Fios(context, "botaoMenu", 340, 0);
		botaoCreditos = new Fios(context, "botaoCreditos",0,0);
	
		backgroundJogo = new Fios(context,"Background",0,0);
		creditos = new Fios(context, "Creditos",0,0);
		
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
		
		for(int i=0; i<sequencia.length; i++)
		{
			sequencia[i].setImage(context, ordem[i]);
		}
				
		Thread thread = new Thread(this);
		thread.start();
	}

	public boolean onTouchEvent(MotionEvent event)	
	{
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		Log.i("Fios", "x: "+x +"y: "+ y);
		
					
		
		
		
		if(maquinaDeEstado == 0)
		{
			if(time<80+maisFios*6)
			{
				time=80+maisFios*6;
			}
		}
		
		
		if(maquinaDeEstado == 1) //se perdeu
		{	//v.vibrate(300);
			
			redesenhar();
					
			numeroDoClick = 0;
			time = 0;
			ultimoFio = ordem[0];
			maquinaDeEstado = 0;					
		}
		
		if(maquinaDeEstado ==4)
		{
			if(x >= coliderFacebook.getPosition().x && x <(coliderFacebook.getPosition().x + coliderFacebook.getImage().getWidth()) 
			&& y >= coliderFacebook.getPosition().y && y <(coliderFacebook.getPosition().y + coliderFacebook.getImage().getHeight()))
				{
					Hiperlink("https://www.facebook.com/leonardofmds");
				}
					
			if(x >= coliderHonraEpoder.getPosition().x && x <(coliderHonraEpoder.getPosition().x + coliderHonraEpoder.getImage().getWidth()) 
			&& y >= coliderHonraEpoder.getPosition().y && y <(coliderHonraEpoder.getPosition().y + coliderHonraEpoder.getImage().getHeight()))
				{
					Hiperlink("https://www.facebook.com/honraepoder");
				}		
		}
		
		if(maquinaDeEstado ==3) // se menu
		{
			
			if(x >= botaoCreditos.getPosition().x && x <(botaoCreditos.getPosition().x + botaoCreditos.getImage().getWidth()) 
			&& y >= botaoCreditos.getPosition().y && y <(botaoCreditos.getPosition().y + botaoCreditos.getImage().getHeight()))
					{
						maquinaDeEstado = 4;
					}
			
		if(x >= botaoJogar.getPosition().x && x <(botaoJogar.getPosition().x + botaoJogar.getImage().getWidth()) 
		&& y >= botaoJogar.getPosition().y && y <(botaoJogar.getPosition().y + botaoJogar.getImage().getHeight()))
			{
				maquinaDeEstado = 0;
				time = 0;
	
			}
		if(x >= botaoHonraEpoder.getPosition().x && x <(botaoHonraEpoder.getPosition().x + botaoHonraEpoder.getImage().getWidth()) 
		&& y >= botaoHonraEpoder.getPosition().y && y <(botaoHonraEpoder.getPosition().y + botaoHonraEpoder.getImage().getHeight()))
					{
						Hiperlink("https://play.google.com/store/apps/details?id=com.nave.segundaguerra");				
					}
		
		
		}
		
		if(maquinaDeEstado == 4) //se creditos
		{
						
			if(x >= botaoMenu.getPosition().x && x <(botaoMenu.getPosition().x + botaoMenu.getImage().getWidth()) 
			&& y >= botaoMenu.getPosition().y && y <(botaoMenu.getPosition().y + botaoMenu.getImage().getHeight()))
				{
					maquinaDeEstado=3;
					
				}
			
		}
		
		if(maquinaDeEstado ==2) //se venceu
		{
			restartFirstActivity();
		}
		
		for(int i = 0; i<fios.length; i++)
		{
			
			if(maquinaDeEstado ==0 && time>80+maisFios*6)
			{
							
				if(x >= botaoMenu.getPosition().x && x <(botaoMenu.getPosition().x + botaoMenu.getImage().getWidth()) 
				&& y >= botaoMenu.getPosition().y && y <(botaoMenu.getPosition().y + botaoMenu.getImage().getHeight()))
					{
						maquinaDeEstado=3;
						numeroDoClick = 0;
						//setBackgroundColor(Color.BLACK);
						redesenhar();
						break;
						
					}
				
				
				
			if(x >= fios[i].getPosition().x && x <(fios[i].getPosition().x + fios[i].getImage().getWidth()) 
			&& y >= fios[i].getPosition().y && y <(fios[i].getPosition().y + fios[i].getImage().getHeight()))
				{
				
				if(fios[i].getCortou() == false)
				{
				
				Log.i(MainActivity.TAG, "Tocou no fio " + fios[i].imageName());
				
				fios[i].setImage(super.getContext(), fios[i].imageName()+"Cortado");
				fios[i].imageResize(Bitmap.createScaledBitmap(fios[i].getImage(), getWidth()/14, getHeight()/2, true));
				
			
				
				ultimoFio = fios[i].imageName();
				
				Log.i("Fios", ultimoFio + " --- "+ ordem[numeroDoClick]);
				
				if(ordem[numeroDoClick] != ultimoFio)
				{
					Log.i(MainActivity.TAG, "EXPLODIU!!!!");
					//perdeu
					totalDeMortes++;
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
					
					break;
					
					}
				

				fios[i].setCortou(true);
				
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
			resizeFios();
			resizeElements = false;
			
		}
		
		
		canvas.drawBitmap(backgroundJogo.getImage(),backgroundJogo.getPosition().x,backgroundJogo.getPosition().y, new Paint());
		//canvas.drawText("Tela: "+this.getWidth()+ "x"+this.getHeight()+ "  "+time, 20, 20, new Paint());
		
		
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
		
		canvas.drawText(temp+(int)tempo, getWidth()/2.4f, getHeight()/5.2f, paintCronometro);
					
		}
		if(maquinaDeEstado == 1)
		{
			//perdeu
			canvas.drawBitmap(telas[0].getImage(), telas[0].getPosition().x, telas[0].getPosition().y, new Paint());
			//canvas.drawText("TOQUE NA TELA PARA TENTAR DE NOVO" , 1, getHeight()-getHeight()/30, paintCronometro);
		}
		
		if(maquinaDeEstado == 2)
		{
			//venceu
			canvas.drawBitmap(telas[1].getImage(), telas[1].getPosition().x, telas[1].getPosition().y, new Paint());
			//canvas.drawText("Tempo Gasto: "+(int)tempoGasto+"s",1,getHeight()/6, paint);
			canvas.drawText("Total de Mortes: "+(int)totalDeMortes+" | "+"Tempo Gasto: "+(int)tempoGasto+"s",1,getHeight()/5, paint);
		}
		if(maquinaDeEstado == 0)
		{
			
			canvas.drawBitmap(botaoMenu.getImage(),botaoMenu.getPosition().x,botaoMenu.getPosition().y, new Paint());
		}
		
		if(time < (80+maisFios*6))
		{
			
			canvas.drawBitmap(telas[2].getImage(), telas[2].getPosition().x, telas[2].getPosition().y, new Paint());
			//canvas.drawText("A SEQUENCIA DE FIOS A SER CORTADA �:" , 1, getHeight()/6.5f,paint);
			for(int i = 0; i< ordem.length-9+maisFios; i++)
			{
				canvas.drawBitmap(sequencia[i].getImage(), 1,getHeight()/4+(i*getHeight()/19.1f)-sequencia[i].getImage().getHeight(), new Paint());
								
				canvas.drawText(ordem[i], getWidth()/2.6f, getHeight()/4+(i*getHeight()/19.1f), paint);
				if(ordem[i]=="Preto" || ordem[i] == "Azul Marinho")
				{
					paint.setColor(Color.WHITE);
					canvas.drawText(ordem[i], getWidth()/2.6f, getHeight()/4+(i*getHeight()/19.1f), paint);
					paint.setColor(Color.BLACK);
				}
			}
		}
		//canvas.drawText("N�O GIRE A BOMBA" , 1, 700,paint);

		if(maquinaDeEstado == 3) //MenuDoJogo
		{
			canvas.drawBitmap(menu.getImage(),menu.getPosition().x,menu.getPosition().y, new Paint());
			canvas.drawBitmap(botaoJogar.getImage(), botaoJogar.getPosition().x,botaoJogar.getPosition().y, new Paint());
			canvas.drawBitmap(botaoHonraEpoder.getImage(), botaoHonraEpoder.getPosition().x,botaoHonraEpoder.getPosition().y, new Paint());
			canvas.drawBitmap(botaoCreditos.getImage(), botaoCreditos.getPosition().x,botaoCreditos.getPosition().y, new Paint());
			
		}
		
		if(maquinaDeEstado ==4)//tela de creditos
		{
			canvas.drawBitmap(creditos.getImage(), creditos.getPosition().x, creditos.getPosition().y, new Paint());
			canvas.drawBitmap(coliderFacebook.getImage(), coliderFacebook.getPosition().x,coliderFacebook.getPosition().y, new Paint());
			canvas.drawBitmap(coliderHonraEpoder.getImage(), coliderHonraEpoder.getPosition().x,coliderHonraEpoder.getPosition().y, new Paint());

		}
		
		
		//canvas.drawText("width = "+getWidth()+" height = "+ getHeight(), 0,30, new Paint());
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
	 /*Intent i = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());

	 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
	 super.getContext().startActivity(i);*/
		tempo = 20;
		numeroDoClick = 0;
		time = 0;
		maquinaDeEstado = 3;
		totalDeMortes = 0;
		tempoGasto = 0;
		maisFios = 0;

		for(int i = 0; i<ordem.length; i++)
				{
					ordem[i] = fios[i].imageName();
					reDraw[i] = fios[i].imageName();
					
				}

		embaralhar(ordem);
		redesenhar();
		for(int i=0; i<sequencia.length; i++)
		{
			sequencia[i].setImage(getContext(), ordem[i]);
		}


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
		if(maquinaDeEstado == 0  && time>80+maisFios*6)
		{
			tempo-=0.045f;
			tempoGasto+=0.045f;
			
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
	
	public void Hiperlink(String endereco)
	{
		/*String url = endereco;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		getContext().startActivity(intent);*/
		
		Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(endereco));
		getContext().startActivity(browserIntent);
		
	}
	
	
	public void resizeElements()
	{
		paint.setTextSize(getWidth()/18.5f);
		paintCronometro.setTextSize(getWidth()/13);		
		
		botaoCreditos.imageResize(Bitmap.createScaledBitmap(botaoCreditos.getImage(),getWidth() , (int)(getHeight()/6.5f), false ));
		botaoCreditos.positionResize(0, getHeight()/1.5f);
		
		botaoMenu.imageResize(Bitmap.createScaledBitmap(botaoMenu.getImage(),getWidth() , getHeight()/9, false ));
		botaoMenu.positionResize(0, getHeight()-botaoMenu.getImage().getHeight()*1.1f);
		
		botaoHonraEpoder.imageResize(Bitmap.createScaledBitmap(botaoHonraEpoder.getImage(),getWidth() , (int)(getHeight()/5.8f), false ));
		botaoHonraEpoder.positionResize(0,getHeight()- (botaoHonraEpoder.getImage().getHeight()));
		
		botaoJogar.imageResize(Bitmap.createScaledBitmap(botaoJogar.getImage(),getWidth() , (int)(getHeight()/6.5f), false ));
		botaoJogar.positionResize(0, getHeight()/2);
		
		menu.imageResize(Bitmap.createScaledBitmap(menu.getImage(),getWidth() , getHeight(), false ));
		
		
		float razaoFbX = getWidth()/3.17f;
		float razaoFbY = getHeight()/4.7f;
		
		coliderFacebook.imageResize(Bitmap.createScaledBitmap(coliderFacebook.getImage(),(int)(razaoFbX) , (int)(razaoFbY), false)) ;
		
		coliderHonraEpoder.imageResize(Bitmap.createScaledBitmap(coliderHonraEpoder.getImage(),(int)(razaoFbX) , (int)(razaoFbY), false ));


		coliderFacebook.positionResize(getWidth()/9.4f, getHeight()/1.9f);
		coliderHonraEpoder.positionResize(getWidth()/1.8f, getHeight()/1.9f);
		
		creditos.imageResize(Bitmap.createScaledBitmap(creditos.getImage(),getWidth() , getHeight(), false ));
		
		bomba.imageResize(Bitmap.createScaledBitmap(bomba.getImage(),getWidth(), (int) (getHeight()/3), false ));
		
		
		
		backgroundJogo.imageResize(Bitmap.createScaledBitmap(backgroundJogo.getImage(),getWidth() , getHeight(), false ));
		
		telas[0].imageResize(Bitmap.createScaledBitmap(telas[0].getImage(),getWidth() , getHeight(), false ));
		telas[1].imageResize(Bitmap.createScaledBitmap(telas[1].getImage(),getWidth() , getHeight(), false ));
		telas[2].imageResize(Bitmap.createScaledBitmap(telas[2].getImage(),getWidth() , getHeight(), false ));
		
		for(int i=0; i<sequencia.length; i++)
		{
			sequencia[i].imageResize(Bitmap.createScaledBitmap(sequencia[i].getImage(),getWidth() , getHeight()/20, false ));
			
			
		}
		
	}
	
	
	
	

}
