package battleship.modelo;

import java.rmi.RemoteException;
import java.util.Collection;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IJuego extends IObservableRemoto{

	String getJugador(int i) throws RemoteException;

	void comenzar() throws RemoteException;

	void iniciarJuego(String nombre) throws RemoteException;

	Collection<Barco> barcosColocados(String nombre) throws RemoteException;

	boolean IsTodosBarcosColocados() throws RemoteException;

	String aQuienLeTocaJugar() throws RemoteException;

	String colocarPiezas(int f, int c, String direccion, int tam, String j,boolean flagRandom) throws RemoteException;

	String finPartida() throws RemoteException;

	String[][] getTableroRadar(String nombre) throws RemoteException;

	String[][] getTableroBarcos(String nombre) throws RemoteException;

	String Turnojugador(int f, int c, String name) throws RemoteException;

	void turnoIA() throws RemoteException;

	String barcosDestruidos(String name) throws RemoteException;

	String barcosDestruidosOponente(String name) throws RemoteException;

	String turnoSiguiente() throws RemoteException;

	void iniciarJuegoVSIA(String name) throws RemoteException;

	void randomizar(String name) throws RemoteException;
	
	 Boolean todosLosbarcos(int j) throws RemoteException;

}