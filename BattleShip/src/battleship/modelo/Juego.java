package battleship.modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;



public class Juego extends ObservableRemoto implements IJuego {
	private jugador[] jugadores=new jugador[2];
	private boolean IA=false;
	//private ArrayList<Observador> misObservadores = new ArrayList<>();
	private int maximoBarcos=5;
	//--IA info
	private String direccion="";
	private int fInit;
	private int cInit;
	private int fant;
	private int cant;
	private boolean target;
	//---
	int turno;
	int noturno;
	private String recorrido;
	
	
	
	@Override
	public String getJugador(int i) throws RemoteException  {
		if (i==1 ||i==0) {
		return this.jugadores[i].getNombre();
		}else {
			return "error";
		}
		
	}
	
	@Override
	public void comenzar() throws RemoteException {
		//notificarCambio(EventosBatallaNaval.JUEGOCREADO);
		notificarObservadores(EventosBatallaNaval.JUEGOCREADO);
	}
	
	@Override
	public void iniciarJuego(String nombre) throws RemoteException {
		if (this.jugadores[0]==null) {
			jugador jugador1=new jugador(nombre);
			this.jugadores[0]=jugador1;
			notificarMensaje(nombre,"esperando");
			notificarObservadores(EventosBatallaNaval.JUGADORAGREGADOP2);
		}else {
			if	(this.jugadores[0].getNombre().equals(nombre)) {
				System.out.println("error duplicate name");
				return;
			}
			if (this.jugadores[1]!=null) {
				System.out.println("All players connected");
				return;
			}
			jugador jugador2=new jugador(nombre);
			this.jugadores[1]=jugador2;
			this.turno=0;
			this.noturno=1;
			//notificarCambio(EventosBatallaNaval.JUEGADORESAGREGADOS);
			notificarObservadores(EventosBatallaNaval.JUGADORAGREGADOP1);
			if (this.IA==true) {
				System.out.println(this.IA);
				this.randomizar("IA");
			}
		}
	}
	
	/*
	public void addObservador(Observador observador) {
		misObservadores.add(observador);
		
	
	
	private void notificarCambio(EventosBatallaNaval evento) {
		for (Observador o : misObservadores) {
			o.notificar(evento);
		}

	}*/
	
	@Override
	public  Collection<Barco> barcosColocados(String nombre) throws RemoteException{
		Collection<Barco> b;
		if (this.jugadores[0].getNombre().equals(nombre)) {
			b=this.jugadores[0].getBarcosJugador().barcoAflote();
		} else {
			b =this.jugadores[1].getBarcosJugador().barcoAflote();
		}
		return b;
		
	}
	
	@Override
	public boolean IsTodosBarcosColocados() throws RemoteException {
		if (this.jugadores[turno].getBarcosJugador().barcoAflote().size()== this.maximoBarcos&&
				this.jugadores[noturno].getBarcosJugador().barcoAflote().size()== this.maximoBarcos) {
			//notificarCambio(EventosBatallaNaval.TODOSLOSBARCOSCOLOCADOS);
			notificarObservadores(EventosBatallaNaval.TODOSLOSBARCOSCOLOCADOS);
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String aQuienLeTocaJugar() throws RemoteException{
		return jugadores[turno].getNombre();
	}
	
	public String turnoSiguiente() throws RemoteException{
		return jugadores[noturno].getNombre();
	}
	
	public Boolean todosLosbarcos(int j) {
		if (this.jugadores[j].getBarcosJugador().barcoAflote().size()==this.maximoBarcos){
			return true;
		}
		return false;
	}
	
	@Override
	public String colocarPiezas(int f, int c,String direccion,int tam,String j,boolean flagRandom) throws RemoteException {
		String mensaje="";
		Barco barco=new Barco(tam);
		int jugador;
		if (this.jugadores[0].getNombre().equals(j)) {
			jugador=0;
		}else {
			jugador=1;
		}
		 mensaje=this.jugadores[jugador].getBarcosJugador().AgregarBarco(f, c, direccion, barco);
		if (flagRandom) {
			if (this.todosLosbarcos(0) && this.todosLosbarcos(1)) {
				notificarObservadores(EventosBatallaNaval.TODOSLOSBARCOSCOLOCADOS);
			}else if(this.todosLosbarcos(0) && this.jugadores[0].getNombre().equals(j)) {
				notificarObservadores(EventosBatallaNaval.BARCOAGREGADOP2);
			}else if(this.todosLosbarcos(1) && this.jugadores[1].getNombre().equals(j)) {
				notificarObservadores(EventosBatallaNaval.BARCOAGREGADOP1);
			}
		}else {
		notificarMensaje(this.jugadores[jugador].getNombre(),mensaje);
		if (this.todosLosbarcos(0) && this.todosLosbarcos(1)) {
			//notificarCambio(EventosBatallaNaval.TODOSLOSBARCOSCOLOCADOS);
			notificarObservadores(EventosBatallaNaval.TODOSLOSBARCOSCOLOCADOS);
		}else if(this.jugadores[0].getNombre().equals(j)) {
			//notificarCambio(EventosBatallaNaval.BARCOAGREGADOP2);	
			notificarObservadores(EventosBatallaNaval.BARCOAGREGADOP2);
		}else if(this.jugadores[1].getNombre().equals(j)) {
			//notificarCambio(EventosBatallaNaval.BARCOAGREGADOP1);	
			notificarObservadores(EventosBatallaNaval.BARCOAGREGADOP1);
		}}
	
		return mensaje;
	}
	

	@Override
	public String finPartida() throws RemoteException{
		String mensaje;
		if (this.jugadores[noturno].getBarcosJugador().barcoAflote().size()==0){
			mensaje="Ganador "+this.jugadores[turno].getNombre();
		} else {
			mensaje="partida continua";
		}
		return mensaje;
	}
	
	
	@Override
	public String[][] getTableroRadar(String nombre)throws RemoteException {
		
		String[][] m=new String[10][10];
		TableroRadar t;
		if (this.jugadores[0].getNombre().equals(nombre)) {
			t= this.jugadores[0].getRadarJugador();
		} else {
			t =this.jugadores[1].getRadarJugador();
		}
		
		int f=0;
		int c=0;
		while (f<=9) {
			while (c<=9) {
				if (t.GetCasilla(f, c).isExplorada()) {
					m[f][c]= t.GetCasilla(f, c).GetValor();
				}else {
					m[f][c]= "";
				}
				c++;
			}
			c=0;
			f++;
		}
		return m;
		
	}
	
	@Override
	public String[][] getTableroBarcos(String nombre)throws RemoteException {
		String[][] m=new String[10][10];
		TableroBarcos t;
		if (this.jugadores[0].getNombre().equals(nombre)) {
			t= this.jugadores[0].getBarcosJugador();
		} else {
			t =this.jugadores[1].getBarcosJugador();
		}
		
		int f=0;
		int c=0;
		while (f<=9) {
			while (c<=9) {
				m[f][c]= t.GetCasilla(f, c).GetValor();
				c++;
			}
			f++;
			c=0;
		}
		return m;
	}
	

	private void notificarMensaje(String destino,String mensaje) throws RemoteException {
		/*
		for (Observador o : misObservadores) {
			o.notificarmensaje(mensaje);
		}*/
		notificarObservadores(destino+"-"+mensaje);
	}
	
	public void marcarDisparo(String name, int f, int c) throws RemoteException {
		this.notificarMensaje("disparo"+name, f+"x"+c);
	}
	
	@Override
	public String  Turnojugador(int f,int c,String j) throws RemoteException {
		String mensaje = null;
		if (this.jugadores[turno].getNombre().equals(j)) {
		mensaje=this.jugadores[turno].getRadarJugador().hacerDisparo(f, c, this.jugadores[noturno].getBarcosJugador());
		if (this.aQuienLeTocaJugar().equals("IA")) {
			this.estrategiaIA(mensaje,f,c);
		}
		marcarDisparo(this.jugadores[noturno].getNombre(),f,c);
		notificarMensaje("all",mensaje);
		if (this.finPartida().equals("partida continua")==false) {
			//notificarCambio(EventosBatallaNaval.FINPARTIDA);
			notificarObservadores(EventosBatallaNaval.FINPARTIDA);
		}else {
		if (mensaje.equals("agua")) {
			this.CambiarTurno();
			//notificarCambio(EventosBatallaNaval.TURNODEJUGADOR);
			notificarObservadores(EventosBatallaNaval.TURNODEJUGADOR);
			
		}	else {
		
			//notificarCambio(EventosBatallaNaval.TURNODEJUGADOR);
			notificarObservadores(EventosBatallaNaval.TURNODEJUGADOR);
		}
		}}
		return mensaje;
	}
	

	


	private void estrategiaIA(String mensaje, int f, int c) {
			if	(mensaje.equals("barco dañado")){
				if (this.target==false) {
					this.target=true;
					this.cInit=c;
					this.fInit=f;
					this.direccion="";
				}else {
					if (this.fInit==f) {
						this.direccion="h";
					}else {
						this.direccion="v";
					}
				}
			}else if (mensaje.equals("agua")==false){
				this.target=false;
			}
	}

	public void CambiarTurno() {
		if (this.turno==0) {
			this.turno=1;
			this.noturno=0;
		}else {
			this.turno=0;
			this.noturno=1;
		}
	}


	private void colocarbarcosRandom(String name) throws RemoteException {
		int tam=this.barcosColocados(name).size();
		if (tam==0) {
			tam=5;
		}else if (tam==1) {
			tam=4;
		}else if (tam==2) {
			tam=3;
		}else if (tam==3) {
			tam=3;
		}else  {
			tam=2;
		}
		
		String direccion;
		Random r= new Random() ;
		int f = r.nextInt(10);
		r= new Random() ;
	    int	c= r.nextInt(10);
	    r= new Random() ;
	    int d=r.nextInt(2);
		if (d==1) {
			direccion="horizontal";
		}else {
			direccion="vertical";
		}
		String m=colocarPiezas(f, c, direccion, tam,name,true);
		while (m.equals("BARCO NO ENTRA EN EL TABLERO")) {
			r= new Random() ;
		    f = r.nextInt(10);
			r= new Random() ;
		    c= r.nextInt(10);
		    r= new Random() ;
		    d=r.nextInt(2);
			if (d==1) {
				direccion="horizontal";
			}else {
				direccion="vertical";
			}
			m=colocarPiezas(f, c, direccion, tam,name,true);
		}

	}

	
	
	
	@Override
	public String barcosDestruidos(String name) throws RemoteException {
		if (this.jugadores[0].getNombre().equals(name)) {
			return this.jugadores[0].getBarcosJugador().barcoAfloteString();
		} else {
			return this.jugadores[1].getBarcosJugador().barcoAfloteString();
		}
	}
	
	@Override
	public String barcosDestruidosOponente(String name) throws RemoteException{
		if (this.jugadores[0].getNombre().endsWith(name)) {
			return this.jugadores[1].getBarcosJugador().barcoAfloteString();
		} else {
			return this.jugadores[0].getBarcosJugador().barcoAfloteString();
		}
	}

	@Override
	public void iniciarJuegoVSIA(String name) throws RemoteException {
		this.IA=true;
		this.iniciarJuego("IA");
		this.iniciarJuego(name);
	}
	
	public void randomizar(String name) throws RemoteException {
		int j = 0;
		boolean loop=true;
		if (this.jugadores[0].getNombre().equals(name)){
			j=0;
		}else	if (this.jugadores[1].getNombre().equals(name)){
			j=1;
		}else {
			loop=false;
		}
		
		while (loop) {
		if ((this.jugadores[j].getBarcosJugador().barcoAflote().size()==this.maximoBarcos)) {
			loop=false;
		}else {
			this.colocarbarcosRandom(name);
		}}
	}
	
	@Override
	public void turnoIA() throws RemoteException {
		if (this.IA && this.aQuienLeTocaJugar().equals("IA")) {
		System.out.println("turno IA"+IA);
		int f = 0;
		int c = 0;
		if (this.target) {
		   if	(direccion.equals("")) {  
			   this.recorrido="derecha";
			   int direccionRandom = 0;
			   boolean exit=false;
			   direccionRandom=0;
			   while (exit==false) {
				   if (direccionRandom==0) {
					   f=this.fInit;
					   c=this.cInit+1;
				   }else  if (direccionRandom==1) {
					   f=this.fInit;
					   c=this.cInit-1;
				   }else  if (direccionRandom==2) {
					   f=this.fInit+1;
					   c=this.cInit;
				   }else if (direccionRandom==3){
					   f=this.fInit-1;
					   c=this.cInit;
				   }else {
					   exit=true;
				   }
				   direccionRandom ++; 
				   if (this.fueraDeRango(f,c)==false) {
					   if (false==this.jugadores[0].getRadarJugador().GetCasilla(f,c).isExplorada()) {
						   exit=true;
					   }
				   }				  
			   }
		   }else{
			   System.out.println("fant "+this.fant+"x"+this.cant);
			   f=this.fInit;
			   c=this.cInit;
			   boolean buscandoProximoDisparo = true;
			   System.out.println("recorrido "+this.recorrido);
			   //String[] salidaForzada = new String[10];
			  // String out="";
			   int s=0;
			   while (buscandoProximoDisparo) {
				   if (direccion.equals("h")) {
					   if (recorrido.equals("derecha")) {
						   f=this.fInit;
						   c++;
					   }else {
						   f=this.fInit;
						   c--;}
				   }
				   if (direccion.equals("v")) {
					   if (recorrido.equals("derecha")) {
						   c=this.cInit;
						   f++;
					   }else {
						   c=this.cInit;
						   f--;}
				   }
				  // out="paso";
				   if (this.fueraDeRango(f,c)==false) {
					   if (this.recorrido.equals("derecha")&&this.jugadores[0].getRadarJugador().GetCasilla(fant,cant).GetValor().equals("agua")){
						   this.recorrido="izquierda";
						//   out="agua anterior";
					    }else if (false==this.jugadores[0].getRadarJugador().GetCasilla(f,c).isExplorada()) {
							   buscandoProximoDisparo=false;
						//   out="salio explorada";
					    }else if (this.jugadores[0].getRadarJugador().GetCasilla(f,c).GetValor().equals("agua")){
						   this.recorrido="izquierda";
						 //  out="agua actual";
					    }
				   }else {
					   if (this.recorrido.equals("derecha")) {
						   this.recorrido="izquierda";
						   //out="cambio por fuera de rango";
					   }
				   }
				   s++;
				   if (s>=9) {
					   buscandoProximoDisparo=false;
				   }
				//   salidaForzada[s]=f+"x"+c+" r :"+this.recorrido+"d :"+this.direccion+" salida: "+out;
			   }
			  
			   /* testear errrores
			   if (s>=9) {
				   System.out.println("-------------");
				   System.out.println(salidaForzada[1]);
				   System.out.println(salidaForzada[2]);
				   System.out.println(salidaForzada[3]);
				   System.out.println(salidaForzada[4]);
				   System.out.println(salidaForzada[5]);
				   System.out.println(salidaForzada[6]);
				   System.out.println(salidaForzada[7]);
				   System.out.println(salidaForzada[8]);
				   System.out.println(salidaForzada[9]);		   
			   }*/
			   
		   }
	    }else {
	    	Random r= new Random() ;
			f = r.nextInt(10);
			r= new Random() ;
		    c= r.nextInt(10);
		    while (this.jugadores[0].getRadarJugador().GetCasilla(f, c).isExplorada()) {
		    	r = new Random();
		    	f = r.nextInt(10);
		    	r = new Random();
		    	c= r.nextInt(10);
		    }
	    }
		System.out.println("salida "+f+"x"+c);
		this.fant=f;
		this.cant=c;
		
		Turnojugador(f, c,"IA");
		}
	}

	private boolean fueraDeRango(int f, int c) {
		if (f>9) {
			return true;
		}
		if (c>9) {
			return true;
		}
		if (f<0) {
			return true;
		}
		if (c<0) {
			return true;
		}
		return false;
	}

	
}
