package battleship.controlador;

import java.rmi.RemoteException;
import java.util.Collection;


import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import battleship.modelo.Barco;
import battleship.modelo.EventosBatallaNaval;
import battleship.modelo.IJuego;
import battleship.vista.IVista;

public class BatallaNavalController implements IControladorRemoto{
	private IJuego ElJuego;
	private String name;
	private IVista vista;
	public boolean setupFase;
	public boolean	combateFase;
	
	public BatallaNavalController getControllerInstance() {
		return this;
	}
	
	public BatallaNavalController(IVista vista) {
		this.setupFase=false;
		this.combateFase=false;
		this.vista=vista;
		this.vista.setMiControlador(this);
		this.name=this.vista.getName();
	}
		
	
	
	public <T extends IObservableRemoto> BatallaNavalController(T modelo) {
		try {
			this.setModeloRemoto(modelo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void notificarmensaje(String mensaje) {
		this.vista.mostrarMensaje(mensaje);
	}
	
	public String verBarcosColocados (String nombre) {
		Collection<Barco> list = null;
		try {
			list = this.ElJuego.barcosColocados(nombre);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String m="Barcos que se colocaron :";
		for (Barco barco:list) {
			m=m+" "+barco.GetNombre();
		}
		return m;
	}


	public void AgregarUnBarco(int f, int c,String direccion,int tam,String jugador) {
		if (this.setupFase) {
		try {
			this.ElJuego.colocarPiezas(f, c, direccion, tam, jugador,false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.ElJuego.IsTodosBarcosColocados();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void Disparar(int f,int c) {
		if (this.combateFase) {
				try {
					this.ElJuego.Turnojugador(f, c,this.name);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public String Turnode() {
		String valor = "";
		try {
			valor= this.ElJuego.aQuienLeTocaJugar();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}
	
	public   String[][] TableroBarcos() {
		String[][] valor = null ;
		try {
			valor=this.ElJuego.getTableroBarcos(this.name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}
	
	
	public   String[][] TableroRadar(String J) {
		String[][] valor = null ;
		try {
			valor=this.ElJuego.getTableroRadar(J);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}
	
	
	public void notificar(EventosBatallaNaval evento) {
		System.out.println(evento.toString());
		switch(evento) {
			case JUGADORAGREGADOP1:
			try {
				if (this.ElJuego.getJugador(1).equals(this.name)) {
					this.setupFase=true;
					this.combateFase=false;
					this.vista.setupBarcos();
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case JUGADORAGREGADOP2:
				try {
					if (this.ElJuego.getJugador(0).equals(this.name)) {
						this.setupFase=true;
						this.combateFase=false;
						this.vista.setupBarcos();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					break;
			case BARCOAGREGADOP1:
			try {
				if (this.ElJuego.getJugador(1).equals(this.name)) {
					if (this.ElJuego.todosLosbarcos(1)) {
						this.combateFase=true;
					}
					this.vista.actualizarBarcos();
					}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case BARCOAGREGADOP2:	
				try {
					if (this.ElJuego.getJugador(0).equals(this.name)) {
					if (this.ElJuego.todosLosbarcos(0)) {
						this.combateFase=true;
					}
					this.vista.actualizarBarcos();
					}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case TODOSLOSBARCOSCOLOCADOS:
				this.combateFase=true;
				this.iniciarFaseCombate(vista);
				break;
			case TURNODEJUGADOR:
				this.turnoJugador(vista);
				break;
			case FINPARTIDA:
				this.combateFase=false;
				this.vista.finPartida(this.winner());
				break;
		default:
			break;
		
		}
	}
	
	

	

	public void iniciarFaseCombate(IVista vista) {
		vista.actualizarBarcos();
		vista.setupCombate();
		this.setupFase=false;
		try {
			this.ElJuego.turnoIA();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void colocarRandom() {
		try {
			this.ElJuego.randomizar(this.name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void turnoJugador(IVista vista) {
		vista.actualizarBarcos();
		vista.actualizarRadar();
		vista.actualizarLabels();
		try {
			this.ElJuego.turnoIA();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readyPlayerVersus(String name) {
		try {
			
			this.ElJuego.iniciarJuego(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String winner() {
		String valor = "";
		try {
			valor= this.ElJuego.finPartida();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}

	
	public String barcosDestruidos(String name) {
		String valor = "";
		try {
			valor=this.ElJuego.barcosDestruidosOponente(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}
	
	public String barcosDestruidosOponente(String name) {
		String valor = "";
		try {
			valor=this.ElJuego.barcosDestruidos(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
		
	}

/*
	public void notificaDisparo(String j, int f, int c) {
		if (this.vistaP1.getName().equals(j)) {
			this.vistaP1.marcarDisparo(f,c);
		}else if (this.vistaP2.getName().equals(j)) {
			this.vistaP2.marcarDisparo(f,c);
		}
		
	
	}
*/	

	@Override
	public void actualizar(IObservableRemoto modelo, Object arg) throws RemoteException {
		if (arg instanceof EventosBatallaNaval) {
			this.notificar((EventosBatallaNaval) arg);
		}else if (arg instanceof String) {
			String message=(String) arg;
			String[] parts = message.split("-");
        	String destino=parts[0];
        	String info=parts[1];
        	if ( destino.equals("all")|| destino.equals(this.name)) {
        		this.notificarmensaje(info);
        	}
        	if (destino.equals("disparo"+this.name)) {
        		String[] partInfo=info.split("x");
        		this.vista.marcarDisparo(Integer.parseInt(partInfo[0]),Integer.parseInt(partInfo[1]));
        	}
		}
		
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modelo) throws RemoteException {
		// TODO Auto-generated method stub
		this.ElJuego = (IJuego)modelo;
	}

	public void iniciarversusIA(String name) {
		try {
			this.ElJuego.iniciarJuegoVSIA(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

	
