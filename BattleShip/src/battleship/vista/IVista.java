package battleship.vista;

import battleship.controlador.BatallaNavalController;

public interface IVista {

	String getName();

	void scanRadar(int f, int c);

	void setShip(int f, int c);

	void setupBarcos();

	void Menu();

	void readyMenu();

	void setMiControlador(BatallaNavalController batallaNavalController);

	void mostrarMensaje(String mensaje);

	void finPartida(String winner);

	void actualizarLabels();

	void actualizarBarcos();

	void setupCombate();

	void actualizarRadar();

	void marcarDisparo(int f, int c);

	void readyVersusIA();

}