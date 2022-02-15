package battleship.cliente;


import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import battleship.controlador.BatallaNavalController;
import battleship.vista.IVista;
import battleship.vista.vistaConsola;
import battleship.vista.vistaGrafica;

public class appCliente {

	public static void main(String[] args) {
		String name = null;
		String ip = null;
		String port = null;
		String ipServidor = null;
		String portServidor = null;
		ArrayList<String> ips = Util.getIpDisponibles();
		String  optionVista=(String)  JOptionPane.showInputDialog(
				null, 
				"Vista  (Grafica)=1 (consola)=2","", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				null
		);
		String option=(String)  JOptionPane.showInputDialog(
				null, 
				"SELECCION RAPIDA (JUGADOR 1)=1 (JUGADOR 2)=2","", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				null
		);
		if (option.equals("1")) {
			name = "Player 1";
			ip = "127.0.0.1";
			port = "4444";
			ipServidor = "127.0.0.1";
			portServidor = "8888";
		}else if (option.equals("2")) {
			name = "Player 2";
			ip = "127.0.0.1";
			port = "7777";
			ipServidor = "127.0.0.1";
			portServidor = "8888";
		}else {
				ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
		);
				port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				9999
		);
				ipServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la corre el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				null
		);
				portServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que corre el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
				name = (String) JOptionPane.showInputDialog(
				"Ingrese nombre del jugador", 
				JOptionPane.QUESTION_MESSAGE
		);
		}
		IVista vista = null;
		if (optionVista.equals("2")) {
			vista = new vistaConsola(name);
		}else {
			vista = new vistaGrafica(name);
		}
		
	
		BatallaNavalController controller=new BatallaNavalController(vista);
		Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
		try {
			c.iniciar(controller);
			vista.Menu();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
